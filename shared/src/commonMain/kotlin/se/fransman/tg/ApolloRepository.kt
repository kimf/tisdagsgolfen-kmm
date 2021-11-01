

package se.fransman.tg

import com.apollographql.apollo3.ApolloClient

class ApolloRepository {
  // private val scope = MainScope()
  private val apolloClient = ApolloClient.Builder().serverUrl("http://192.168.68.112:4000/graphql").build()

  suspend fun getPlayers(): List<PlayersQuery.AllPlayer> {
    val response = apolloClient.query(PlayersQuery())
    return response.dataOrThrow.allPlayers
  }
}

/*
interface ListItemRepository {
  fun listItems() : List<ListEntry>
}

class DefaultListItemRepository : ListItemRepository {
  override fun listItems() = listOf(
    ListEntry("Title", "This is a subtitle", "https://picsum.photos/100/100"),
    ListEntry("Another title", "This is another subtitle", "https://picsum.photos/100/100"),
    ListEntry("Third one", "And this is the third subtitle", "https://picsum.photos/100/100"),
  )
}
 */