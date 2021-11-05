package se.fransman.tgapp.ui.compose

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.navigationBarsHeight
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import se.fransman.tg.app.LeaderboardAction
import se.fransman.tg.app.LeaderboardStore

class MainScreen : Screen, KoinComponent {

    @Composable
    override fun Content() {
        val store: LeaderboardStore by inject()
        val context = LocalContext.current
        val navigator = LocalNavigator.currentOrThrow
        val state = store.observeState().collectAsState()
        val players = state.value.players

        SwipeRefresh(
            state = rememberSwipeRefreshState(state.value.loading),
            indicatorPadding = rememberInsetsPaddingValues(LocalWindowInsets.current.systemBars),
            clipIndicatorToPadding = false,
            indicator = { indicatorState, refreshTriggerDistance ->
                SwipeRefreshIndicator(
                    state = indicatorState,
                    refreshTriggerDistance = refreshTriggerDistance,
                    scale = true //https://github.com/google/accompanist/issues/572
                )
            },
            onRefresh = { store.dispatch(LeaderboardAction.Refresh(true)) }
        ) {
            Column {
                val listState = rememberLazyListState()
                PlayersList(
                    modifier = Modifier.weight(1f),
                    players = players,
                    listState = listState
                ) { post ->
                    post.photo?.let { url ->
                        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                    }
                }
                Spacer(
                    Modifier
                        .navigationBarsHeight()
                        .fillMaxWidth()
                )
            }
        }
    }
}