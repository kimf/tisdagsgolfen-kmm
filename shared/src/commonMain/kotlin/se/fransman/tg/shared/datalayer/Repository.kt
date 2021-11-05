package se.fransman.tg.shared.datalayer

import com.apollographql.apollo3.ApolloClient
import kotlinx.coroutines.*

class Repository(val useDefaultDispatcher: Boolean = true) {

  internal val apolloClient by lazy { ApolloClient.Builder().serverUrl("http://192.168.68.112:4000/graphql").build() }

  // we run each repository function on a Dispatchers.Default coroutine
  // we pass useDefaultDispatcher=false just for the TestRepository instance
  suspend fun <T> withRepoContext (block: suspend () -> T) : T {
    return if (useDefaultDispatcher) {
      withContext(Dispatchers.Default) {
        block()
      }
    } else {
      block()
    }
  }

}