package com.example.moodflow.presentation

import android.app.Application
import com.example.moodflow.data.database.databaseModule
import com.example.moodflow.data.firebase.firebaseModule
import com.example.moodflow.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class  MoodflowApp : Application() {

	override fun onCreate() {
		super.onCreate()

		startKoin {
			androidLogger()

			androidContext(this@MoodflowApp)

			modules(
				databaseModule,
				firebaseModule,
				appModule
			)
		}
	}
}