package se.fransman.tg.app

import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import se.fransman.tg.PlayersQuery
import se.fransman.tg.core.Tisdagsgolfen

data class LeaderboardState(
  val loading: Boolean,
  val players: List<PlayersQuery.Player>
) : State

// fun LeaderboardState.mainFeedPosts() = (selectedFeed?.posts ?: feeds.flatMap { it.posts }).sortedByDescending { it.date }

sealed class LeaderboardAction : Action {
  data class Refresh(val forceLoad: Boolean = false) : LeaderboardAction()
/*
  data class Add(val url: String) : LeaderboardAction()
  data class Delete(val url: String) : LeaderboardAction()
  data class SelectFeed(val feed: Feed?) : LeaderboardAction()
 */
  data class Data(val players: List<PlayersQuery.Player>) : LeaderboardAction()
  data class Error(val error: Exception) : LeaderboardAction()
}

sealed class LeaderboardSideEffect : Effect {
  data class Error(val error: Exception) : LeaderboardSideEffect()
}

class LeaderboardStore(
  private val app: Tisdagsgolfen
) : Store<LeaderboardState, LeaderboardAction, LeaderboardSideEffect>,
  CoroutineScope by CoroutineScope(Dispatchers.Main) {

  private val state = MutableStateFlow(LeaderboardState(false, emptyList()))
  private val sideEffect = MutableSharedFlow<LeaderboardSideEffect>()

  override fun observeState(): StateFlow<LeaderboardState> = state

  override fun observeSideEffect(): Flow<LeaderboardSideEffect> = sideEffect

  override fun dispatch(action: LeaderboardAction) {
    Napier.d(tag = "LeaderboardStore", message = "Action: $action")
    val oldState = state.value

    val newState = when (action) {
      is LeaderboardAction.Refresh -> {
        if (oldState.loading) {
          launch { sideEffect.emit(LeaderboardSideEffect.Error(Exception("In progress"))) }
          oldState
        } else {
          launch { loadPlayers() }
          oldState.copy(loading = true)
        }
      }
      /*is FeedAction.Add -> {
        if (oldState.progress) {
          launch { sideEffect.emit(FeedSideEffect.Error(Exception("In progress"))) }
          oldState
        } else {
          launch { addFeed(action.url) }
          FeedState(true, oldState.feeds)
        }
      }*/
      is LeaderboardAction.Data -> {
        if (oldState.loading) {
          LeaderboardState(false, action.players)
        } else {
          launch { sideEffect.emit(LeaderboardSideEffect.Error(Exception("Unexpected action"))) }
          oldState
        }
      }
      is LeaderboardAction.Error -> {
        if (oldState.loading) {
          launch { sideEffect.emit(LeaderboardSideEffect.Error(action.error)) }
          LeaderboardState(false, oldState.players)
        } else {
          launch { sideEffect.emit(LeaderboardSideEffect.Error(Exception("Unexpected action"))) }
          oldState
        }
      }
    }

    if (newState != oldState) {
      Napier.d(tag = "LeaderboardStore", message = "NewState: $newState")
      state.value = newState
    }
  }

  private suspend fun loadPlayers() {
    try {
      val players = app.getPlayers()
      dispatch(LeaderboardAction.Data(players))
    } catch (e: Exception) {
      dispatch(LeaderboardAction.Error(e))
    }
  }
  /*
  private suspend fun addFeed(url: String) {
    try {
      rssReader.addFeed(url)
      val allFeeds = rssReader.getAllFeeds(false)
      dispatch(FeedAction.Data(allFeeds))
    } catch (e: Exception) {
      dispatch(FeedAction.Error(e))
    }
  }
   */
}