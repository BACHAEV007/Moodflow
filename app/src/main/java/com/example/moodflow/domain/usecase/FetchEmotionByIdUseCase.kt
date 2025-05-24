package com.example.moodflow.domain.usecase

import com.example.moodflow.domain.model.EmotionModel
import com.example.moodflow.domain.repository.EmotionRepository

class FetchEmotionByIdUseCase(private val emotionRepository: EmotionRepository) {
	suspend operator fun invoke(entryId: Long): EmotionModel? {
		return emotionRepository.getEmotionById(entryId)
	}
}