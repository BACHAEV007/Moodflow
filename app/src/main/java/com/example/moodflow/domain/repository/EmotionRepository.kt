package com.example.moodflow.domain.repository

import com.example.moodflow.domain.model.EmotionModel
import kotlinx.coroutines.flow.Flow

interface EmotionRepository {
	suspend fun getEmotionById(entryId: Long): EmotionModel?
	fun getAllEmotions(userId: String): Flow<List<EmotionModel>>
	suspend fun addEmotion(emotionModel: EmotionModel)
	suspend fun getEmotionsForDateRange(uid: String, startDate: Long, endDate: Long): List<EmotionModel>
}