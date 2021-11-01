package se.fransman.tgapp

import androidx.lifecycle.ViewModel
import se.fransman.tg.ApolloRepository
import se.fransman.tg.PlayersQuery

class AppViewModel(private val repository: ApolloRepository): ViewModel() {
  suspend fun getPlayers(): List<PlayersQuery.AllPlayer> {
    return repository.getPlayers()
  }
}