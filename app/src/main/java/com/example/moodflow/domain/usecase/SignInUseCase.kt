package com.example.moodflow.domain.usecase

import com.example.moodflow.domain.repository.AuthRepository

class SignInUseCase(private val authRepository: AuthRepository) {
	suspend operator fun invoke(): Boolean {
		return authRepository.signIn()
	}
}