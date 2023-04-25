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
    
    var body: some View {
        NavigationView {
            List {
                Picker(FastingType.sixteen.rawValue, selection: $selectedFastingType) {
                    ForEach(FastingType.allCases) {
                        data in Text(data.rawValue).tag(data)
                    }
                }
                .colorMultiply(Color.white)
                .pickerStyle(.segmented)
                .padding()

                HStack {
                    
                }
            }
            .navigationTitle(NSLocalizedString("Tracker.Title", comment: ""))
        }
    }
}

struct TrackerTabView_Previews: PreviewProvider {
    static var previews: some View {
        TrackerTabView()
    }
}
