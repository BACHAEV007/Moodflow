package com.example.moodflow.data.repository

import com.example.moodflow.data.dao.EmotionDao
import com.example.moodflow.data.mapper.toEmotion
import com.example.moodflow.data.mapper.toEmotionModel
import com.example.moodflow.domain.model.EmotionModel
import com.example.moodflow.domain.repository.EmotionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class EmotionRepositoryImpl(
	private val emotionDao: EmotionDao
) : EmotionRepository {

	override suspend fun getEmotionById(entryId: Long): EmotionModel? {
		val emotion = emotionDao.getEmotionById(entryId)
		return emotion?.toEmotionModel()
	}

	override fun getAllEmotions(userId: String): Flow<List<EmotionModel>> {
		return emotionDao.getEmotionsForUser(userId)
			.map { list -> list.map { it.toEmotionModel() } }
	}


	override suspend fun addEmotion(emotionModel: EmotionModel) {
		val emotion = emotionModel.toEmotion()
		emotionDao.insertEmotion(emotion)
	}

	override suspend fun getEmotionsForDateRange(uid: String, startDate: Long, endDate: Long): List<EmotionModel> {
		return emotionDao.getEmotionsForDateRange(uid, startDate = startDate, endDate = endDate).map { it.toEmotionModel() }
	}
}