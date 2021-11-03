package se.fransman.tg.shared.viewmodel.screens.home

import se.fransman.tg.shared.viewmodel.Navigation
import se.fransman.tg.shared.viewmodel.ScreenParams
import se.fransman.tg.shared.viewmodel.debugLogger
import se.fransman.tg.shared.viewmodel.screens.ScreenInitSettings
import kotlinx.serialization.Serializable
import se.fransman.tg.shared.datalayer.functions.getDashboardData

// INITIALIZATION settings for this screen
// this is what should be implemented:
// - a data class implementing the ScreenParams interface, which defines the parameters to the passed to the screen
// - Navigation extension function taking the ScreenParams class as an argument, return the ScreenInitSettings for this screen
// to understand the initialization behaviour, read the comments in the ScreenInitSettings.kt file

// @Serializable // Note: ScreenParams should always be set as Serializable
// data class CountriesListParams(val listType: CountriesListType) : ScreenParams

fun Navigation.initDashboard() = ScreenInitSettings (
  title = "Tisdagsgolfen",
  initState = { HomeState(isLoading = true) },
  callOnInit = {
    var listData = dataRepository.getDashboardData()
    // update state, after retrieving data from the repository
    stateManager.updateScreen(HomeState::class) {
      it.copy(
        isLoading = false,
        players = listData
      )
    }
  },
  reinitOnEachNavigation = true, // in this way favourites can refresh
)