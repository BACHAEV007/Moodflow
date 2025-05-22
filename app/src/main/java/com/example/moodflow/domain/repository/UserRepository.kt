package com.example.moodflow.domain.repository

import com.example.moodflow.domain.model.UserModel

interface UserRepository {
	suspend fun insertUser(user: UserModel): Long
	suspend fun getUserById(userId: String): UserModel?
}