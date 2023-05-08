import SwiftUI
import shared

struct TrackerTabView: View {
    init() {
         let appearance = UISegmentedControl.appearance()
         let font = UIFont.boldSystemFont(ofSize: 18)
         let foregroundColor = UIColor.systemPink

         // 選択時の背景色
         appearance.selectedSegmentTintColor = foregroundColor

         // 通常時のフォントとフォント色
         appearance.setTitleTextAttributes([
             .font: font,
             .foregroundColor: foregroundColor
         ], for: .normal)

         // 選択時のフォントとフォント色
         appearance.setTitleTextAttributes([
             .font: font,
             .foregroundColor: UIColor.white
         ], for: .selected)
    }
    
    enum FastingType: String, CaseIterable, Identifiable {
        case sixteen = "16:8"
        case twelve = "12:12"
        
        var id: String { rawValue }
    }
    
    @State private var selectedFastingType = FastingType.sixteen
    @ObservedObject private var viewModel = TrackingTabViewModel()

    var body: some View {
        VStack {
            VStack(alignment: .center) {
                Text(NSLocalizedString("Tracker.Title", comment: ""))
                    .font(Font(UIFont.systemFont(ofSize: 30, weight: UIFont.Weight.semibold)))

                Picker(FastingType.sixteen.rawValue, selection: $selectedFastingType) {
                        ForEach(FastingType.allCases) {
                            data in Text(data.rawValue).tag(data)
                        }
                    }
                    .colorMultiply(Color.white)
                    .pickerStyle(.segmented)
                    .padding()
                
                HStack {
                    Text(NSLocalizedString("Tracker.ElapsedTime", comment: ""))
                        .font(Font(UIFont.systemFont(ofSize: 26, weight: UIFont.Weight.semibold)))
                    
                    Spacer()
                    
                    Text(viewModel.elapsedTime)
                        .font(Font(UIFont.systemFont(ofSize: 24, weight: UIFont.Weight.regular)))
                }
                
                HStack {
                    Text(NSLocalizedString("Tracker.RemainingTime", comment: ""))
                        .font(Font(UIFont.systemFont(ofSize: 26, weight: UIFont.Weight.semibold)))
                    
                    Spacer()
                    
                    Text(viewModel.remainingTime)
                        .font(Font(UIFont.systemFont(ofSize: 24, weight: UIFont.Weight.regular)))
                }
                
                HStack {
                    Text(NSLocalizedString("Tracker.StartTime", comment: ""))
                        .font(Font(UIFont.systemFont(ofSize: 26, weight: UIFont.Weight.regular)))
                    
                    Spacer()
                    
                    Text(viewModel.startTime)
                        .font(Font(UIFont.systemFont(ofSize: 18, weight: UIFont.Weight.light)))
                }

                HStack {
                    Text(NSLocalizedString("Tracker.EndTime", comment: ""))
                        .font(Font(UIFont.systemFont(ofSize: 26, weight: UIFont.Weight.regular)))

                    Spacer()
                    
                    Text(viewModel.finishTime)
                        .font(Font(UIFont.systemFont(ofSize: 18, weight: UIFont.Weight.light)))
                }

                Spacer()
                
                Button(NSLocalizedString("Tracker.Button.Title", comment: "")) {
                    viewModel.startFasting()
                }
                .padding(EdgeInsets.init(top: 20, leading: 15, bottom: 20, trailing: 15))
                .background(Color.pink)
                .cornerRadius(15)
                .foregroundColor(Color.white)
            }.padding()
            
            Spacer()
        }.onAppear {
            registerObservables()
        }
    }
    
    private func registerObservables() {
//        viewModel.fetchData()
            viewModel.updateFasting()
    }
}

struct TrackerTabView_Previews: PreviewProvider {
    static var previews: some View {
        TrackerTabView()
    }
}
