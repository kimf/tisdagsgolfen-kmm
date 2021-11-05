//
//  PlayerSetup.swift
//  iosApp
//
//  Created by Kim Fransman on 2021-11-02.
//  Copyright © 2021 Fransman. All rights reserved.
//

import SwiftUI
import shared

struct GameSetup: ConnectedView {
    func map(state: LeaderboardState, dispatch: @escaping DispatchFunction) -> Props {
        return Props(
            course: CoursesQuery.Course.init(id: 1, club: "Nynäshamns GK", name: "Sjö-Berg", par: 72, _count: nil),
            players: state.players
        )
    }
    
    
    struct Props {
        let course: CoursesQuery.Course
        let players: [PlayersQuery.Player]
    }
    
    @SwiftUI.State private var specialWeek = false
    @SwiftUI.State private var teamEvent = false
    @SwiftUI.State private var strokes = false
    

    func body(props: Props) -> some View {
        VStack(alignment: .leading, spacing: 20) {
            Form {
                CourseItem(course: props.course)
                Toggle("Specialvecka?", isOn: $specialWeek)
                Toggle("Lagtävling?", isOn: $teamEvent)
                Toggle("Slag?", isOn: $strokes)
                Section {
                    ForEach(props.players, id:\.id) { player in
                        PlayerRow(player: player)
                    }
                }
                Button("BÖRJA SPELA", action: { print("Lets play") })
            }
        }
    }
}

struct GameSetup_Previews: PreviewProvider {
    static var previews: some View {
        // var course = CoursesQuery.Course.init(id: 1, club: "Nynäshamns GK", name: "Sjö-Berg", par: 72, _count: nil)
        GameSetup()
    }
}
