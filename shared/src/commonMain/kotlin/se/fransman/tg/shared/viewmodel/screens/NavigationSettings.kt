package se.fransman.tg.shared.viewmodel.screens

import se.fransman.tg.shared.viewmodel.ScreenIdentifier
import se.fransman.tg.shared.viewmodel.screens.Screen.*


// CONFIGURATION SETTINGS

object navigationSettings {
  val dashboardScreen = Level1Navigation.Dashboard // the start screen should be specified here
  val saveLastLevel1Screen = true
  val alwaysQuitOnHomeScreen = true
}


// LEVEL 1 NAVIGATION OF THE APP

enum class Level1Navigation(val screenIdentifier: ScreenIdentifier, val rememberVerticalStack: Boolean = false) {
  Dashboard( ScreenIdentifier.get(Home, null), true),
  // FavoriteCountries( ScreenIdentifier.get(CountriesList, CountriesListParams(listType = FAVORITES)), true),
}