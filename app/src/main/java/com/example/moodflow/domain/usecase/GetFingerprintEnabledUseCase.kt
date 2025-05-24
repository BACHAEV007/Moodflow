package com.example.moodflow.domain.usecase

import com.example.moodflow.domain.repository.SettingsPreferences
import kotlinx.coroutines.flow.Flow

class GetFingerprintEnabledUseCase(
	private val repo: SettingsPreferences
) {
	operator fun invoke(): Flow<Boolean> = repo.fingerprintEnabled()
}
