package com.example.moodflow.data.entity

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithEmotions(
	@Embedded val user: User,

	@Relation(
		parentColumn = "userId",
		entityColumn = "userId",
		entity = Emotion::class
	)
	val emotions: List<Emotion>
)