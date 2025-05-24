package com.example.moodflow

import android.view.View
import com.example.moodflow.presentation.JournalFragment
import com.example.moodflow.presentation.uicontent.CardContent
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.common.views.KView
import io.github.kakaocup.kakao.recycler.KRecyclerItem
import io.github.kakaocup.kakao.recycler.KRecyclerView
import io.github.kakaocup.kakao.text.KTextView
import org.hamcrest.Matcher

object JournalFragmentScreen : KScreen<JournalFragmentScreen>() {
    override val layoutId: Int
        get() = R.layout.journal_screen
    override val viewClass: Class<*>
        get() = JournalFragment::class.java

    private val circularProgressView: KView = KView { withId(R.id.progress_circular) }

    fun checkProgressViewVisibility(isVisible: Boolean) {
        if (isVisible) {
            circularProgressView.isDisplayed()
        } else {
            circularProgressView.isNotDisplayed()
        }
    }

    private val recycler = KRecyclerView(
        builder = { withId(R.id.cards) },
        itemTypeBuilder = {
            itemType(::SavedEmotionItem)
        }
    )



    fun checkRecycler(emotions: List<CardContent>) {
        for(i in emotions.indices) {
            CheckRecyclerItem(i, emotions[i])
        }
    }

    private fun CheckRecyclerItem(position: Int, emotion: CardContent) {
        recycler.scrollTo()
        recycler.childAt<SavedEmotionItem>(position) {
            emotionType.hasText(emotion.feeling)

            timeDate.isDisplayed()
            timeDate.hasText(emotion.data)
        }
    }
}

class SavedEmotionItem(parent: Matcher<View>) : KRecyclerItem<SavedEmotionItem>(parent) {
    val emotionType = KTextView(parent) { withId(R.id.card_emotion_desc) }
    val timeDate = KTextView(parent) { withId(R.id.card_data) }
}