package com.example.moodflow.presentation.state

import com.example.moodflow.presentation.uicontent.CardContent
import com.example.moodflow.presentation.uicontent.GradientColor

data class JournalState(

        val cardsCount: Int = 4,
        val cardInDay: Int = 2,
        val cardStreak: Int = 3,
        val emotions: List<CardContent> = emptyList(),
        val progressMap: Map<GradientColor, Float> = emptyMap(),
        val isLoading: Boolean = false,
        val error: String? = null
)