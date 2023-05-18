import SwiftUI

struct CalendarTabView: View {
    var body: some View {
        VStack {
            CalendarView()
            Divider()
            Text("Calendar View")
                .frame(height: 300, alignment: .bottom)
        }
    }
}

struct CalendarTabView_Previews: PreviewProvider {
    static var previews: some View {
        CalendarTabView()
    }
}
