//
//  CourseItem.swift
//  iosApp
//
//  Created by Kim Fransman on 2021-11-02.
//  Copyright © 2021 Fransman. All rights reserved.
//

import SwiftUI
import shared

struct CourseItem: View {
    let course: CoursesQuery.Course
    
    var body: some View {
        HStack(alignment: .top) {
            Text(course.club)
            Text("-")
            Text(course.name)
            Text("\(course.par)")
        }
    }
}

struct CourseItem_Previews: PreviewProvider {
    static var previews: some View {
        Group {
            CourseItem(course: CoursesQuery.Course.init(id: 1, club: "Nynäshamns GK", name: "Sjö-Berg", par: 72, _count: nil))
                .previewLayout(PreviewLayout.sizeThatFits)
                .padding()
        }
    }
}
