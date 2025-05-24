package com.example.moodflow.domain.usecase

import com.example.moodflow.domain.repository.SettingsPreferences

class SetFingerprintEnabledUseCase(
	private val repo: SettingsPreferences
) {
	suspend operator fun invoke(enabled: Boolean) =
		repo.setFingerprintEnabled(enabled)
}