//
//  ListView.swift
//  iosApp
//
//  Created by Kim Fransman on 2021-10-19.
//  Copyright © 2021 Fransman. All rights reserved.
//

import SwiftUI
import shared


/*
 var editFeedLink: some View {
    NavigationLink(destination: NavigationLazyView<FeedsList>(FeedsList())) {
        Image(systemName: "pencil.circle").imageScale(.large)
    }
}
 */

struct Leaderboard: ConnectedView {
    
    struct Props {
        let loading: Bool
        let items: [PlayersQuery.Player]
        
        let onReload: (Bool) -> Void
        // let onSelectPlayer: (PlayersQuery.Player?) -> Void
    }
    
    func map(state: LeaderboardState, dispatch: @escaping DispatchFunction) -> Props {
        return Props(
            loading: state.loading,
            items: state.players,
            onReload: { reload in
                dispatch(LeaderboardAction.Refresh(forceLoad: reload))
            }
//            onSelectPlayer: { player in
//                dispatch(LeaderboardAction)
//            }
        )
    }
    
    // @SwiftUI.State private var showSelectFeed = false
    
    init() {
        UITableView.appearance().backgroundColor = .white
    }
    
    func body(props: Props) -> some View {
        VStack {
            LeaderboardFilters()
            List(props.items, rowContent: LeaderboardItem.init)
        }
        .navigationBarTitleDisplayMode(.inline)
        .navigationTitle("Tisdagsgolfen")
        .onAppear {
            props.onReload(true)
        }
    }
    
    
}


extension PlayersQuery.Player: Identifiable { }
