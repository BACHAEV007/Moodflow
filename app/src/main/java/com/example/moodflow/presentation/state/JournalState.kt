package com.example.moodflow.presentation.state

import com.example.moodflow.presentation.uicontent.CardColor
import com.example.moodflow.presentation.uicontent.CardContent

data class JournalState(

        val cardsCount: Int = 4,
        val cardInDay: Int = 2,
        val cardStreak: Int = 3,
        val emotions: List<CardContent> = emptyList(),
        val isLoading: Boolean = false,
        val error: String? = null
)