//
// Created by Jonathan YEUNG on 7/6/2023.
// iosApp
// Copyright © 2023 orgName. All rights reserved.
// 

import SwiftUI
import Lottie

struct LottieView: UIViewRepresentable {
    @Binding var isPlay: Bool // アニメーションの状態管理
    @Binding var isLoop: Bool // ループ設定の状態管理
    
    func makeUIView(context: Context) -> UIView {
        let view = LottieAnimationView(name: "my_lottie_file")
        view.translatesAutoresizingMaskIntoConstraints = false
            
        // 親ビューを用意し、AnimationViewをサブビューにする.
        let parentView = UIView()
        parentView.addSubview(view)
        parentView.addConstraints([
            view.widthAnchor.constraint(equalTo: parentView.widthAnchor),
            view.heightAnchor.constraint(equalTo: parentView.heightAnchor)
        ])

        return parentView
    }
    
    func updateUIView(_ uiView: UIView, context: Context) {
        // LottieAnimationViewを取得
        guard let view = uiView.subviews.compactMap({ $0 as? LottieAnimationView }).first else { return }
        
        // ループ状態によってloopModeを切り替える
        view.loopMode = isLoop ? .loop : .playOnce
        
        if isPlay {
            // すでにアニメーション中なら何もしない
            if view.isAnimationPlaying { return }
            
            // アニメーション開始
            view.play { _ in
                // 終わったら状態をOFFにする
                isPlay = false
            }
        } else {
            // アニメーションを一時停止
            view.pause()
        }
    }
}
