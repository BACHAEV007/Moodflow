package com.example.moodflow.data.firebase

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import kotlin.coroutines.cancellation.CancellationException

val firebaseModule = module {
	single { GoogleAuthClient(androidContext()) }

}

class GoogleAuthClient(
	private val context: Context,
) {
	private val credentialManager = CredentialManager.create(context)
	private val firebaseAuth = FirebaseAuth.getInstance()

	fun isSingedIn(): Boolean {
		if (firebaseAuth.currentUser != null) {
			return true
		}

		return false
	}

	suspend fun signIn(): FirebaseUser? {
		if (isSingedIn()) {
			Log.d("DEBUG", "Result = $firebaseAuth.currentUser")
			return firebaseAuth.currentUser
		}

		return try {
			val result = buildCredentialRequest()
			Log.d("DEBUG", "Result = $result")
			handleSignIn(result)
		} catch (e: Exception) {
			e.printStackTrace()
			if (e is CancellationException) throw e
			null
		}
	}

	private suspend fun handleSignIn(result: GetCredentialResponse): FirebaseUser? {
		val credential = result.credential

		if (
			credential is CustomCredential &&
			credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
		) {
			return try {
				val tokenCredential = GoogleIdTokenCredential.createFrom(credential.data)

				val authCredential = GoogleAuthProvider.getCredential(
					tokenCredential.idToken, null
				)
				val authResult = firebaseAuth.signInWithCredential(authCredential).await()
				authResult.user
			} catch (e: GoogleIdTokenParsingException) {
				null
			}
		} else {
			null
		}
		return null
	}

	private suspend fun buildCredentialRequest(): GetCredentialResponse {
		val request = GetCredentialRequest.Builder()
			.addCredentialOption(
				GetGoogleIdOption.Builder()
					.setFilterByAuthorizedAccounts(false)
					.setServerClientId(
						"XXXXX"
					)
					.setAutoSelectEnabled(false)
					.build()
			)

			.build()

		return credentialManager.getCredential(
			request = request, context = context
		)
	}

	suspend fun signOut() {
		credentialManager.clearCredentialState(
			ClearCredentialStateRequest()
		)
		firebaseAuth.signOut()
	}

	fun getCurrentUserId(): String? {
		return firebaseAuth.currentUser?.uid
	}

	fun getCurrentUserAvatar(): Uri? {
		return firebaseAuth.currentUser?.photoUrl
	}

	fun getCurrentUserName(): String? {
		return firebaseAuth.currentUser?.displayName
	}

	fun getCurrentUserEmail(): String? {
		return firebaseAuth.currentUser?.email
	}

}
