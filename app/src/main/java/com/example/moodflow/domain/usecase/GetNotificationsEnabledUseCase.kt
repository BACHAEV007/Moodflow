package com.example.moodflow.domain.usecase

import com.example.moodflow.domain.repository.SettingsPreferences
import kotlinx.coroutines.flow.Flow

class GetNotificationsEnabledUseCase(
	private val repo: SettingsPreferences
) {
	operator fun invoke(): Flow<Boolean> = repo.notificationsEnabled()
}
