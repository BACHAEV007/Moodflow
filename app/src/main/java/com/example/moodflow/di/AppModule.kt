package com.example.moodflow.di

import android.content.Context
import com.example.moodflow.data.datastore.DataStoreManager
import com.example.moodflow.data.repository.AuthRepositoryImpl
import com.example.moodflow.data.repository.EmotionRepositoryImpl
import com.example.moodflow.data.repository.NotificationHelper
import com.example.moodflow.data.repository.SettingsRepositoryImpl
import com.example.moodflow.data.repository.UserRepositoryImpl
import com.example.moodflow.domain.repository.AuthRepository
import com.example.moodflow.domain.repository.EmotionRepository
import com.example.moodflow.domain.repository.SettingsPreferences
import com.example.moodflow.domain.repository.SettingsRepository
import com.example.moodflow.domain.repository.UserRepository
import com.example.moodflow.domain.usecase.AddEmotionUseCase
import com.example.moodflow.domain.usecase.AddNotificationUseCase
import com.example.moodflow.domain.usecase.CancelAllNotificationUseCase
import com.example.moodflow.domain.usecase.FetchEmotionByIdUseCase
import com.example.moodflow.domain.usecase.FetchEmotionsUseCase
import com.example.moodflow.domain.usecase.FetchNotificationsUseCase
import com.example.moodflow.domain.usecase.FetchUserUseCase
import com.example.moodflow.domain.usecase.GetEmotionsForDateUseCase
import com.example.moodflow.domain.usecase.GetFingerprintEnabledUseCase
import com.example.moodflow.domain.usecase.GetNotificationsEnabledUseCase
import com.example.moodflow.domain.usecase.RemoveNotificationUseCase
import com.example.moodflow.domain.usecase.RescheduleNotificationsUseCase
import com.example.moodflow.domain.usecase.SetFingerprintEnabledUseCase
import com.example.moodflow.domain.usecase.SetNotificationsEnabledUseCase
import com.example.moodflow.domain.usecase.SignInUseCase
import com.example.moodflow.domain.usecase.SyncUserUseCase
import com.example.moodflow.presentation.viewmodel.AddEmotionViewModel
import com.example.moodflow.presentation.viewmodel.AuthViewModel
import com.example.moodflow.presentation.viewmodel.JournalViewModel
import com.example.moodflow.presentation.viewmodel.SettingsViewModel
import com.example.moodflow.presentation.viewmodel.StatisticViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
	factoryOf(::SignInUseCase)
	factoryOf(::SyncUserUseCase)
	factoryOf(::FetchEmotionsUseCase)
	factoryOf(::FetchEmotionByIdUseCase)
	factoryOf(::AddEmotionUseCase)
	factoryOf(::FetchUserUseCase)
	factoryOf(::GetEmotionsForDateUseCase)
	factoryOf(::FetchNotificationsUseCase)
	factoryOf(::AddNotificationUseCase)
	factoryOf(::RemoveNotificationUseCase)

	factoryOf(::GetNotificationsEnabledUseCase)
	factoryOf(::SetNotificationsEnabledUseCase)
	factoryOf(::GetFingerprintEnabledUseCase)
	factoryOf(::SetFingerprintEnabledUseCase)
	factoryOf(::CancelAllNotificationUseCase)
	factoryOf(::RescheduleNotificationsUseCase)

	factoryOf(::UserRepositoryImpl) bind UserRepository::class
	viewModelOf(::AuthViewModel)
	viewModelOf(::AddEmotionViewModel)
	viewModelOf(::JournalViewModel)
	viewModelOf(::StatisticViewModel)
	viewModelOf(::SettingsViewModel)
	factoryOf(::AuthRepositoryImpl) bind AuthRepository::class
	factoryOf(::EmotionRepositoryImpl) bind EmotionRepository::class
	factoryOf(::SettingsRepositoryImpl) {
		bind<SettingsRepository>()
		bind<SettingsPreferences>()
	}
	factory { { get<Context>() } }
	single { NotificationHelper(androidContext()) }
	singleOf(::DataStoreManager)
}
