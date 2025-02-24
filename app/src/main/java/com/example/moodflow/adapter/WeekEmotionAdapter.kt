package com.example.moodflow.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.fonts.Font
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.moodflow.R
import com.example.moodflow.databinding.WeekEmotionItemBinding
import com.example.moodflow.uicontent.WeekEmotionContent
import com.google.android.flexbox.FlexboxLayout

class WeekEmotionAdapter(private val context: Context) : RecyclerView.Adapter<WeekEmotionAdapter.EmotionViewHolder>() {
    private val emotionList = mutableListOf(
        WeekEmotionContent(
            emotions = listOf("Спокойствие", "Продуктивность", "Счастье"),
            date = "17 фев",
            icons = listOf(R.drawable.green_image_card, R.drawable.red_image_card, R.drawable.blue_image_card),

            ), WeekEmotionContent(
            emotions = listOf("Выгорание", "Усталость"),
            date = "18 фев",
            icons = listOf(R.drawable.yellow_image_card, R.drawable.blue_image_card)
        ), WeekEmotionContent(
            emotions = listOf(),
            date = "19 фев",
            icons = listOf()
        ), WeekEmotionContent(
            emotions = listOf(),
            date = "20 фев",
            icons = listOf()
        ), WeekEmotionContent(
            emotions = listOf(),
            date = "21 фев",
            icons = listOf()
        ), WeekEmotionContent(
            emotions = listOf(),
            date = "22 фев",
            icons = listOf()
        ), WeekEmotionContent(
            emotions = listOf(),
            date = "23 фев",
            icons = listOf()
        ),
    )
    inner class EmotionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = WeekEmotionItemBinding.bind(view)
        fun bind(content: WeekEmotionContent, weekDay: String) = with(binding){
            dayWeek.text = weekDay
            date.text = content.date
            if(content.icons.isEmpty()){
                val imageView = ImageView(itemView.context).apply {
                    setImageResource(R.drawable.mock_icon)
                }
                iconsLayout.addView(imageView)
            } else {
                content.icons.forEach { iconRes ->
                    val imageView = ImageView(itemView.context).apply {
                        setImageResource(iconRes)
                        val params = FlexboxLayout.LayoutParams(dpToPx(40), dpToPx(40))
                        layoutParams = params

                        if (iconsLayout.childCount > 0) {
                            params.setMargins(dpToPx(4), 0, 0, 0)
                        }
                        setImageResource(iconRes)
                        scaleType = ImageView.ScaleType.FIT_CENTER
                        adjustViewBounds = true
                    }
                    iconsLayout.addView(imageView)
                }
            }


            emotionsLayout.removeAllViews()
            val typeface = ResourcesCompat.getFont(itemView.context, R.font.velasans_regular)
            content.emotions.forEach { emotion ->
                val textView = TextView(itemView.context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    text = emotion
                    textSize = 12f
                    setTextColor(Color.parseColor("#808080"))
                    setTypeface(typeface)
                }
                emotionsLayout.addView(textView)
            }
        }
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * context.resources.displayMetrics.density).toInt()
    }

    override fun onBindViewHolder(holder: EmotionViewHolder, position: Int) {
        holder.bind(emotionList[position], getDayOfWeek(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmotionViewHolder {
        return EmotionViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.week_emotion_item, parent, false)
        )
    }

    private fun getDayOfWeek(position: Int) : String =
        when(position){
            0 -> "Понедельник"
            1 -> "Вторник"
            2 -> "Среда"
            3 -> "Четверг"
            4 -> "Пятница"
            5 -> "Суббота"
            6 -> "Воскресенье"
            else -> ""
        }


    override fun getItemCount(): Int {
        return emotionList.size
    }
}