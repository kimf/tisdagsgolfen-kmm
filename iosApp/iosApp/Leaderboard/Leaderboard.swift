//
//  ListView.swift
//  iosApp
//
//  Created by Kim Fransman on 2021-10-19.
//  Copyright © 2021 Fransman. All rights reserved.
//

import SwiftUI
import shared

struct Leaderboard: View {
    @StateObject var viewModel = AppViewModel()

    var body: some View {
        LeaderboardFilters()
        List {            
            ForEach(viewModel.players, id:\.id) { player in
                LeaderboardItem(player: player)
            }
        }
        .task {
            await viewModel.getPlayers()
        }
    }
}
