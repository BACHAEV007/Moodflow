package com.example.moodflow.presentation.uicontent

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CardContent(
	val id: Long? = null,
	val data: String,
	val color: CardColor,
	val feeling: String
) : Parcelable
