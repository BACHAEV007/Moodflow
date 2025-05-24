package com.example.moodflow.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsPreferences {
	fun notificationsEnabled(): Flow<Boolean>
	suspend fun setNotificationsEnabled(enabled: Boolean)
	fun fingerprintEnabled(): Flow<Boolean>
	suspend fun setFingerprintEnabled(enabled: Boolean)
}