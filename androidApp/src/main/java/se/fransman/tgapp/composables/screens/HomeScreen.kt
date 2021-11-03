package se.fransman.tgapp.composables.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import se.fransman.tg.shared.viewmodel.screens.home.HomeState

@Composable
fun HomeScreen(
  homeState: HomeState,
) {

  if (homeState.isLoading) {

    LoadingScreen()

  } else {
    val data = homeState.players
    Column(modifier = Modifier.padding(10.dp)) {
      for (player in data) {
        Text(text = "${player.firstName} ${player.lastName}", style = MaterialTheme.typography.body1)
      }
    }

  }
}