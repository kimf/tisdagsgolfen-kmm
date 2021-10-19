//
//  ListRowView.swift
//  iosApp
//
//  Created by Kim Fransman on 2021-10-19.
//  Copyright © 2021 Fransman. All rights reserved.
//


import SwiftUI
import shared

struct ListRowView: View {
    let side: CubeQuery.Side
    
    var body: some View {
        HStack(alignment: .top, spacing: 20) {
            Text(side.id?.uppercased() ?? "-")
                .font(.title3)
                .foregroundColor(.accentColor)
            Text("\(side.red ?? 0)")
                .font(.footnote)
                .foregroundColor(.gray)
            Text("\(side.green ?? 0)")
                .font(.footnote)
                .foregroundColor(.gray)
            Text("\(side.blue ?? 0)")
                .font(.footnote)
                .foregroundColor(.gray)
            Rectangle().fill(
                Color.init(UIColor(side: side))
            ).frame(width: 60, height: 60)
        }
    }
}

struct ListRowViews_Previews: PreviewProvider {
    static var previews: some View {
        Group {
            ListRowView(side: CubeQuery.Side.init(id: "top", red: 123, green: 140, blue: 20))
                .previewLayout(PreviewLayout.sizeThatFits)
                .padding()
                .previewDisplayName("Default preview 1")
            ListRowView(side: CubeQuery.Side.init(id: "top", red: 123, green: 140, blue: 20))
                .padding()
                .previewLayout(PreviewLayout.sizeThatFits)
                .background(Color(.systemBackground))
                .environment(\.colorScheme, .dark)
                .previewDisplayName("Dark Mode preview 1")
        }
    }
}


extension UIColor {
    convenience init(side: CubeQuery.Side) {
        self.init(red: side.red as! Int, green: side.green as! Int, blue: side.blue as! Int)
    }
    
   convenience init(red: Int, green: Int, blue: Int) {
       assert(red >= 0 && red <= 255, "Invalid red component")
       assert(green >= 0 && green <= 255, "Invalid green component")
       assert(blue >= 0 && blue <= 255, "Invalid blue component")

       self.init(red: CGFloat(red) / 255.0, green: CGFloat(green) / 255.0, blue: CGFloat(blue) / 255.0, alpha: 1.0)
   }

   convenience init(rgb: Int) {
       self.init(
           red: (rgb >> 16) & 0xFF,
           green: (rgb >> 8) & 0xFF,
           blue: rgb & 0xFF
       )
   }
}
