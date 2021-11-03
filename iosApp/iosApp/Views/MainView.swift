import SwiftUI
import shared

struct MainView: View {
    @EnvironmentObject var appObj: AppObservableObject

    var body: some View {
        let dkmpNav = appObj.dkmpNav
        dkmpNav.router()
    }
}

struct MainView_Previews: PreviewProvider {
    static var previews: some View {
        MainView()
    }
}

/*
    NavigationView {
            GeometryReader { metrics in
                VStack {
                    Leaderboard()
                    Events().frame(maxHeight: metrics.size.height / 4)
                }
            }
            .navigationTitle("Tisdagsgolfen")
            .navigationBarTitleDisplayMode(.inline)
            .toolbar {
                ToolbarItem(placement: .navigationBarTrailing) {
                    NavigationLink(destination: SetupRoundView()) {
                        Text("SPELA!")
                    }
                }
            }
        }
  */
