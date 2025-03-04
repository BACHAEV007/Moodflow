package com.example.moodflow

import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.common.views.KView
import io.github.kakaocup.kakao.text.KTextView

object EmotionCategoriesFragmentScreen : KScreen<EmotionCategoriesFragmentScreen>() {
    override val layoutId: Int
        get() = R.layout.emotion_categories
    override val viewClass: Class<*>
        get() = EmotionCategoriesFragment::class.java

    private val circularDiagramView: KView = KView { withId(R.id.circle_diagram_view) }
    private val emotionCategoryText: KTextView = KTextView { withId(R.id.emotionCategoryText) }
    fun checkCircularDiagramViewVisibility(isVisible: Boolean) {
        if (isVisible) {
            circularDiagramView.isDisplayed()
        } else {
            circularDiagramView.isNotDisplayed()
        }
    }

    fun checkEmotionCategoryText(input: String) {
        emotionCategoryText.isDisplayed()
        emotionCategoryText.hasText(input)
    }
}