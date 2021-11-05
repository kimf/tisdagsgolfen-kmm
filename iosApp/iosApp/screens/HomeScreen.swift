import SwiftUI
import shared

struct HomeScreen: View {
    
    
    var body: some View {
        VStack {
            if homeState.isLoading {
                LoadingScreen()
            } else {
                LeaderboardFilters()
                let data = homeState.players
                ScrollView(.vertical) {
                    VStack(alignment: .leading, spacing: 5) {
                        ForEach(data, id: \.id) { player in
                            LeaderboardItem(player: player)
                        }
                    }.padding(.all, 15).frame(maxWidth: .infinity, alignment: .leading)
                }
            }
        }
    }
}
