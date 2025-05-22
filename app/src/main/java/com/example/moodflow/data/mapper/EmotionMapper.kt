package com.example.moodflow.data.mapper

import com.example.moodflow.data.entity.Emotion
import com.example.moodflow.data.entity.EmotionType
import com.example.moodflow.domain.model.EmotionModel
import com.example.moodflow.domain.model.EmotionTypeModel

fun Emotion.toEmotionModel(): EmotionModel = EmotionModel(
	entryId = this.entryId,
	userId = this.userId,
	emotion = this.emotion,
	timestamp = this.timestamp,
	emotionType = this.emotionType.toEmotionTypeModel(),
	iconResName = this.iconResName,
	description = this.description,
	activities = this.activities,
	companions = this.companions,
	locations = this.locations
)

fun EmotionModel.toEmotion(): Emotion = Emotion(
	entryId = this.entryId,
	userId = this.userId,
	emotion = this.emotion,
	timestamp = this.timestamp,
	emotionType = this.emotionType.toEmotionType(),
	iconResName = this.iconResName,
	description = this.description,
	activities = this.activities,
	companions = this.companions,
	locations = this.locations
)

fun EmotionType.toEmotionTypeModel(): EmotionTypeModel = when (this) {
	EmotionType.RED -> EmotionTypeModel.RED
	EmotionType.BLUE -> EmotionTypeModel.BLUE
	EmotionType.GREEN -> EmotionTypeModel.GREEN
	EmotionType.YELLOW -> EmotionTypeModel.YELLOW
}

fun EmotionTypeModel.toEmotionType(): EmotionType = when (this) {
	EmotionTypeModel.RED -> EmotionType.RED
	EmotionTypeModel.BLUE -> EmotionType.BLUE
	EmotionTypeModel.GREEN -> EmotionType.GREEN
	EmotionTypeModel.YELLOW -> EmotionType.YELLOW
}