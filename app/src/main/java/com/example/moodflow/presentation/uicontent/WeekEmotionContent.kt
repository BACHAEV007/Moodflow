package com.example.moodflow.presentation.uicontent

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeekEmotionContent(
    val emotions: List<String> = listOf("Спокойствие", "Продуктивность","Счастье"),
    val icons: List<String>,
    val date: String = "17 фев"
) : Parcelable
