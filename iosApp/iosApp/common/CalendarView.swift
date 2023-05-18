//
// Created by Jonathan YEUNG on 18/5/2023.
// iosApp
// Copyright © 2023 orgName. All rights reserved.
// 

import SwiftUI

struct CalendarView: UIViewRepresentable {
    func makeCoordinator() -> Coordinator {
        Coordinator(self)
    }

    func makeUIView(context: Context) -> UICalendarView {
        let view = UICalendarView()
        let selection = UICalendarSelectionSingleDate(delegate: context.coordinator)
        view.selectionBehavior = selection
        return view
    }
    
    func updateUIView(_ uiView: UICalendarView, context: Context) {
    }
    
    class Coordinator: NSObject, UICalendarSelectionSingleDateDelegate {
        private let parent: CalendarView
        
        init(_ parent: CalendarView) {
            self.parent = parent
        }
        
        func dateSelection(_ selection: UICalendarSelectionSingleDate, didSelectDate dateComponents: DateComponents?) {
            print("did select")
        }
    }
}
