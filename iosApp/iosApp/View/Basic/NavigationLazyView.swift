//
//  NavigationLazyView.swift
//  iosApp
//
//  Created by Kim Fransman on 2021-11-05.
//  Copyright © 2021 Fransman. All rights reserved.
//

import SwiftUI

struct NavigationLazyView<Content: View>: View {
    let build: () -> Content
    init(_ build: @autoclosure @escaping () -> Content) {
        self.build = build
    }
    var body: Content {
        build()
    }
}
