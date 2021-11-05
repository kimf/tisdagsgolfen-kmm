import SwiftUI
import shared

struct MainView: View {
    @EnvironmentObject var appObj: AppObservableObject

    var body: some View {
        NavigationView {
            GeometryReader { metrics in
                VStack {
                    HomeScreen()
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
    }
}

struct MainView_Previews: PreviewProvider {
    static var previews: some View {
        MainView()
    }
}
