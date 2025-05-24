package com.example.moodflow.domain.usecase

import com.example.moodflow.domain.repository.SettingsRepository

class CancelAllNotificationUseCase(
	private val repo: SettingsRepository
) {
	suspend operator fun invoke(userId: String) = repo.cancelAllScheduledNotifications(userId)
}