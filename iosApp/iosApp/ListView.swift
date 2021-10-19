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
    @State var sides: [CubeQuery.Side] = []
    @StateObject var viewModel = AppViewModel()

    var body: some View {
        List {
            Section("Sides") {
                ForEach(viewModel.sides, id:\.id) { side in
                    ListRowView(side: side)
                }
            }
        }
        .task {
            await viewModel.getCube()
        }
    }
}
