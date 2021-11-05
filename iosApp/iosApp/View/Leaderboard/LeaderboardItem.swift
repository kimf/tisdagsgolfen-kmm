//
//  ListRowView.swift
//  iosApp
//
//  Created by Kim Fransman on 2021-10-19.
//  Copyright © 2021 Fransman. All rights reserved.
//


import SwiftUI
import shared

struct LeaderboardItem: View {
    let player: PlayersQuery.Player
    
    var body: some View {
        HStack(alignment: .top, spacing: 8) {
            Image(systemName: "sunrise")
            Text(player.firstName)
                .font(.footnote)
                .foregroundColor(.gray)
            Text(player.lastName)
                .font(.footnote)
                .foregroundColor(.gray)
        }
    }
}

struct LeaderboardItem_Previews: PreviewProvider {
    static var previews: some View {
        Group {
            LeaderboardItem(player: PlayersQuery.Player.init(id: 1, firstName: "Kim", lastName: "Fransman", photo: ""))
                .previewLayout(PreviewLayout.sizeThatFits)
                .padding()
                .previewDisplayName("Default preview 1")
        }
    }
}
