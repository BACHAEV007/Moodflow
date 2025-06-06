package com.example.moodflow.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.moodflow.data.dao.EmotionDao
import com.example.moodflow.data.dao.NotificationDao
import com.example.moodflow.data.dao.UserDao
import com.example.moodflow.data.dbconverter.Converters
import com.example.moodflow.data.entity.Emotion
import com.example.moodflow.data.entity.NotificationEntity
import com.example.moodflow.data.entity.User
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
	single {
		Room.databaseBuilder(
			androidContext(),
			AppDatabase::class.java, "database-name"
		).build()
	}

	single { get<AppDatabase>().emotionDao() }
	single { get<AppDatabase>().userDao() }
	single { get<AppDatabase>().notificationDao() }
}

@Database(entities = [Emotion::class, User::class, NotificationEntity::class], version = 3)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
	abstract fun emotionDao(): EmotionDao
	abstract fun userDao(): UserDao
	abstract fun notificationDao(): NotificationDao
}