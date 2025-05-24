package com.example.moodflow

import android.view.View
import com.example.moodflow.presentation.BottomNavigationActivity
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.common.views.KView
import io.github.kakaocup.kakao.recycler.KRecyclerItem
import io.github.kakaocup.kakao.recycler.KRecyclerView
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView
import org.hamcrest.Matcher

object NavigationScreen : KScreen<NavigationScreen>() {

    override val layoutId: Int = R.layout.bottom_navigation
    override val viewClass: Class<*> = BottomNavigationActivity::class.java

    val journalFragmentButton = KButton { withId(R.id.addEmotionButton) }
    val chooseEmotionFragmentButton = KButton { withId(R.id.forward_button_allow) }
    val chooseEmotionFragmentButtonDisable = KButton { withId(R.id.forward_button) }
    val addNotionFragmentButton = KButton { withId(R.id.add_emotion_notion_button) }
    val gridRecyclerView = KRecyclerView(builder = { withId(R.id.choose_emotion_container) },
        itemTypeBuilder = {
            itemType(::EmotionItem)
        })
    val journalFragmentContent = KView { withId(R.id.journalFragment) }
    val SettingsFragmentContent = KView { withId(R.id.settingsFragment) }
    val StatisticFragmentContent = KView { withId(R.id.vertical_pager) }

    val statisticFragmentButtonMenu = KButton { withId(R.id.statisticFragment) }
    val settingsFragmentButtonMenu = KButton { withId(R.id.settingsFragment) }
}

class EmotionItem(parent: Matcher<View>) : KRecyclerItem<EmotionItem>(parent) {
    val timeText = KTextView(parent) { withId(R.id.circle_emotion_text) }
    val itemView = KView(parent) {}
}
