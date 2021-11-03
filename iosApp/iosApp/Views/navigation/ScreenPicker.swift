import SwiftUI
import shared

extension Navigation {
    @ViewBuilder func screenPicker(_ sId: ScreenIdentifier) -> some View {
        VStack {
            switch sId.screen {

            case .home:
                HomeScreen(homeState: self.stateProvider.getToCast(screenIdentifier: sId) as! HomeState)
            default:
                EmptyView()
            }

        }
    }
}
