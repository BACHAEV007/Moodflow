package com.example.moodflow.domain.usecase

import com.example.moodflow.domain.repository.SettingsRepository

class AddNotificationUseCase(
	private val repo: SettingsRepository
) {
	suspend operator fun invoke(userId: String, time: String) {
		repo.addNotification(userId, time)
	}
}