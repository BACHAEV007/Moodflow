package com.example.moodflow

import android.view.View
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.recycler.KRecyclerItem
import io.github.kakaocup.kakao.recycler.KRecyclerView
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView
import org.hamcrest.Matcher

object SettingsFragmentScreen : KScreen<SettingsFragmentScreen>() {
    override val layoutId: Int
        get() = R.layout.settings_screen
    override val viewClass: Class<*>
        get() = SettingsFragment::class.java

    val settingsFragmentAddNotionButton: KButton = KButton { withId(R.id.add_notion_button) }

    private val recycler = KRecyclerView(
        builder = { withId(R.id.notionRecycler) },
        itemTypeBuilder = {
            itemType(::NotificationItem)
        }
    )

    fun checkRecycler(notifications: List<String>) {
        for (i in notifications.indices) {
            CheckRecyclerItem(i, notifications[i])
        }
    }

    private fun CheckRecyclerItem(position: Int, time: String) {
        recycler.scrollTo()
        recycler.childAt<NotificationItem>(position) {
            timeText.hasText(time)
            timeText.isDisplayed()
        }
    }
}

class NotificationItem(parent: Matcher<View>) : KRecyclerItem<NotificationItem>(parent) {
    val timeText = KTextView(parent) { withId(R.id.time_text) }
}