package se.fransman.tg.shared.datalayer.functions

import com.apollographql.apollo3.exception.ApolloException
import se.fransman.tg.PlayersQuery
import se.fransman.tg.shared.datalayer.Repository
import se.fransman.tg.shared.viewmodel.debugLogger


suspend fun Repository.getDashboardData(): List<PlayersQuery.Player> = withRepoContext {
    val response = apolloClient.query(PlayersQuery())
    response.dataOrThrow.players
}