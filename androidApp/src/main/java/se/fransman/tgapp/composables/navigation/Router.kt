package se.fransman.tgapp.composables.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import se.fransman.tg.shared.viewmodel.*

@Composable
fun Navigation.Router() {

  val screenUIsStateHolder = rememberSaveableStateHolder()
  OnePane(screenUIsStateHolder)

  screenStatesToRemove.forEach {
    screenUIsStateHolder.removeState(it.URI)
    println("D-KMP: removed UI screen "+it.URI)
  }

  if (!only1ScreenInBackstack) {
    HandleBackButton()
  }

}