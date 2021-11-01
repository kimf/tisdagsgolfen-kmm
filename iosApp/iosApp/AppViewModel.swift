//
//  AppViewModel.swift
//  iosApp
//
//  Created by Kim Fransman on 2021-10-19.
//  Copyright © 2021 Fransman. All rights reserved.
//

import Foundation
import SwiftUI
import shared

@MainActor
class AppViewModel: ObservableObject {
    @Published
    var players: [PlayersQuery.AllPlayer] = []

    let repository = ApolloRepository()

    func getPlayers() async {
        do {
            players = try await repository.getPlayers()
        } catch {
            print(error)
        }
    }
}

