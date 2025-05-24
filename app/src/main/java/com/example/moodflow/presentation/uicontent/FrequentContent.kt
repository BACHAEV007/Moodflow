package com.example.moodflow.presentation.uicontent

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FrequentContent(
    val icon: String,
    val emotion: String,
    val count: Int
): Parcelable
