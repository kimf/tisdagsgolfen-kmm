//
//  SetupRoundView.swift
//  iosApp
//
//  Created by Kim Fransman on 2021-11-02.
//  Copyright © 2021 Fransman. All rights reserved.
//

import SwiftUI

struct SetupRoundView: View {
    @StateObject var viewModel = AppViewModel()

    var body: some View {
        List {
            ForEach(viewModel.courses, id:\.id) { course in
                NavigationLink(destination: GameSetup(course: course)) {
                    CourseItem(course: course)
                }
            }
        }.listStyle(GroupedListStyle())
        .task {
            await viewModel.getCourses()
        }.navigationTitle("Välj bana")
    }
}
