package se.fransman.tg.shared.state

import com.msabhi.flywheel.*
import com.msabhi.flywheel.attachments.DispatcherProvider
import com.msabhi.flywheel.attachments.SideEffect
import com.msabhi.flywheel.utilities.getDefaultScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import se.fransman.tg.PlayersQuery
import se.fransman.tg.shared.DebugLogger
import se.fransman.tg.shared.datalayer.Repository
import se.fransman.tg.shared.datalayer.functions.getDashboardData

data class LeaderboardState(
  val loading: Boolean = true,
  val players: List<PlayersQuery.Player> = emptyList(),
  val error: Exception? = null,
) : State

sealed interface LeaderboardAction : Action {
  object GetLeaderboardAction : LeaderboardAction
  data class LeaderboardLoadedAction(val players: List<PlayersQuery.Player>) : LeaderboardAction
  data class ErrorLoadingLeaderboardAction(val error: Exception) : LeaderboardAction
}

val reducer = reducerForAction<LeaderboardAction, LeaderboardState> { action, state ->
  with(state) {
    when (action) {
      is LeaderboardAction.GetLeaderboardAction -> copy(loading = true)
      is LeaderboardAction.LeaderboardLoadedAction -> copy(players = action.players, loading = false)
      is LeaderboardAction.ErrorLoadingLeaderboardAction -> copy(error = action.error, loading = false)
      else -> state
    }
  }
}

val leaderboardStateReserve = StateReserve(
  initialState = InitialState.set(LeaderboardState()),
  reduce = reducer,
  middlewares = emptyList(),
  config = StateReserveConfig(
    scope = getDefaultScope(), debugMode = false
  )
)


class GetLeaderboardSideEffect(
  private val repository: Repository,
  private val stateReserve: StateReserve<LeaderboardState>,
  dispatchers: DispatcherProvider
) : SideEffect<LeaderboardState>(stateReserve, dispatchers) {

  init {
    stateReserve.actionStates.onEach(::handle).launchIn(scope)
  }

  private fun handle(actionState: ActionState<Action, LeaderboardState>) {
    if (actionState.action is LeaderboardAction.GetLeaderboardAction && actionState.state.players.isEmpty()) {
      scope.launch {
        val players = repository.getDashboardData()
        stateReserve.dispatch(LeaderboardAction.LeaderboardLoadedAction(players))
      }
    }
  }

}


// stateReserve.states.collect { state -> println(state.counter) }
// stateReserve.dispatch(IncrementAction)