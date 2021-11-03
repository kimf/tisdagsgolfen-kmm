//
//  LeaderboardFilters.swift
//  iosApp
//
//  Created by Kim Fransman on 2021-11-02.
//  Copyright © 2021 Fransman. All rights reserved.
//

import SwiftUI

struct LeaderboardFilters: View {
    @State private var currentFilter = "🎰"
    let filters = ["🎰","🍻","💸"]
    
    var body: some View {
        Picker("Show leaderboard by?", selection: $currentFilter) {
            ForEach(filters, id: \.self) {
                Text($0)
            }

        }
        .pickerStyle(SegmentedPickerStyle()).padding(.all, 10)
    }
}

struct LeaderboardFilters_Previews: PreviewProvider {
    static var previews: some View {
        LeaderboardFilters()
    }
}
