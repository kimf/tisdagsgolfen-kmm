import SwiftUI
import shared


extension Navigation {
    @ViewBuilder func router() -> some View {

        ZStack {
            ForEach(self.level1ScreenIdentifiers, id: \.self.URI) { screenIdentifier in
                self.onePane(screenIdentifier).opacity(screenIdentifier.URI == self.currentLevel1ScreenIdentifier.URI ? 1 : 0)
            }
        }
        .navigationBarColor(backgroundUIColor: UIColor(customBgColor), tintUIColor: .white)
        .toolbarColor(backgroundUIColor: UIColor(customBgColor), tintUIColor: .white)

    }

    func navigate(_ screen: Screen, _ params: ScreenParams?) -> ScreenIdentifier {
        return ScreenIdentifier.Factory().get(screen: screen, params: params)
    }

}



struct NavLink<Content: View>: View {
    var linkFunction: () -> ScreenIdentifier
    let content: () -> Content

    @EnvironmentObject var appObj: AppObservableObject
    @State private var selected : Bool = false
    var body: some View {
        let isActive = Binding<Bool> (
            get: {
                selected && appObj.dkmpNav.isInCurrentVerticalBackstack(screenIdentifier: linkFunction())
            },
            set: { isActive in
                if isActive {
                    let screenIdentifier = linkFunction()
                    appObj.dkmpNav.navigateByScreenIdentifier(screenIdentifier: screenIdentifier)
                    self.selected = true
                }
            }
        )
        NavigationLink(
            destination: LazyDestinationView(
                appObj.dkmpNav.screenPicker(linkFunction())
                    .navigationBarTitle(appObj.dkmpNav.getTitle(screenIdentifier: linkFunction()), displayMode: .inline)
                    .onDisappear {
                        let screenIdentifier = linkFunction()
                        //print("onDisappear: "+screenIdentifier.URI)
                        if appObj.dkmpNav.isInCurrentVerticalBackstack(screenIdentifier: screenIdentifier) {
                            //print("confimed disappear")
                            self.selected = false
                            isActive.wrappedValue = false
                            appObj.dkmpNav.exitScreen(screenIdentifier: screenIdentifier, triggerRecomposition: false)
                        }
                    }
            ),
            isActive: isActive
        ) {
            content()
        }
    }
}


struct LazyDestinationView<Content: View>: View {
    let build: () -> Content
    init(_ build: @autoclosure @escaping () -> Content) {
        self.build = build
    }
    var body: Content {
        build()
    }
}
