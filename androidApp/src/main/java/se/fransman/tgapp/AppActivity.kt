package se.fransman.tgapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import se.fransman.tg.shared.state.LeaderboardAction
import se.fransman.tgapp.screens.HomeScreen

class AppActivity: ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val viewModel by viewModels<LeaderboardViewModel> {
      BaseViewModelFactory {
        LeaderboardViewModel.get(this)
      }
    }
    viewModel.dispatch(LeaderboardAction.GetLeaderboardAction)

    setContent {
      MaterialTheme() {
        HomeScreen(state = viewModel.state())
      }
    }
  }
}