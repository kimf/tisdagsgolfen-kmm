package se.fransman.tgapp.ui.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.google.accompanist.insets.statusBarsHeight
import se.fransman.tg.PlayersQuery

@Composable
fun PlayersList(
    modifier: Modifier,
    players: List<PlayersQuery.Player>,
    listState: LazyListState,
    onClick: (PlayersQuery.Player) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        state = listState
    ) {
        itemsIndexed(players) { i, player ->
            if (i == 0) Spacer(Modifier.statusBarsHeight())
            PlayerItem(player) { onClick(player) }
            if (i != players.size - 1) Spacer(modifier = Modifier.size(16.dp))
        }
    }
}


@Composable
private fun PlayerItem(
    item: PlayersQuery.Player,
    onClick: () -> Unit
) {
    val padding = 16.dp
    Box {
        Card(
            elevation = 16.dp,
            shape = RoundedCornerShape(padding)
        ) {
            Column(
                modifier = Modifier.clickable(onClick = onClick)
            ) {
                Spacer(modifier = Modifier.size(padding))
                Text(
                    modifier = Modifier.padding(start = padding, end = padding),
                    style = MaterialTheme.typography.h6,
                    text = item.firstName + " " + item.lastName
                )
                item.photo?.let { url ->
                    Spacer(modifier = Modifier.size(padding))
                    Image(
                        modifier = Modifier.height(180.dp).fillMaxWidth(),
                        contentScale = ContentScale.FillWidth,
                        painter = rememberImagePainter(url),
                        contentDescription = null
                    )
                }
                Spacer(modifier = Modifier.size(padding))
            }
        }
    }
}

@Preview
@Composable
private fun PlayerPreview() {
    AppTheme {
        PlayerItem(item = PreviewData.player, onClick = {})
    }
}