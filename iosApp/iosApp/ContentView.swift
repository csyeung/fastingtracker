import SwiftUI

struct ContentView: View {
	var body: some View {
	    TabView {
	        TrackerTabView()
	            .tabItem {
	                VStack {
	                    Image(systemName: "Tracker")
	                    Text("タイマー")
	                }
	            }.tag(1)
	        CalendarTabView()
	            .tabItem {
	               VStack {
	                   Image(systemName: "Calendar")
                       Text("カレンター")
	               }
	            }.tag(2)
	        SettingTabView()
	            .tabItem {
	                VStack {
                        Image(systemName: "Setting")
	                    Text("設定")
	                }
	            }.tag(3)
	    }
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}