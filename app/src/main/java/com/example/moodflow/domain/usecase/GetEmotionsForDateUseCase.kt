package com.example.moodflow.domain.usecase

import com.example.moodflow.domain.model.EmotionModel
import com.example.moodflow.domain.repository.AuthRepository
import com.example.moodflow.domain.repository.EmotionRepository

class GetEmotionsForDateUseCase(private val authRepository: AuthRepository, private val emotionRepository: EmotionRepository) {
	suspend operator fun invoke(startDate: Long, endDate: Long): List<EmotionModel> {
		val userId = authRepository.getCurrentUserId()
			?: throw IllegalStateException("User not authenticated")
		return emotionRepository.getEmotionsForDateRange(userId, startDate, endDate)
	}
}