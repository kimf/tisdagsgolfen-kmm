//
//  PlayerRow.swift
//  iosApp
//
//  Created by Kim Fransman on 2021-11-02.
//  Copyright © 2021 Fransman. All rights reserved.
//

import SwiftUI
import shared

struct PlayerRow: View {
    var player: PlayersQuery.Player
    
    func toggle () {
        // viewModel.toggleSelectedPlayer(player: player)
    }
    
    @State var selectedPlayers: [PlayersQuery.Player] = []
    
    var body: some View {
        HStack(alignment: .top, spacing: 8) {
            Image(systemName: "sunrise")
            Text(player.firstName)
                .font(.footnote)
                .foregroundColor(.gray)
            Text(player.lastName)
                .font(.footnote)
                .foregroundColor(.gray)
            if selectedPlayers.contains(player) {
                Text("Vald")
            }
            Button("Select", action: toggle)
        }
    }
}

struct PlayerRow_Previews: PreviewProvider {
    static var previews: some View {
        PlayerRow(player: PlayersQuery.Player.init(id: 1, firstName: "Kim", lastName: "Fransman", photo: ""))
    }
}
