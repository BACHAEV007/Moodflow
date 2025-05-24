package com.example.moodflow.presentation.uicontent

import com.example.moodflow.domain.model.EmotionTypeModel

data class AddEmotionContent(
	val id: Long? = null,
	val timestamp: String = "",
	val emotion: String = "",
	val emotionType: EmotionTypeModel = EmotionTypeModel.RED,
	val iconResName: String? = null,
	val description: String? = null,

	val activities: List<String>? = null,
	val companions: List<String>? = null,
	val locations: List<String>? = null,
)
