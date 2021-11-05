package se.fransman.tgapp

import android.content.Context
import com.msabhi.flywheel.InitialState
import com.msabhi.flywheel.StateReserve
import com.msabhi.flywheel.attachments.DispatcherProviderImpl
import se.fransman.tg.shared.datalayer.Repository
import se.fransman.tg.shared.state.GetLeaderboardSideEffect
import se.fransman.tg.shared.state.LeaderboardState
import se.fransman.tg.shared.state.leaderboardStateReserve

class LeaderboardViewModel(
  initialState: LeaderboardState,
  stateReserve: StateReserve<LeaderboardState>
): BaseViewModel<LeaderboardState>(
    initialState = initialState,
    stateReserve = stateReserve
) {
    companion object {
      fun get(context: Context): LeaderboardViewModel {
        val initialState = InitialState.set(LeaderboardState())
        val stateReserve = leaderboardStateReserve
        val repository = Repository()
        GetLeaderboardSideEffect(repository, stateReserve, DispatcherProviderImpl)
        return LeaderboardViewModel(initialState = initialState.state!!, stateReserve = stateReserve)
    }
  }
}
