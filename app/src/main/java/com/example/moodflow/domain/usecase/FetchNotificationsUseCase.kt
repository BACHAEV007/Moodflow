package com.example.moodflow.domain.usecase

import com.example.moodflow.domain.model.NotificationModel
import com.example.moodflow.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class FetchNotificationsUseCase(
	private val repo: SettingsRepository
) {
	operator fun invoke(userId: String): Flow<List<NotificationModel>> =
		repo.notificationsFlow(userId)
}