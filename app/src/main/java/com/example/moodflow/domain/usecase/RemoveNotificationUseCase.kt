package com.example.moodflow.domain.usecase

import com.example.moodflow.domain.repository.SettingsRepository

class RemoveNotificationUseCase(
	private val repo: SettingsRepository
) {
	suspend operator fun invoke(notificationId: String) {
		repo.removeNotification(notificationId)
	}
}