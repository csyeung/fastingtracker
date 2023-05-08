//
// Created by Jonathan YEUNG on 8/5/2023.
// iosApp
// Copyright © 2023 orgName. All rights reserved.
// 

import SwiftUI
import shared

class FlowCollector<T> : Kotlinx_coroutines_coreFlowCollector {
    let callback:(T) -> Void

    init(callback: @escaping (T) -> Void) {
        self.callback = callback
    }

    func emit(value: Any?) async throws -> Void   {
        if let updatedValue = value as? T {
          callback(updatedValue)
        }
        return Void()
    }
}
