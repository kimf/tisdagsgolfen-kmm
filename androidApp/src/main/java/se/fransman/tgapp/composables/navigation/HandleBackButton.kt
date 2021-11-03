package se.fransman.tgapp.composables.navigation

import androidx.compose.runtime.Composable
import androidx.activity.compose.BackHandler
import se.fransman.tg.shared.viewmodel.Navigation

@Composable
fun Navigation.HandleBackButton() {
  BackHandler { // catching the back button to update the DKMPViewModel
    exitScreen()
  }
}