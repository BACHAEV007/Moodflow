package com.example.moodflow.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodflow.domain.usecase.FetchEmotionsUseCase
import com.example.moodflow.presentation.mapper.toCardContent
import com.example.moodflow.presentation.mapper.toGradientColor
import com.example.moodflow.presentation.state.JournalState
import com.example.moodflow.presentation.uicontent.CardContent
import com.example.moodflow.utils.DateTimeFormatterUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

class JournalViewModel(
	private val fetchEmotionsUseCase: FetchEmotionsUseCase
) : ViewModel() {

	private val _state = MutableStateFlow(JournalState())
	val state: StateFlow<JournalState> = _state.asStateFlow()

	init {
		loadEmotions()
	}

	private fun loadEmotions() {
		viewModelScope.launch {
			fetchEmotionsUseCase()
				.map { emotionModels ->
					val cardContents = emotionModels.map { it.toCardContent() }

					val groupedByDate: Map<LocalDate, List<CardContent>> = cardContents
						.mapNotNull {
							DateTimeFormatterUtil.parseDateTime(it.data)?.let { epoch ->
								Instant.ofEpochMilli(epoch)
									.atZone(ZoneId.systemDefault())
									.toLocalDate() to it
							}
						}
						.groupBy({ it.first }, { it.second })

					val today = LocalDate.now()
					val lastDayEmotions = groupedByDate[today].orEmpty().take(2)

					val progressMap = lastDayEmotions.associate {
						val gc = it.color.toGradientColor()
						val value = 0.5f
						gc to value
					}

					val total = cardContents.size
					val streak = calculateStreak(groupedByDate.keys)

					Triple(cardContents, progressMap, streak to total)
				}
				.collect { (cards, progressMap, streakAndTotal) ->
					_state.update {
						it.copy(
							emotions = cards,
							progressMap = progressMap,
							cardStreak = streakAndTotal.first,
							cardsCount = streakAndTotal.second,
							isLoading = false,
							error = null
						)
					}
				}
		}
	}

	private fun calculateStreak(days: Set<LocalDate?>): Int {
		var streak = 0
		var currentDay = LocalDate.now()

		while (days.contains(currentDay)) {
			streak++
			currentDay = currentDay.minusDays(1)
		}

		return streak
	}
}