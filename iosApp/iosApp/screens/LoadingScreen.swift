import SwiftUI

struct LoadingScreen: View {
    var body: some View {
        VStack {
            Spacer()
            Text("loading...")
            Spacer().frame(height: 30)
            ProgressView().progressViewStyle(CircularProgressViewStyle())
            Spacer()
        }
    }
}