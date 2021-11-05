package se.fransman.tgapp

import androidx.lifecycle.ViewModel
import com.msabhi.flywheel.*
import com.msabhi.flywheel.utilities.getDefaultScope
import com.msabhi.flywheel.utilities.getDefaultStateReserveConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch

import android.util.ArrayMap
import android.util.LongSparseArray
import android.util.SparseArray
import androidx.lifecycle.ViewModelProvider
import java.lang.reflect.Field
import java.lang.reflect.Modifier
import java.lang.reflect.ParameterizedType
import kotlin.reflect.KCallable
import kotlin.reflect.KClass

open class BaseViewModel<S : State>(
  private val initialState: S,
  private val reduce: Reduce<S>? = null,
  private val middlewares: List<Middleware<S>>? = null,
  stateReserve: StateReserve<S>? = null,
  scope: CoroutineScope = getDefaultScope(),
  private val config: StateReserveConfig = getDefaultStateReserveConfig(scope, BuildConfig.DEBUG),
) : ViewModel() {

  private val stateReserve = stateReserve ?: createStateReserve()

  protected val TAG by lazy { this::class.simpleName ?: "FlywheelViewModel" }
  protected val actions: Flow<Action> = this.stateReserve.actions
  protected val actionStates: Flow<ActionState.Always<Action, S>> = this.stateReserve.actionStates
  protected val scope = this.stateReserve.config.scope

  val states: Flow<S> = this.stateReserve.states
  val eventActions: Flow<EventAction> = actions.filterIsInstance()
  val navigateActions: Flow<NavigateAction> = actions.filterIsInstance()

  init {

    if (this.stateReserve.config.debugMode) {
      this.stateReserve.config.scope.launch(Dispatchers.Default) {
        this@BaseViewModel.initialState::class.assertImmutability()
      }
    }
  }

  private fun createStateReserve() = StateReserve(
    initialState = InitialState.set(initialState),
    reduce = reduce ?: ::reduce,
    middlewares = middlewares,
    config = config
  )

  fun state() = stateReserve.state()

  suspend fun awaitState() = stateReserve.awaitState()

  fun dispatch(action: Action) {
    stateReserve.dispatch(action)
  }

  protected open fun reduce(action: Action, state: S): S {
    throw NotImplementedError("Either provide a reducer in constructor or override this function")
  }

  override fun onCleared() {
    super.onCleared()
    stateReserve.terminate()
  }
}


/**
 * Ensures that the state class is immutable.
 * NOTE: Kotlin collections immutability is a compile-time check only and the underlying classes are
 * mutable so it is impossible to detect them here.
 * Kotlin mutability: https://stackoverflow.com/a/33732403/715633
 *
 * As a result, you may not use MutableList, mutableListOf(...) or the map variants by convention only.
 */

private const val IMMUTABLE_LIST_MESSAGE =
  "Use the immutable listOf(...) method instead. You can append it with `val newList = listA + listB`"
private const val IMMUTABLE_MAP_MESSAGE =
  "Use the immutable mapOf(...) method instead. You can append it with `val newMap = mapA + mapB`"
internal fun KClass<*>.assertImmutability() {
  require(java.isData) { "State must be a data class! - ${this::class.simpleName}" }

  fun Field.isSubtype(vararg classes: KClass<*>): Boolean {
    return classes.any { klass ->
      return when (val returnType = this.type) {
        is ParameterizedType -> klass.java.isAssignableFrom(returnType.rawType as Class<*>)
        else -> klass.java.isAssignableFrom(returnType)
      }
    }
  }

  java.declaredFields
    // During tests, jacoco can add a transient field called jacocoData.
    .filterNot { Modifier.isTransient(it.modifiers) }
    .forEach { prop ->
      when {
        !Modifier.isFinal(prop.modifiers) -> "State property ${prop.name} must be a val, not a var."
        prop.isSubtype(ArrayList::class) -> "You cannot use ArrayList for ${prop.name}.\n$IMMUTABLE_LIST_MESSAGE"
        prop.isSubtype(SparseArray::class) -> "You cannot use SparseArray for ${prop.name}.\n$IMMUTABLE_LIST_MESSAGE"
        prop.isSubtype(LongSparseArray::class) -> "You cannot use LongSparseArray for ${prop.name}.\n$IMMUTABLE_LIST_MESSAGE"
        //prop.isSubtype(SparseArrayCompat::class) -> "You cannot use SparseArrayCompat for ${prop.name}.\n$IMMUTABLE_LIST_MESSAGE"
        prop.isSubtype(ArrayMap::class) -> "You cannot use ArrayMap for ${prop.name}.\n$IMMUTABLE_MAP_MESSAGE"
        prop.isSubtype(HashMap::class) -> "You cannot use HashMap for ${prop.name}.\n$IMMUTABLE_MAP_MESSAGE"
        prop.isSubtype(Function::class, KCallable::class) -> {
          "You cannot use functions inside state. Only pure data should be represented: ${prop.name}"
        }
        else -> null
      }?.let { throw IllegalArgumentException("Invalid property in state ${this@assertImmutability::class.simpleName}: $it") }
    }
}

/**
 * Since we can only use java reflection, this basically duck types a data class.
 * componentN methods are also used for @PersistState.
 */
internal val Class<*>.isData: Boolean
get() {
  if (!declaredMethods.any { it.name == "copy\$default" && it.isSynthetic }) {
    return false
  }

  // if the data class property is internal then kotlin appends '$module_name_debug' to the
  // expected function name.
  declaredMethods.firstOrNull { it.name.startsWith("component1") } ?: return false

  declaredMethods.firstOrNull { it.name == "equals" } ?: return false
  declaredMethods.firstOrNull { it.name == "hashCode" } ?: return false
  return true
}

class BaseViewModelFactory<T>(val creator: () -> T) : ViewModelProvider.Factory {
  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    return creator() as T
  }
}