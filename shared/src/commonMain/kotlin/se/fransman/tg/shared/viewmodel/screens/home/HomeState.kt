package se.fransman.tg.shared.viewmodel.screens.home

import se.fransman.tg.PlayersQuery
import se.fransman.tg.shared.viewmodel.*

// here is the data class defining the state for this screen

data class HomeState (
  val isLoading : Boolean = false,
  val players : List<PlayersQuery.Player> = emptyList(),
): ScreenState

/********** property classes **********/

// enum class CountriesListType { ALL, FAVORITES }
/*
data class PlayerListItem (
  val _data : PlayersQuery.Player,
) {
  // in the ViewModel classes, our computed properties only do UI-formatting operations
  // (the arithmetical operations, such as calculating a percentage, should happen in the DataLayer classes)
  val name = "${_data.firstName} ${_data.lastName}"
}*/