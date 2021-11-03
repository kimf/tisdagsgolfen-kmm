import SwiftUI
import shared


extension Navigation {

    @ViewBuilder func onePane(_ level1ScreenIdentifier: ScreenIdentifier) -> some View {
        NavigationView {
            self.screenPicker(level1ScreenIdentifier)
                .navigationBarTitle(getTitle(screenIdentifier: level1ScreenIdentifier), displayMode: .inline)
        }
        .navigationViewStyle(StackNavigationViewStyle())
    }

}
