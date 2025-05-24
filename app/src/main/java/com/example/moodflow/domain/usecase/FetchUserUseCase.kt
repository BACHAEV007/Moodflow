package com.example.moodflow.domain.usecase

import com.example.moodflow.domain.model.SettingsUserModel
import com.example.moodflow.domain.repository.AuthRepository

class FetchUserUseCase(
	private val authRepository: AuthRepository
) {
	operator fun invoke(): SettingsUserModel {
		val avatar = authRepository.getCurrentUserAvatar()
		val name = authRepository.getCurrentUserName()
		val id = authRepository.getCurrentUserId()
		return SettingsUserModel(avatar, name.toString(), id.toString())
	}
}