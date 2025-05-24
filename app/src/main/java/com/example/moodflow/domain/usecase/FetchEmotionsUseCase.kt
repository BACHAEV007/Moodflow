package com.example.moodflow.domain.usecase

import com.example.moodflow.domain.model.EmotionModel
import com.example.moodflow.domain.repository.AuthRepository
import com.example.moodflow.domain.repository.EmotionRepository
import kotlinx.coroutines.flow.Flow

class FetchEmotionsUseCase(private val authRepository: AuthRepository, private val emotionRepository: EmotionRepository) {
	operator fun invoke(): Flow<List<EmotionModel>>{
		val userId = authRepository.getCurrentUserId()
			?: throw IllegalStateException("User not authenticated")

		return emotionRepository.getAllEmotions(userId)
	}
}