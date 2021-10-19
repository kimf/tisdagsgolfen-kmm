package se.fransman.tisdagsgolfen

import androidx.lifecycle.ViewModel

class AppViewModel(private val repository: ApolloRepository): ViewModel() {
  suspend fun getCube(): CubeQuery.Cube? {
    return repository.getCube()
  }
}