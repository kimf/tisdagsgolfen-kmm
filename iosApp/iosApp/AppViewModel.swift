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
    @Published var sides: [CubeQuery.Side] = []
    let repository = ApolloRepository()
    
    func getCube() async {
        do {
            let cube = try await repository.getCube()
            sides = (cube?.sidesFilterNotNull())!
        } catch {
            print(error)
        }
    }
}

