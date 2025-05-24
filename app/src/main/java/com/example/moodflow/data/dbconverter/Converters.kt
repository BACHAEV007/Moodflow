package com.example.moodflow.data.dbconverter

import androidx.room.TypeConverter
import com.example.moodflow.data.entity.EmotionType
import com.google.gson.Gson

class Converters {
	private val gson = Gson()

	@TypeConverter
	fun fromList(value: List<String>?): String? =
		value?.let { gson.toJson(it) }

	@TypeConverter
	fun toList(value: String?): List<String>? =
		value?.let {
			gson.fromJson(it, Array<String>::class.java).toList()
		}

	@TypeConverter
	fun fromEmotionType(value: EmotionType): String = value.name

	@TypeConverter
	fun toEmotionType(value: String): EmotionType = EmotionType.valueOf(value)
}