package com.example.moodflow

import com.example.moodflow.presentation.MainActivity
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView

internal object MainActivityScreen : KScreen<MainActivityScreen>() {
    override val layoutId: Int
        get() = R.layout.activity_main
    override val viewClass: Class<*>
        get() = MainActivity::class.java

    private val welcomeText = KTextView { withId(R.id.welcomeText) }
    private val welcomeButton = KButton { withId(R.id.welcome_button) }

    fun checkText(input: String) {
        welcomeText {
            hasText(input)
        }
    }

    fun clickWelcomeButton(){
        welcomeButton {
            isDisplayed()
        }
    }

}