package se.fransman.tgapp.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import se.fransman.tgapp.composables.navigation.Router
import se.fransman.tg.shared.viewmodel.DKMPViewModel

@Composable
fun MainComposable(model: DKMPViewModel) {
  val appState by model.stateFlow.collectAsState()
  println("D-KMP-SAMPLE: recomposition Index: "+appState.recompositionIndex.toString())
  val dkmpNav = appState.getNavigation(model)
  dkmpNav.Router()
}