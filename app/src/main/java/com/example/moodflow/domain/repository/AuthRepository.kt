package com.example.moodflow.domain.repository

interface AuthRepository {
	suspend fun signIn(): Boolean
	suspend fun signOut()
	fun isSignedIn(): Boolean
	fun getCurrentUserId(): String?
	fun getCurrentUserEmail(): String?
	fun getCurrentUserAvatar(): String
	fun getCurrentUserName(): String?
}