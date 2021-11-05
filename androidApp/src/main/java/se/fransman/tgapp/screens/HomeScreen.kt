package se.fransman.tgapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import se.fransman.tg.shared.state.LeaderboardState

@Composable
fun HomeScreen(state: LeaderboardState) {
   if (state.loading) {
      LoadingScreen()
   } else {
     val data = state.players
     Column() {
       for (player in data) {
         Text(
           text = "${player.firstName} ${player.lastName}",
           style = MaterialTheme.typography.body1
         )
       }
     }
   }
}

