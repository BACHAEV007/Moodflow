package com.example.moodflow.presentation.uicontent

data class WeekEmotionContent(
    val emotions: List<String> = listOf("Спокойствие", "Продуктивность","Счастье"),
    val icons: List<Int>,
    val date: String = "17 фев"
)
