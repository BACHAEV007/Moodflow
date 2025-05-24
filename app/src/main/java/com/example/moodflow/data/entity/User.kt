package com.example.moodflow.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
	@PrimaryKey val userId: String,
	val email: String

)