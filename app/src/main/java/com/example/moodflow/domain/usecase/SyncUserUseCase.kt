package com.example.moodflow.domain.usecase

import com.example.moodflow.domain.model.UserModel
import com.example.moodflow.domain.repository.AuthRepository
import com.example.moodflow.domain.repository.UserRepository

class SyncUserUseCase(
	private val authRepository: AuthRepository,
	private val userRepository: UserRepository
) {
	suspend operator fun invoke() {
		val uid = authRepository.getCurrentUserId()
		val email = authRepository.getCurrentUserEmail()

		if (uid != null && email != null) {
			val existingUser = userRepository.getUserById(uid)
			if (existingUser == null) {
				val newUser = UserModel(userId = uid, email = email)
				userRepository.insertUser(newUser)
			}
		}
	}
}