package se.fransman.tgapp.composables.navigation

import androidx.compose.runtime.Composable
import se.fransman.tg.shared.viewmodel.Navigation
import se.fransman.tg.shared.viewmodel.ScreenIdentifier
import se.fransman.tg.shared.viewmodel.screens.Screen.*
import se.fransman.tgapp.composables.screens.HomeScreen


@Composable
fun Navigation.ScreenPicker(
  screenIdentifier: ScreenIdentifier
) {

  when (screenIdentifier.screen) {

    Home ->
      HomeScreen(
        homeState = stateProvider.get(screenIdentifier),
        // onListItemClick = { navigate(CountryDetail, CountryDetailParams(countryName = it)) },
      )

  }
}