//
//  RootView.swift
//  iosApp
//
//  Created by Kim Fransman on 2021-11-05.
//  Copyright © 2021 Fransman. All rights reserved.
//

import SwiftUI
import shared

struct RootView: View {
    @EnvironmentObject var store: ObservableLeaderboardStore
    @SwiftUI.State var errorMessage: String?
    
    var body: some View {
        ZStack {
            NavigationView {
                Leaderboard()
            }.zIndex(0)
            if let errorMessage = self.errorMessage {
                VStack {
                    Spacer()
                    Text(errorMessage)
                        .foregroundColor(.white)
                        .padding(10.0)
                        .background(Color.black)
                        .cornerRadius(3.0)
                }
                .padding(.bottom, 10)
                .zIndex(1)
                .transition(.asymmetric(insertion: .move(edge: .bottom), removal: .opacity) )
            }
        }
        .navigationViewStyle(StackNavigationViewStyle())
        .onReceive(store.$sideEffect) { value in
            if let errorMessage = (value as? LeaderboardSideEffect.Error)?.error.message {
                withAnimation { self.errorMessage = errorMessage }
                DispatchQueue.main.asyncAfter(deadline: .now() + 2.0) {
                    withAnimation { self.errorMessage = nil }
                }
            }
        }
    }
}
