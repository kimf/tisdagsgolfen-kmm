package se.fransman.tgapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.statusBarsHeight
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import se.fransman.tg.PlayersQuery
import se.fransman.tgapp.theme.AppTheme

@Composable
fun SideItem(
  player: PlayersQuery.AllPlayer
) {
  Row(
    Modifier.padding(16.dp),
  ) {
    Row {
      Text(
        style = MaterialTheme.typography.body1,
        text = player.firstName ?: ""
      )
      Spacer(modifier = Modifier.size(8.dp))
      Text(
        style = MaterialTheme.typography.body1,
        text = player.lastName ?: ""
      )
    }
  }
}

@Composable
fun SideItemList(
  players: List<PlayersQuery.AllPlayer>
) {

  LazyColumn {
    itemsIndexed(players) { i, side ->
      if (i == 0) Spacer(modifier = Modifier.statusBarsHeight())
      SideItem(side)
    }
  }
}

@Composable
fun MainScreen(viewModel: AppViewModel) {
  val (players, setPlayers) = remember { mutableStateOf<List<PlayersQuery.AllPlayer>?>(null) }

  LaunchedEffect("main") {
    setPlayers(viewModel.getPlayers())
  }

  players?.let {
    SideItemList(players = players)
  }
}

@Preview
@Composable
fun MainScreenPreview() {
  AppTheme {
    SideItemList(players = listOf(PreviewData.player, PreviewData.player))
  }
}