package se.fransman.tgapp.composables.navigation

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.SaveableStateHolder
import se.fransman.tgapp.composables.navigation.bars.TopBar
import se.fransman.tg.shared.viewmodel.Navigation

@Composable
fun Navigation.OnePane(
  saveableStateHolder: SaveableStateHolder
) {
  Scaffold(
    topBar = { TopBar(getTitle(currentScreenIdentifier)) },
    content = {
      saveableStateHolder.SaveableStateProvider(currentScreenIdentifier.URI) {
        ScreenPicker(currentScreenIdentifier)
      }
    }
  )
}