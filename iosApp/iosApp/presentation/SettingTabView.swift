import SwiftUI

struct SettingTabView: View {
    @State private var isPlay = false // アニメーションの状態管理
    @State private var isLoop = false // ループ設定の状態管理
    
    var body: some View {
        VStack {
            LottieView(isPlay: $isPlay, isLoop: $isLoop)
                .frame(width: 200, height: 200)
            Toggle("アニメーション", isOn: $isPlay)
            Toggle("ループ", isOn: $isLoop)
        }
        .padding()
    }
}

struct SettingTabView_Previews: PreviewProvider {
    static var previews: some View {
        SettingTabView()
    }
}
