package se.fransman.tg.shared.viewmodel.screens

import se.fransman.tg.shared.viewmodel.Navigation
import se.fransman.tg.shared.viewmodel.ScreenIdentifier
import se.fransman.tg.shared.viewmodel.screens.home.initDashboard


// DEFINITION OF ALL SCREENS IN THE APP

enum class Screen(
  val asString: String,
  val navigationLevel : Int = 1,
  val initSettings: Navigation.(ScreenIdentifier) -> ScreenInitSettings,
  val stackableInstances : Boolean = false,
) {
  Home("dashboard", 1, { initDashboard() }, true),
  // CountryDetail("country", 2, { initCountryDetail(it.params()) }),
}