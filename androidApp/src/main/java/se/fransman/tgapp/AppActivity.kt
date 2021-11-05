package se.fransman.tgapp


import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import se.fransman.tgapp.ui.compose.AppTheme
import se.fransman.tgapp.ui.compose.MainScreen
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject
import se.fransman.tg.app.LeaderboardAction
import se.fransman.tg.app.LeaderboardSideEffect
import se.fransman.tg.app.LeaderboardStore

class AppActivity : ComponentActivity(), CoroutineScope by CoroutineScope(Dispatchers.Main) {
  private val store: LeaderboardStore by inject()
  private var effectJob: Job? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    if (savedInstanceState == null) {
      store.dispatch(LeaderboardAction.Refresh(false))
    }
    setContent {
      AppTheme {
        ProvideWindowInsets {
          Box(
            Modifier.padding(
              rememberInsetsPaddingValues(
                insets = LocalWindowInsets.current.systemBars,
                applyStart = true,
                applyTop = false,
                applyEnd = true,
                applyBottom = false
              )
            )
          ) {
            Navigator(MainScreen())
          }
        }
      }
    }
  }

  override fun onResume() {
    super.onResume()
    effectJob = store.observeSideEffect().onEach { effect ->
      if (effect is LeaderboardSideEffect.Error) {
        Toast.makeText(this, effect.error.message, Toast.LENGTH_SHORT).show()
      }
    }.launchIn(this)
  }

  override fun onPause() {
    super.onPause()
    effectJob?.cancel()
  }

  override fun onDestroy() {
    if (isFinishing) cancel()
    super.onDestroy()
  }
}