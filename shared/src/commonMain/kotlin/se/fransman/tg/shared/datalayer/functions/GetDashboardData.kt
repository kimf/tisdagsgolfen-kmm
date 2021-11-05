package se.fransman.tg.shared.datalayer.functions

import se.fransman.tg.PlayersQuery
import se.fransman.tg.shared.datalayer.Repository


suspend fun Repository.getDashboardData(): List<PlayersQuery.Player> = withRepoContext {
    val response = apolloClient.query(PlayersQuery())
    response.dataOrThrow.players
}