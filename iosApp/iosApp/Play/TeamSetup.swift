//
//  TeamSetup.swift
//  iosApp
//
//  Created by Kim Fransman on 2021-11-02.
//  Copyright © 2021 Fransman. All rights reserved.
//

import SwiftUI
import shared

struct TeamSetup: View {
    @Binding var teams: [[PlayersQuery.AllPlayer]]
    let players: [PlayersQuery.AllPlayer]
    
    var body: some View {
        Text("Setup Team")
    }
}
