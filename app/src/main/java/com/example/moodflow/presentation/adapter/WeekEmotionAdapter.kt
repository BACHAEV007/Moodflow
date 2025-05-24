package com.example.moodflow.presentation.adapter

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.moodflow.R
import com.example.moodflow.databinding.WeekEmotionItemBinding
import com.example.moodflow.presentation.uicontent.WeekEmotionContent
import com.example.moodflow.utils.Constants.DAY_IN_WEEK
import com.google.android.flexbox.FlexboxLayout

class WeekEmotionAdapter(private val context: Context) :
    RecyclerView.Adapter<WeekEmotionAdapter.EmotionViewHolder>() {

    private val emotionList = MutableList(DAY_IN_WEEK) { WeekEmotionContent(emotions = emptyList(), icons = emptyList(), date = "") }

    fun updateData(newData: List<WeekEmotionContent>) {
        newData.forEachIndexed { index, content ->
            if (index in emotionList.indices) {
                emotionList[index] = content
            }
        }
        notifyDataSetChanged()
    }

    inner class EmotionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = WeekEmotionItemBinding.bind(view)
        fun bind(content: WeekEmotionContent, weekDay: String) = with(binding) {
            dayWeek.text = weekDay
            date.text = content.date
            if (content.icons.isEmpty()) {
                val imageView = ImageView(itemView.context).apply {
                    setImageResource(R.drawable.mock_icon)
                }
                iconsLayout.addView(imageView)
            } else {
                content.icons.forEach { iconRes ->
                    val iconResId = context.resources.getIdentifier(iconRes, "drawable", context.packageName)
                    val imageView = ImageView(itemView.context).apply {
                        setImageResource(iconResId)
                        val params = FlexboxLayout.LayoutParams(dpToPx(40), dpToPx(40))
                        layoutParams = params

                        if (iconsLayout.childCount > 0) {
                            params.setMargins(dpToPx(4), 0, 0, 0)
                        }
                        setImageResource(iconResId)
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
                    ellipsize = TextUtils.TruncateAt.END
                    maxLines = 1
                    text = emotion
                    textSize = 12f
                    setTextColor(ContextCompat.getColor(context, R.color.light_gray))
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

    private fun getDayOfWeek(position: Int): String =
        when (position) {
            0 -> context.getString(R.string.day_monday)
            1 -> context.getString(R.string.day_tuesday)
            2 -> context.getString(R.string.day_wednesday)
            3 -> context.getString(R.string.day_thursday)
            4 -> context.getString(R.string.day_friday)
            5 -> context.getString(R.string.day_saturday)
            6 -> context.getString(R.string.day_sunday)
            else -> ""
        }

    override fun getItemCount(): Int {
        return emotionList.size
    }
}