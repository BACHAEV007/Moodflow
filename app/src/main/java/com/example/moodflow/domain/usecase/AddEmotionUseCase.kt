package com.example.moodflow.domain.usecase

import com.example.moodflow.domain.model.EmotionModel
import com.example.moodflow.domain.repository.AuthRepository
import com.example.moodflow.domain.repository.EmotionRepository

class AddEmotionUseCase(private val authRepository: AuthRepository, private val emotionRepository: EmotionRepository) {
	suspend operator fun invoke(emotionModel: EmotionModel) {
		val userId = authRepository.getCurrentUserId()
			?: throw IllegalStateException("User not authenticated")

		emotionRepository.addEmotion(emotionModel.copy(userId = userId))
	}
}