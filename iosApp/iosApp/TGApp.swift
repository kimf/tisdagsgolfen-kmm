import Foundation
import SwiftUI
import shared

@main
class TGApp: App {
    let app: Tisdagsgolfen
    let store: ObservableLeaderboardStore
    
    required init() {
        app = Tisdagsgolfen.Companion().create(withLog: true)
        store = ObservableLeaderboardStore(store: LeaderboardStore(app: app))
    }
    
    var body: some Scene {
        WindowGroup {
            RootView().environmentObject(store)
        }
    }
}

class ObservableLeaderboardStore: ObservableObject {
    @Published public var state: LeaderboardState =  LeaderboardState(loading: false, players: [])
    @Published public var sideEffect: LeaderboardSideEffect?
    
    let store: LeaderboardStore
    
    var stateWatcher : Closeable?
    var sideEffectWatcher : Closeable?

    init(store: LeaderboardStore) {
        self.store = store
        stateWatcher = self.store.watchState().watch { [weak self] state in
            self?.state = state
        }
        sideEffectWatcher = self.store.watchSideEffect().watch { [weak self] state in
            self?.sideEffect = state
        }
    }
    
    public func dispatch(_ action: LeaderboardAction) {
        store.dispatch(action: action)
    }
    
    deinit {
        stateWatcher?.close()
        sideEffectWatcher?.close()
    }
}

public typealias DispatchFunction = (LeaderboardAction) -> ()

public protocol ConnectedView: View {
    associatedtype Props
    associatedtype V: View
    
    func map(state: LeaderboardState, dispatch: @escaping DispatchFunction) -> Props
    func body(props: Props) -> V
}

public extension ConnectedView {
    func render(state: LeaderboardState, dispatch: @escaping DispatchFunction) -> V {
        let props = map(state: state, dispatch: dispatch)
        return body(props: props)
    }
    
    var body: StoreConnector<V> {
        return StoreConnector(content: render)
    }
}

public struct StoreConnector<V: View>: View {
    @EnvironmentObject var store: ObservableLeaderboardStore
    let content: (LeaderboardState, @escaping DispatchFunction) -> V
    
    public var body: V {
        return content(store.state, store.dispatch)
    }
}
