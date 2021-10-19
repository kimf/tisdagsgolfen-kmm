

package se.fransman.tisdagsgolfen

import com.apollographql.apollo3.ApolloClient

class ApolloRepository {
  // private val scope = MainScope()
  private val apolloClient = ApolloClient(serverUrl = "https://thecube.daresaycloud.co/graphql")

  suspend fun getCube(): CubeQuery.Cube? {
    val response = apolloClient.query(CubeQuery())
    return response.dataOrThrow.cube
  }
}