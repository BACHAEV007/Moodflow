package com.example.moodflow.presentation.state

import com.example.moodflow.domain.model.NotificationModel
import com.example.moodflow.domain.model.SettingsUserModel

data class SettingsState(
	val user: SettingsUserModel? = null,
	val notifications: List<NotificationModel> = emptyList(),
	val isLoading: Boolean = false,
	val error: String? = null
)