package com.example.moodflow

import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.text.KButton

object ChooseEmotionFragmentScreen : KScreen<ChooseEmotionFragmentScreen>() {
    override val layoutId: Int
        get() = R.layout.choose_emotion_screen
    override val viewClass: Class<*>
        get() = ChooseEmotionFragment::class.java

    val ChooseEmotionFragmentBackButton: KButton = KButton { withId(R.id.choose_back_button) }
}
