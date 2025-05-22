package com.example.moodflow.data.repository

import com.example.moodflow.data.dao.UserDao
import com.example.moodflow.data.mapper.toDomain
import com.example.moodflow.data.mapper.toEntity
import com.example.moodflow.domain.model.UserModel
import com.example.moodflow.domain.repository.UserRepository

class UserRepositoryImpl(private val userDao: UserDao) : UserRepository {

	override suspend fun insertUser(user: UserModel): Long {
		return userDao.insertUser(user.toEntity())
	}

	override suspend fun getUserById(userId: String): UserModel? {
		return userDao.getUserById(userId)?.toDomain()
	}
}