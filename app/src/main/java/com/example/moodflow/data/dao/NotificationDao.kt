package com.example.moodflow.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moodflow.data.entity.NotificationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {
	@Query("SELECT * FROM notifications WHERE userOwnerId = :userId ORDER BY time")
	fun getForUser(userId: String): Flow<List<NotificationEntity>>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insert(notification: NotificationEntity)

	@Query("DELETE FROM notifications WHERE id = :notificationId")
	suspend fun deleteById(notificationId: String)

	@Query("SELECT * FROM notifications WHERE userOwnerId = :userId")
	suspend fun getForUserOnce(userId: String): List<NotificationEntity>
}