package com.example.moodflow.di

import com.example.moodflow.data.repository.AuthRepositoryImpl
import com.example.moodflow.data.repository.EmotionRepositoryImpl
import com.example.moodflow.data.repository.UserRepositoryImpl
import com.example.moodflow.domain.repository.AuthRepository
import com.example.moodflow.domain.repository.EmotionRepository
import com.example.moodflow.domain.repository.UserRepository
import com.example.moodflow.domain.usecase.AddEmotionUseCase
import com.example.moodflow.domain.usecase.FetchEmotionByIdUseCase
import com.example.moodflow.domain.usecase.FetchEmotionsUseCase
import com.example.moodflow.domain.usecase.SignInUseCase
import com.example.moodflow.domain.usecase.SyncUserUseCase
import com.example.moodflow.presentation.viewmodel.AddEmotionViewModel
import com.example.moodflow.presentation.viewmodel.AuthViewModel
import com.example.moodflow.presentation.viewmodel.JournalViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
	factoryOf(::SignInUseCase)
	factoryOf(::SyncUserUseCase)
	factoryOf(::FetchEmotionsUseCase)
	factoryOf(::FetchEmotionByIdUseCase)
	factoryOf(::AddEmotionUseCase)
	factoryOf(::UserRepositoryImpl) bind UserRepository::class
	viewModelOf(::AuthViewModel)
	viewModelOf(::AddEmotionViewModel)
	viewModelOf(::JournalViewModel)
	factoryOf(::AuthRepositoryImpl) bind AuthRepository::class
	factoryOf(::EmotionRepositoryImpl) bind EmotionRepository::class
}
