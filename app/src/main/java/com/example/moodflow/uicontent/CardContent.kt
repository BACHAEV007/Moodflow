package com.example.moodflow.uicontent

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CardContent(
    val data: String,
    val color: CardColor,
    val feeling: String
) : Parcelable
