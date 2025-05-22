package com.example.moodflow.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moodflow.data.entity.User

@Dao
interface UserDao {
	@Insert(onConflict = OnConflictStrategy.IGNORE)
	suspend fun insertUser(user: User): Long

	@Query("SELECT * FROM users WHERE userId = :id")
	suspend fun getUserById(id: String): User?
}