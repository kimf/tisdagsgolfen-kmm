//
//  ContentView.swift
//  iosApp
//
//  Created by Kim Fransman on 2021-11-05.
//  Copyright © 2021 Fransman. All rights reserved.
//

import SwiftUI
import flywheel

struct ContentView: View {
    
    @ObservedObject var viewModel = LeaderboardViewModel()
    
    var body: some View {
        HomeScreen(state: $viewModel.state)
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}


class LeaderboardViewModel : ObservableObject {
    @Published var  state : LeaderboardState = LeaderboardState()
    
    
    init() {
        stateReserveHolder.states.collect(collector: Collector<LeaderboardState> { leaderboardState in
            self.state = leaderboardState
            
        }) { (unit, error) in
            // code which is executed if the Flow object completed
        }
    
        var repository = Repository.init()
        GetLeaderboardSideEffect.init(stateReserve: stateReserveHolder.stateReserve, dispatchers: DispatcherProviderImpl.init())
    }
}
