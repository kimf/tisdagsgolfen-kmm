//
//  ListView.swift
//  iosApp
//
//  Created by Kim Fransman on 2021-10-19.
//  Copyright © 2021 Fransman. All rights reserved.
//

import SwiftUI
import shared

struct ListView: View {
    @State var players: [PlayersQuery.AllPlayer] = []
    @StateObject var viewModel = AppViewModel()

    var body: some View {
        List {
            Section("Players") {
                ForEach(viewModel.players, id:\.id) { player in
                    ListRowView(player: player)
                }
            }
        }
        .task {
            await viewModel.getPlayers()
        }
    }
}
