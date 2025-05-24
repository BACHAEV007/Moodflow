package com.example.moodflow.data.mapper

import com.example.moodflow.data.entity.User
import com.example.moodflow.domain.model.UserModel

fun User.toDomain(): UserModel {
	return UserModel(
		userId = this.userId,
		email = this.email
	)
}

fun UserModel.toEntity(): User {
	return User(
		userId = this.userId,
		email = this.email
	)
}
