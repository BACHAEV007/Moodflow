package com.example.moodflow

import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.common.views.KView

object EmotionCategoriesFragmentScreen : KScreen<EmotionCategoriesFragmentScreen>() {
    override val layoutId: Int
        get() = R.layout.emotion_categories
    override val viewClass: Class<*>
        get() = EmotionCategoriesFragment::class.java

    private val circularDiagramView: KView = KView { withId(R.id.circle_diagram_view) }

    fun checkCircularDiagramViewVisibility(isVisible: Boolean) {
        if (isVisible) {
            circularDiagramView.isDisplayed()
        } else {
            circularDiagramView.isNotDisplayed()
        }
    }
}