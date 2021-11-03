//
//  PlayerSetup.swift
//  iosApp
//
//  Created by Kim Fransman on 2021-11-02.
//  Copyright © 2021 Fransman. All rights reserved.
//

import SwiftUI
import shared

struct GameSetup: View {
    let course: CoursesQuery.AllCourse
    @StateObject var viewModel = AppViewModel()
    
    @State private var specialWeek = false
    @State private var teamEvent = false
    @State private var strokes = false
    

    var body: some View {
        VStack(alignment: .leading, spacing: 20) {
            Form {
                CourseItem(course: course)
                Toggle("Specialvecka?", isOn: $specialWeek)
                Toggle("Lagtävling?", isOn: $teamEvent)
                Toggle("Slag?", isOn: $strokes)
                Section {
                    ForEach(viewModel.players, id:\.id) { player in
                        PlayerRow(player: player)
                    }
                }
                Button("BÖRJA SPELA", action: { print("Lets play") })
            }
        }.task {
            await viewModel.getPlayers()
        }
    }
}

struct GameSetup_Previews: PreviewProvider {
    static var previews: some View {
        GameSetup(course: CoursesQuery.AllCourse.init(id: 1, club: "Nynäshamns GK", name: "Sjö-Berg", par: 72))
    }
}
