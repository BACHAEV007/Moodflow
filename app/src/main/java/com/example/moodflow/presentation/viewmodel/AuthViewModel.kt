package com.example.moodflow.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodflow.domain.repository.AuthRepository
import com.example.moodflow.domain.usecase.SignInUseCase
import com.example.moodflow.domain.usecase.SyncUserUseCase
import kotlinx.coroutines.launch

class AuthViewModel(
	private val authRepository: AuthRepository,
	private val signInUseCase: SignInUseCase,
	private val syncUserUseCase: SyncUserUseCase
) : ViewModel() {

	fun signIn() {
		viewModelScope.launch {
			val success = signInUseCase()
			if (success) {
				syncUserUseCase()

			} else {

			}
		}
	}

	fun syncUser(){
		viewModelScope.launch {
			syncUserUseCase()
		}
	}

	fun signOut() {
		viewModelScope.launch {
			authRepository.signOut()
		}
	}

	fun isUserSignedIn(): Boolean {
		return authRepository.isSignedIn()
	}
}