package com.example.moodflow.state

import com.example.moodflow.uicontent.CardColor
import com.example.moodflow.uicontent.CardContent

sealed interface JournalState {
    data class Content(
        val cardsCount: Int = 4,
        val cardInDay: Int = 2,
        val cardStreak: Int = 3,
        val cards: List<CardContent> = listOf(
            CardContent(data = "вчера, 23:40", color = CardColor.GREEN, feeling = "спокойствие"),
            CardContent(data = "вчера, 23:40", color = CardColor.BLUE, feeling = "выгорание"),
            CardContent(data = "воскресенье, 23:40", color = CardColor.YELLOW, feeling = "продуктивность"),
            CardContent(data = "воскресенье, 23:40", color = CardColor.RED, feeling = "беспокойство")
        )
    ) : JournalState
}