import SwiftUI

struct ContentView: View {
	var body: some View {
	    TabView {
	        TrackerTabView()
	            .tabItem {
	                VStack {
	                    Image(systemName: "timer")
	                    Text(
                            NSLocalizedString("Content.Timer.Title", comment: "")
                        )
	                }
	            }.tag(1)
	        RecordTabView()
	            .tabItem {
	               VStack {
	                   Image(systemName: "menubar.dock.rectangle.badge.record")
                       Text(
                        NSLocalizedString("Content.Record.Title", comment: "")
                       )
	               }
	            }.tag(2)
	        CalendarTabView()
	            .tabItem {
	               VStack {
	                   Image(systemName: "calendar")
                       Text(
                        NSLocalizedString("Content.Calendar.Title", comment: "")
                       )
	               }
	            }.tag(3)
	        SettingTabView()
	            .tabItem {
	                VStack {
                        Image(systemName: "iphone.gen1.circle")
	                    Text(
                            NSLocalizedString("Content.Setting.Title", comment: "")
                        )
	                }
	            }.tag(4)
	    }
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
