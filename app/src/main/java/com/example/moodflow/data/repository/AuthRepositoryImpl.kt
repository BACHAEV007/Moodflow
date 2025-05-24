package com.example.moodflow.data.repository

import com.example.moodflow.data.firebase.GoogleAuthClient
import com.example.moodflow.domain.repository.AuthRepository

class AuthRepositoryImpl(
	private val googleAuthClient: GoogleAuthClient
) : AuthRepository {

	override suspend fun signIn(): Boolean {
		return if (googleAuthClient.signIn() != null) true else false
	}

	override suspend fun signOut() {
		googleAuthClient.signOut()
	}

	override fun isSignedIn(): Boolean {
		return googleAuthClient.isSingedIn()
	}

	override fun getCurrentUserId(): String? {
		return googleAuthClient.getCurrentUserId()
	}

	override fun getCurrentUserEmail(): String? {
		return googleAuthClient.getCurrentUserEmail()
	}

	override fun getCurrentUserAvatar(): String {
		return googleAuthClient.getCurrentUserAvatar().toString()
	}

	override fun getCurrentUserName(): String? {
		return googleAuthClient.getCurrentUserName()
	}
}