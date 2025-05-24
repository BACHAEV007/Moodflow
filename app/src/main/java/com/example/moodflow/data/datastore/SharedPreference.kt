package com.example.moodflow.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.example.moodflow.utils.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager(
	private val context: Context
) {
	private object Keys {
		val NOTIFICATIONS_ENABLED = booleanPreferencesKey("notifications_enabled")
		val FINGERPRINT_ENABLED   = booleanPreferencesKey("fingerprint_enabled")
	}

	private val dataStore = context.dataStore

	fun notificationsEnabled(): Flow<Boolean> =
		dataStore.data.map { prefs ->
			prefs[Keys.NOTIFICATIONS_ENABLED] ?: true
		}

	suspend fun setNotificationsEnabled(enabled: Boolean) {
		dataStore.edit { prefs ->
			prefs[Keys.NOTIFICATIONS_ENABLED] = enabled
		}
	}

	fun fingerprintEnabled(): Flow<Boolean> =
		dataStore.data.map { prefs ->
			prefs[Keys.FINGERPRINT_ENABLED] ?: false
		}

	suspend fun setFingerprintEnabled(enabled: Boolean) {
		dataStore.edit { prefs ->
			prefs[Keys.FINGERPRINT_ENABLED] = enabled
		}
	}
}