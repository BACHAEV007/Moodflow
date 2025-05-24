package com.example.moodflow.data.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.util.UUID

@Entity(
	tableName = "notifications",
	foreignKeys = [
		ForeignKey(
			entity = User::class,
			parentColumns = ["userId"],
			childColumns = ["userOwnerId"],
			onDelete = ForeignKey.CASCADE
		)
	],
	indices = [Index("userOwnerId")]
)
data class NotificationEntity(
	@PrimaryKey val id: String = UUID.randomUUID().toString(),
	val userOwnerId: String,
	val time: String
)


data class UserWithNotifications(
	@Embedded val user: User,
	@Relation(
		parentColumn  = "userId",
		entityColumn  = "userOwnerId",
		entity        = NotificationEntity::class
	)
	val notifications: List<NotificationEntity>
)