package com.example.moodflow.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.moodflow.data.entity.Emotion
import kotlinx.coroutines.flow.Flow

@Dao
interface EmotionDao {
	@Query("SELECT * FROM emotion WHERE userId = :uid ORDER BY timestamp DESC")
	fun getEmotionsForUser(uid: String): Flow<List<Emotion>>

	@Query("SELECT * FROM emotion WHERE entryId = :entryId LIMIT 1")
	suspend fun getEmotionById(entryId: Long): Emotion?

	@Upsert
	suspend fun insertEmotion(entry: Emotion): Long

	@Query("""
        SELECT * FROM emotion 
        WHERE userId = :uid 
          AND timestamp BETWEEN :startDate AND :endDate 
        ORDER BY timestamp DESC
    """)
	suspend fun getEmotionsForDateRange(uid: String, startDate: Long, endDate: Long): List<Emotion>
}