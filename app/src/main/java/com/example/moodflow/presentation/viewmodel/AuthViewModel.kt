package com.example.moodflow.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodflow.domain.usecase.GetFingerprintEnabledUseCase
import com.example.moodflow.domain.usecase.SignInUseCase
import com.example.moodflow.domain.usecase.SyncUserUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class AuthViewModel(
	private val signInUseCase: SignInUseCase,
	private val syncUserUseCase: SyncUserUseCase,
	private val getFingerprintEnabledUseCase: GetFingerprintEnabledUseCase
) : ViewModel() {

	val fingerprintEnabled: Flow<Boolean> = getFingerprintEnabledUseCase()

	fun signIn() {
		viewModelScope.launch {
			val success = signInUseCase()
			if (success) {
				syncUserUseCase()

			}
		}
	}

	fun syncUser(){
		viewModelScope.launch {
			syncUserUseCase()
		}
	}
}