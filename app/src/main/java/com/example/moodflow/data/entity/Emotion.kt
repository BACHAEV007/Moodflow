package com.example.moodflow.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
	tableName = "emotion",
	foreignKeys = [
		ForeignKey(
			entity = User::class,
			parentColumns = ["userId"],
			childColumns = ["userId"],
			onDelete = ForeignKey.CASCADE
		)
	],
	indices = [Index("userId"), Index("emotionType")]
)
data class Emotion(
	@PrimaryKey(autoGenerate = true) val entryId: Long = 0,
	val userId: String,
	val timestamp: Long,
	val emotion: String,
	val emotionType: EmotionType,
	val iconResName: String? = null,
	val description: String? = null,

	val activities: List<String>? = null,
	val companions: List<String>? = null,
	val locations: List<String>? = null,
)

enum class EmotionType {
	RED,
	BLUE,
	GREEN,
	YELLOW
}