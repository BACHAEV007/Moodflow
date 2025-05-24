package com.example.moodflow.presentation.viewmodel

import android.content.Context
import android.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodflow.R
import com.example.moodflow.domain.model.EmotionModel
import com.example.moodflow.domain.model.EmotionTypeModel
import com.example.moodflow.domain.usecase.GetEmotionsForDateUseCase
import com.example.moodflow.presentation.mapper.toCircleDataList
import com.example.moodflow.presentation.uicontent.FrequentContent
import com.example.moodflow.presentation.uicontent.TimeOfDay
import com.example.moodflow.presentation.uicontent.WeekEmotionContent
import com.example.moodflow.presentation.view.CircleDiagramView
import com.example.moodflow.presentation.view.ColorBlocksView
import com.example.moodflow.utils.Constants.WEEK_COUNT
import com.example.moodflow.utils.DateTimeFormatterUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters
import java.util.Locale

class StatisticViewModel(
	private val getEmotionsForDateUseCase: GetEmotionsForDateUseCase,
	private val contextProvider: () -> Context
) : ViewModel() {
	private val maxOffset = WEEK_COUNT
	val weekRanges: List<Pair<LocalDate, LocalDate>> = generateWeeklyRangesAroundCurrent()
	val weekLabels: List<String> = weekRanges.map { (start, end) ->
		val fmt = DateTimeFormatter.ofPattern("d MMM", Locale.getDefault())
		"${start.format(fmt)} – ${end.format(fmt)}"
	}

	private val _currentWeek = MutableStateFlow(WEEK_COUNT)
	val currentWeek: StateFlow<Int> = _currentWeek.asStateFlow()

	private val weekEmotionsFlows =
		mutableMapOf<Int, MutableStateFlow<List<EmotionModel>>>()

	fun getCircleData(index: Int): Pair<List<CircleDiagramView.CircleData>, Int> {
		val raw = weekEmotionsFlows[index]?.value.orEmpty()
		val circleDataList = raw.toCircleDataList(contextProvider())
		return circleDataList to raw.size
	}

	fun getWeekEmotions(index: Int): List<WeekEmotionContent> {
		val raw = weekEmotionsFlows[index]?.value.orEmpty()
		val startDate = weekRanges.getOrNull(index)?.first ?: return emptyList()

		fun timestampToLocalDate(timestamp: Long): LocalDate =
			Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDate()

		return (0 until 7).map { dayOffset ->
			val currentDate = startDate.plusDays(dayOffset.toLong())
			val formattedDate = DateTimeFormatterUtil.formatDateRussianNoDot(currentDate)
			val emotionsForDay = raw.filter {
				timestampToLocalDate(it.timestamp) == currentDate
			}

			WeekEmotionContent(
				emotions = emotionsForDay.map { it.emotion },
				date = formattedDate,
				icons = emotionsForDay.mapNotNull { it.iconResName }
			)
		}
	}

	fun getFrequentEmotionData(index: Int): List<FrequentContent> {
		val raw = weekEmotionsFlows[index]?.value.orEmpty()

		val emotionCounts = raw.groupBy { it.emotion }
			.mapValues { it.value.size }

		return emotionCounts.map { (emotion, count) ->
			val iconResId = raw.firstOrNull { it.emotion == emotion }?.iconResName.toString()
			FrequentContent(
				icon = iconResId,
				emotion = emotion,
				count = count
			)
		}.sortedByDescending { it.count }
	}

	private fun classifyTime(ts: Long): TimeOfDay {
		val hour = Instant.ofEpochMilli(ts)
			.atZone(ZoneId.systemDefault())
			.hour
		return when (hour) {
			in  4..7  -> TimeOfDay.EARLY_MORNING
			in  8..11 -> TimeOfDay.MORNING
			in 12..15 -> TimeOfDay.DAY
			in 16..19 -> TimeOfDay.EVENING
			else       -> TimeOfDay.LATE_EVENING
		}
	}

	fun getDayEmotionBlocks(index: Int): List<Pair<List<ColorBlocksView.Block>, Int>> {
		val raw = weekEmotionsFlows[index]?.value.orEmpty()

		val groupedBySlot: Map<TimeOfDay, List<EmotionModel>> = raw.groupBy {
			classifyTime(it.timestamp)
		}

		return TimeOfDay.values().map { slot ->
			val inSlot = groupedBySlot[slot].orEmpty()
			val total = inSlot.size.coerceAtLeast(1).toFloat()

			val counts: Map<EmotionTypeModel, Int> =
				inSlot.groupingBy { it.emotionType }.eachCount()

			val blocks = EmotionTypeModel.values().mapNotNull { type ->
				val cnt = counts[type] ?: return@mapNotNull null
				val (startRes, endRes) = when (type) {
					EmotionTypeModel.GREEN   -> R.drawable.green_image_card to R.drawable.green_image_card
					EmotionTypeModel.BLUE    -> R.drawable.blue_shell_icon to R.drawable.blue_shell_icon
					EmotionTypeModel.RED     -> R.drawable.red_image_card to R.drawable.red_image_card
					EmotionTypeModel.YELLOW  -> R.drawable.yellow_circle_icon to R.drawable.yellow_circle_icon
				}
				ColorBlocksView.Block(
					percentage = cnt / total,
					startColor = getGradientStartColor(startRes),
					endColor   = getGradientEndColor(endRes)
				)
			}

			blocks to inSlot.size
		}
	}

	private fun getGradientStartColor(iconRes: Int): Int = when (iconRes) {
		R.drawable.green_image_card                                 -> R.color.green_start_gradient
		R.drawable.yellow_image_card, R.drawable.yellow_circle_icon -> R.color.yellow_start_gradient
		R.drawable.red_image_card                                   -> R.color.red_start_gradient
		R.drawable.blue_shell_icon, R.drawable.blue_image_card      -> R.color.blue_start_gradient
		else                                                        -> Color.GRAY
	}

	private fun getGradientEndColor(iconRes: Int): Int = when (iconRes) {
		R.drawable.green_image_card                                 -> R.color.green_end_gradient
		R.drawable.yellow_image_card, R.drawable.yellow_circle_icon -> R.color.yellow_end_gradient
		R.drawable.red_image_card                                   -> R.color.red_end_gradient
		R.drawable.blue_shell_icon, R.drawable.blue_image_card      -> R.color.blue_end_gradient
		else                                                        -> Color.LTGRAY
	}

	fun getWeekEmotionsFlow(index: Int): StateFlow<List<EmotionModel>> {
		return weekEmotionsFlows.getOrPut(index) {
			MutableStateFlow(emptyList())
		}
	}

	private fun prefetchWeek(index: Int) {
		if (index !in weekRanges.indices) return
		viewModelScope.launch(Dispatchers.IO) {
			if (weekEmotionsFlows[index]?.value?.isNotEmpty() == true) return@launch

			val (start, end) = weekRanges[index]
			val startMs = start.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
			val endMs   = end  .atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
			val data    = getEmotionsForDateUseCase(startMs, endMs)

			weekEmotionsFlows[index]?.value = data
		}
	}

	fun selectWeek(index: Int) {
		if (index !in weekRanges.indices) return
		listOf(index - 1, index, index + 1)
			.filter { it in weekRanges.indices }
			.forEach { prefetchWeek(it) }
	}

	private fun generateWeeklyRangesAroundCurrent(): List<Pair<LocalDate, LocalDate>> {
		val today = LocalDate.now()
		val monday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))

		return (-maxOffset..maxOffset).map { offset ->
			val start = monday.plusWeeks(offset.toLong())
			start to start.plusDays(6)
		}
	}

	fun invalidateCache() {
		viewModelScope.launch {
			if (_currentWeek.value in weekRanges.indices) {
				weekEmotionsFlows[_currentWeek.value]?.value = emptyList()
			}
			selectWeek(_currentWeek.value)
		}
	}
}

