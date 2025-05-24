package com.example.moodflow.domain.repository

import com.example.moodflow.domain.model.NotificationModel
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
	fun notificationsFlow(userId: String): Flow<List<NotificationModel>>
	suspend fun addNotification(userId: String, time: String)
	suspend fun removeNotification(notificationId: String)
	suspend fun rescheduleAllNotifications(userId: String)
	suspend fun cancelAllScheduledNotifications(userId: String)
}