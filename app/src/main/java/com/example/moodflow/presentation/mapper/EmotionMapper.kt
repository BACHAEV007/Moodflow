package com.example.moodflow.presentation.mapper

import android.content.Context
import androidx.core.content.ContextCompat
import com.example.moodflow.R
import com.example.moodflow.domain.model.EmotionModel
import com.example.moodflow.domain.model.EmotionTypeModel
import com.example.moodflow.presentation.uicontent.AddEmotionContent
import com.example.moodflow.presentation.uicontent.CardColor
import com.example.moodflow.presentation.uicontent.CardContent
import com.example.moodflow.presentation.uicontent.CardStyle
import com.example.moodflow.presentation.uicontent.GradientColor
import com.example.moodflow.presentation.view.CircleDiagramView
import com.example.moodflow.utils.DateTimeFormatterUtil

fun AddEmotionContent.toEmotionModel(): EmotionModel = EmotionModel(
	entryId = this.id ?: 0L,
	userId = "",
	emotion = emotion,
	timestamp = DateTimeFormatterUtil.parseDateTime(this.timestamp)!!,
	emotionType = this.emotionType,
	iconResName = this.iconResName,
	description = this.description,
	activities = this.activities,
	companions = this.companions,
	locations = this.locations
)

fun EmotionTypeModel.toCardStyle(
	context: Context,
	iconResName: String?
): CardStyle {
	val defaultIcon = when(this) {
		EmotionTypeModel.RED    -> R.drawable.red_image_card
		EmotionTypeModel.BLUE   -> R.drawable.blue_image_card
		EmotionTypeModel.YELLOW -> R.drawable.yellow_image_card
		EmotionTypeModel.GREEN  -> R.drawable.green_image_card
	}

	val iconResolved = iconResName
		?.let { name ->
			context.resources.getIdentifier(name, "drawable", context.packageName)
		}
		?.takeIf { it != 0 }
		?: defaultIcon

	return when (this) {
		EmotionTypeModel.RED    -> CardStyle(
			backgroundDrawable = R.drawable.red_card_shape,
			textColor  = ContextCompat.getColor(context, R.color.red_card_text),
			iconRes    = iconResolved
		)
		EmotionTypeModel.BLUE   -> CardStyle(
			backgroundDrawable = R.drawable.blue_card_shape,
			textColor  = ContextCompat.getColor(context, R.color.blue_card_text),
			iconRes    = iconResolved
		)
		EmotionTypeModel.YELLOW -> CardStyle(
			backgroundDrawable = R.drawable.yellow_card_shape,
			textColor  = ContextCompat.getColor(context, R.color.yellow_card_text),
			iconRes    = iconResolved
		)
		EmotionTypeModel.GREEN  -> CardStyle(
			backgroundDrawable = R.drawable.green_card_shape,
			textColor  = ContextCompat.getColor(context, R.color.green_card_text),
			iconRes    = iconResolved
		)
	}
}

fun EmotionModel.toCardContent(): CardContent {
	val formatted = DateTimeFormatterUtil.formatTimestamp(this.timestamp)

	val cardColor = when (this.emotionType) {
		EmotionTypeModel.RED    -> CardColor.RED
		EmotionTypeModel.BLUE   -> CardColor.BLUE
		EmotionTypeModel.YELLOW -> CardColor.YELLOW
		EmotionTypeModel.GREEN  -> CardColor.GREEN
	}

	return CardContent(
		id      = this.entryId,
		data    = formatted,
		color   = cardColor,
		feeling = this.emotion
	)
}

fun CardColor.toGradientColor(): GradientColor = when (this) {
	CardColor.RED    -> GradientColor.RED
	CardColor.BLUE   -> GradientColor.BLUE
	CardColor.GREEN  -> GradientColor.GREEN
	CardColor.YELLOW -> GradientColor.YELLOW
}

fun List<EmotionModel>.toCircleDataList(context: Context): ArrayList<CircleDiagramView.CircleData> {
	if (isEmpty()) return arrayListOf()

	val grouped = this.groupBy { it.emotionType }
	val total = this.size.toFloat()

	val result = arrayListOf<CircleDiagramView.CircleData>()
	EmotionTypeModel.entries.forEach { type ->
		val count = grouped[type]?.size ?: 0
		if (count > 0) {
			val percent = (count / total) * 100f
			val (startColorRes, endColorRes) = when (type) {
				EmotionTypeModel.GREEN   -> R.color.green_start_gradient   to R.color.green_end_gradient
				EmotionTypeModel.BLUE   -> R.color.blue_start_gradient    to R.color.blue_end_gradient
				EmotionTypeModel.RED   -> R.color.red_start_gradient     to R.color.red_end_gradient
				EmotionTypeModel.YELLOW -> R.color.yellow_start_gradient  to R.color.yellow_end_gradient
			}
			result += CircleDiagramView.CircleData(
				percent,
				ContextCompat.getColor(context, startColorRes),
				ContextCompat.getColor(context, endColorRes)
			)
		}
	}
	return result
}