package se.fransman.tg.app

import se.fransman.tg.core.wrap

fun LeaderboardStore.watchState() = observeState().wrap()
fun LeaderboardStore.watchSideEffect() = observeSideEffect().wrap()