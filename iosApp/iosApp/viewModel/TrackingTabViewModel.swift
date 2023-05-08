//
// Created by Jonathan YEUNG on 6/5/2023.
// iosApp
// Copyright © 2023 orgName. All rights reserved.
// 

import Foundation
import SwiftUI
import shared

class TrackingTabViewModel : ObservableObject {
    @Published var uiState = TrackingUiState.Empty
    
    @State var elapsedTime: String = "00:00"
    @State var remainingTime: String = "00:00"
    @State var startTime: String = "00:00:00"
    @State var finishTime: String = "00:00:00"
    
    let useCase: Greeting = GetUseCases().getGreeting()
            
    func fetchData() async {
        do {
            try await useCase.hasFastingRecord()
            updateFasting()
        } catch {
            uiState = TrackingUiState.Start
        }
    }
    
    func startFasting() {
        uiState = TrackingUiState.Start
        useCase.startFasting()
        updateFasting()
    }
    
    func updateFasting() {
//        uiState = TrackingUiState.Fasting
        
        useCase.updateFastingRecord.collect(
            collector: FlowCollector<FastingRecord> { record in
                self.remainingTime = record.remainingTime ?? "00:00"
                self.elapsedTime = record.elapsedTime ?? "00:00"
                self.startTime = record.startTime ?? "00:00:00"
                self.finishTime = record.endTime ?? "00:00:00"
            },
            completionHandler: { _ in
                
            }
        )
    }
}

