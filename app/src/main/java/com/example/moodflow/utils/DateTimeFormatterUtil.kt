package com.example.moodflow.utils

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.format.TextStyle
import java.util.Locale

object DateTimeFormatterUtil {

	private val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault())
	private val dayOfWeekFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("EEEE", Locale.getDefault())
	private val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("d MMMM", Locale.getDefault())

	fun formatTimestamp(timestamp: Long): String {
		val zoneId = ZoneId.systemDefault()
		val dateTime = Instant.ofEpochMilli(timestamp).atZone(zoneId).toLocalDateTime()
		val today = LocalDate.now(zoneId)
		val date = dateTime.toLocalDate()
		val time = dateTime.toLocalTime().format(timeFormatter)

		return when {
			date.isEqual(today) -> "Сегодня, $time"
			date.isEqual(today.minusDays(1)) -> "Вчера, $time"
			date.isAfter(today.minusDays(7)) -> {
				val dayOfWeek = date.format(dayOfWeekFormatter).replaceFirstChar { it.uppercase() }
				"$dayOfWeek, $time"
			}
			else -> {
				val formattedDate = date.format(dateFormatter)
				"$formattedDate, $time"
			}
		}
	}

	fun parseDateTime(dateTimeString: String): Long? {
		val zoneId = ZoneId.systemDefault()
		val now = LocalDate.now(zoneId)

		val timeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault())
		val dayOfWeekFormatter = DateTimeFormatter.ofPattern("EEEE", Locale.getDefault())
		val dateFormatter = DateTimeFormatter.ofPattern("d MMMM", Locale.getDefault())

		return try {
			when {
				dateTimeString.startsWith("Сегодня, ") -> {
					val timePart = dateTimeString.removePrefix("Сегодня, ").trim()
					val time = LocalTime.parse(timePart, timeFormatter)
					LocalDateTime.of(now, time).atZone(zoneId).toInstant().toEpochMilli()
				}
				dateTimeString.startsWith("Вчера, ") -> {
					val timePart = dateTimeString.removePrefix("Вчера, ").trim()
					val time = LocalTime.parse(timePart, timeFormatter)
					LocalDateTime.of(now.minusDays(1), time).atZone(zoneId).toInstant().toEpochMilli()
				}
				dayOfWeekFormatter.format(now.minusDays(1)).let { day ->
					dateTimeString.startsWith("$day, ")
				} -> {
					val parts = dateTimeString.split(", ")
					if (parts.size == 2) {
						val dayOfWeek = parts[0]
						val time = LocalTime.parse(parts[1], timeFormatter)
						val daysAgo = (1..6).find { offset ->
							now.minusDays(offset.toLong()).dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault()) == dayOfWeek
						}
						daysAgo?.let {
							LocalDateTime.of(now.minusDays(it.toLong()), time).atZone(zoneId).toInstant().toEpochMilli()
						}
					} else null
				}
				else -> {
					val parts = dateTimeString.split(", ")
					if (parts.size == 2) {
						val date = LocalDate.parse(parts[0], dateFormatter)
						val time = LocalTime.parse(parts[1], timeFormatter)
						LocalDateTime.of(date, time).atZone(zoneId).toInstant().toEpochMilli()
					} else null
				}
			}
		} catch (e: DateTimeParseException) {
			null
		}
	}

	fun formatDateRussianNoDot(date: LocalDate): String {
		val formatter = DateTimeFormatter.ofPattern("d MMM", Locale("ru"))
		val formatted = date.format(formatter)
		return formatted.trimEnd('.')
	}
}