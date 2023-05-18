//
// Created by Jonathan YEUNG on 18/5/2023.
// iosApp
// Copyright © 2023 orgName. All rights reserved.
// 

import Foundation
import Combine

class CalendarTabViewModel : ObservableObject {
    private(set) var didSelectDateSubject: PassthroughSubject<Void, Never> = .init()
    private var cancellables: Set<AnyCancellable> = []
    
    init() {
        subscribeDidSelectDate()
    }
    
    private func subscribeDidSelectDate() {
        didSelectDateSubject
            .receive(on: DispatchQueue.main)
            .sink {
                print("did select")
            }
            .store(in: &cancellables)
    }
}
