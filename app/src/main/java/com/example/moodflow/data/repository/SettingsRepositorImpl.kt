package com.example.moodflow.data.repository

import com.example.moodflow.data.dao.NotificationDao
import com.example.moodflow.data.datastore.DataStoreManager
import com.example.moodflow.data.entity.NotificationEntity
import com.example.moodflow.domain.model.NotificationModel
import com.example.moodflow.domain.repository.SettingsPreferences
import com.example.moodflow.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsRepositoryImpl(
	private val dao: NotificationDao,
	private val notificationHelper: NotificationHelper,
	private val dataStoreManager: DataStoreManager
) : SettingsRepository, SettingsPreferences {

	override fun notificationsFlow(userId: String): Flow<List<NotificationModel>> {
		return dao.getForUser(userId)
			.map { list ->
				list.map { ent ->
					NotificationModel(id = ent.id, time = ent.time)
				}
			}
	}

	override suspend fun addNotification(userId: String, time: String) {
		val entity = NotificationEntity(
			userOwnerId = userId,
			time = time
		)
		dao.insert(entity)
		val (hour, minute) = time.split(":").let { it[0].toInt() to it[1].toInt() }
		notificationHelper.scheduleNotification(entity.id.hashCode(), hour, minute)
	}

	override suspend fun removeNotification(notificationId: String) {
		dao.deleteById(notificationId)
		notificationHelper.cancelScheduled(notificationId.hashCode())
	}

	override suspend fun rescheduleAllNotifications(userId: String) {
		val list = dao.getForUserOnce(userId)
		list.forEach { ent ->
			val (hour, minute) = ent.time.split(":")
				.let { it[0].toInt() to it[1].toInt() }
			notificationHelper.scheduleNotification(ent.id.hashCode(), hour, minute)
		}
	}

	override suspend fun cancelAllScheduledNotifications(userId: String) {
		val list = dao.getForUserOnce(userId)
		list.forEach { ent ->
			notificationHelper.cancelScheduled(ent.id.hashCode())
		}
	}

	override fun notificationsEnabled(): Flow<Boolean> =
		dataStoreManager.notificationsEnabled()

	override suspend fun setNotificationsEnabled(enabled: Boolean) {
		dataStoreManager.setNotificationsEnabled(enabled)
	}

	override fun fingerprintEnabled(): Flow<Boolean> =
		dataStoreManager.fingerprintEnabled()

	override suspend fun setFingerprintEnabled(enabled: Boolean) {
		dataStoreManager.setFingerprintEnabled(enabled)
	}
}