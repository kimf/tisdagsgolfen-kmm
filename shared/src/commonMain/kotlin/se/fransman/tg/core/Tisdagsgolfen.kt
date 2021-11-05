package se.fransman.tg.core

import com.apollographql.apollo3.ApolloClient
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import se.fransman.tg.PlayersQuery

class Tisdagsgolfen internal constructor() {

  private val apolloClient = ApolloClient.Builder().serverUrl("http://192.168.68.112:4000/graphql").build()

  @Throws(Exception::class)
  suspend fun getPlayers(): List<PlayersQuery.Player> {
    val response = apolloClient.query(PlayersQuery())
    return response.dataOrThrow.players
  }

  /*@Throws(Exception::class)
  suspend fun addPlayer(player: PlayersQuery.Player) {
    feedStorage.saveFeed(feed)
  }*/

  private suspend fun <A, B> Iterable<A>.mapAsync(f: suspend (A) -> B): List<B> =
    coroutineScope { map { async { f(it) } }.awaitAll() }

  companion object
}