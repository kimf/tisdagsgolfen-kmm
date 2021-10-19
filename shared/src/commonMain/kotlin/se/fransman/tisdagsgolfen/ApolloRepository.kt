

package se.fransman.tisdagsgolfen

import com.apollographql.apollo3.ApolloClient
import kotlinx.coroutines.MainScope


class ApolloRepository {
  private val apolloClient = ApolloClient(serverUrl = "https://thecube.daresaycloud.co/graphql")

  suspend fun getCube(): CubeQuery.Cube? {
    val response = apolloClient.query(CubeQuery())
    return response.dataOrThrow.cube
  }
}