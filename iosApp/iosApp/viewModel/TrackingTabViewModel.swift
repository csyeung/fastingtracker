//
// Created by Jonathan YEUNG on 6/5/2023.
// iosApp
// Copyright © 2023 orgName. All rights reserved.
// 

import SwiftUI
import shared

@MainActor
class TrackingTabViewModel : ObservableObject {
    @Published var uiState: TrackingUiState = .Empty
    
    @Published var elapsedTime: String? = nil
    @Published var remainingTime: String? = nil
    @Published var startTime: String? = nil
    @Published var finishTime: String? = nil
    
    let useCase: Greeting = GetUseCases().getGreeting()
            
    func fetchData() async {
        do {
            try await useCase.hasFastingRecord()
            updateFasting()
            uiState = .Fasting
        } catch {
            uiState = .Start
        }
    }
    
    func startFasting() {
        uiState = .Fasting
        useCase.startFasting()
        updateFasting()
    }
    
    func updateFasting() {
        self.useCase.updateFastingRecord.collect(
            collector: FlowCollector<FastingRecord> { record in
                self.remainingTime = record.remainingTime
                self.elapsedTime = record.elapsedTime
                self.startTime = record.startTime
                self.finishTime = record.endTime
            },
            completionHandler: { _ in
                
            }
        )
    }
    
    func stopFasting() {
        useCase.stopFasting()
        uiState = .Stop
        
        self.startTime = "00:00:00"
        self.finishTime = "00:00:00"
    }
}
