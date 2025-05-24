package com.example.moodflow.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodflow.R
import com.example.moodflow.domain.model.EmotionTypeModel
import com.example.moodflow.domain.usecase.AddEmotionUseCase
import com.example.moodflow.domain.usecase.FetchEmotionByIdUseCase
import com.example.moodflow.presentation.adapter.Emotion
import com.example.moodflow.presentation.mapper.toEmotionModel
import com.example.moodflow.presentation.uicontent.AddEmotionContent
import com.example.moodflow.utils.DateTimeFormatterUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddEmotionViewModel(
	private val addEmotionUseCase: AddEmotionUseCase,
	private val fetchEmotionByIdUseCase: FetchEmotionByIdUseCase
) : ViewModel() {

	private val _state = MutableStateFlow(
		AddEmotionContent()
	)
	val state: StateFlow<AddEmotionContent> = _state.asStateFlow()

	fun addEmotion() {
		viewModelScope.launch {
			try {
				addEmotionUseCase(
					_state.value.toEmotionModel()
				)
			} catch (e: Exception) {
				throw e
			}
		}
	}

	fun syncTime() {
		val currentTimestamp = System.currentTimeMillis()
		_state.update { current ->
			current.copy(
				timestamp = DateTimeFormatterUtil.formatTimestamp(currentTimestamp)
			)
		}
	}

	fun getEmotionById(id: Long) {
		viewModelScope.launch {
			val emotion = fetchEmotionByIdUseCase(id)
			if (emotion != null) {
				_state.value = AddEmotionContent(
					id          = emotion.entryId,
					timestamp   = DateTimeFormatterUtil.formatTimestamp(emotion.timestamp),
					emotion     = emotion.emotion,
					emotionType = emotion.emotionType,
					iconResName = emotion.iconResName,
					description = emotion.description,
					activities  = emotion.activities ?: emptyList(),
					companions  = emotion.companions ?: emptyList(),
					locations   = emotion.locations ?: emptyList()
				)
			} else {
				_state.value = AddEmotionContent()
			}
		}
	}

	fun setEmotion(emotion: Emotion) {
		val newState = AddEmotionContent(
			emotion = emotion.emotion,
			iconResName = emotion.iconResName,
			description = emotion.description,
			emotionType = when (emotion.color) {
				R.color.red_card_text    -> EmotionTypeModel.RED
				R.color.yellow_card_text -> EmotionTypeModel.YELLOW
				R.color.blue_card_text   -> EmotionTypeModel.BLUE
				R.color.green_card_text  -> EmotionTypeModel.GREEN
				else                     -> EmotionTypeModel.RED
			}
		)
		_state.update { _ -> newState }
	}

	fun setActivities(list: List<String>) {
		_state.update { it.copy(activities = list) }
	}

	fun setCompanions(list: List<String>) {
		_state.update { it.copy(companions = list) }
	}

	fun setLocations(list: List<String>) {
		_state.update { it.copy(locations = list) }
	}
}