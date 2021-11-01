//
//  ListRowView.swift
//  iosApp
//
//  Created by Kim Fransman on 2021-10-19.
//  Copyright © 2021 Fransman. All rights reserved.
//


import SwiftUI
import shared

struct ListRowView: View {
    let player: PlayersQuery.AllPlayer
    
    var body: some View {
        HStack(alignment: .top, spacing: 20) {
            Text(player.firstName ?? "")
                .font(.footnote)
                .foregroundColor(.gray)
            Text(player.lastName ?? "")
                .font(.footnote)
                .foregroundColor(.gray)
        }
    }
}

struct ListRowViews_Previews: PreviewProvider {
    static var previews: some View {
        Group {
            ListRowView(player: PlayersQuery.AllPlayer.init(id: 1, firstName: "Kim", lastName: "Fransman", photo: ""))
                .previewLayout(PreviewLayout.sizeThatFits)
                .padding()
                .previewDisplayName("Default preview 1")
        }
    }
}
