import SwiftUI
import shared

struct HomeScreen: View {
    var homeState: HomeState
    
    var body: some View {
        VStack {
            if homeState.isLoading {
                LoadingScreen()
            } else {
                let data = homeState.players
                ScrollView(.vertical) {
                    VStack(alignment: .leading, spacing: 5) {
                        ForEach(data, id: \.id) { player in
                            Text("\(player.firstName) \(player.lastName)").font(Font.callout)
                        }
                    }.padding(.all, 15).frame(maxWidth: .infinity, alignment: .leading)
                }
            }
        }
    }
}
