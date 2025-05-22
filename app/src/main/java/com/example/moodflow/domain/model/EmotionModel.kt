package com.example.moodflow.domain.model

data class EmotionModel(
	val entryId: Long,
	val userId: String,
	val emotion: String,
	val timestamp: Long,
	val emotionType: EmotionTypeModel,
	val iconResName: String? = null,
	val description: String? = null,

	val activities: List<String>? = null,
	val companions: List<String>? = null,
	val locations: List<String>? = null,
)
