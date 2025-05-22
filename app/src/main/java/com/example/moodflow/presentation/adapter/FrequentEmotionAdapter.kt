package com.example.moodflow.presentation.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moodflow.R
import com.example.moodflow.databinding.FrequentEmotionItemBinding
import com.example.moodflow.presentation.uicontent.FrequentContent

class FrequentEmotionAdapter : RecyclerView.Adapter<FrequentEmotionAdapter.FrequentViewHolder>() {
    var emotionList = mutableListOf<FrequentContent>(
        FrequentContent(icon = R.drawable.green_image_card, emotion = "Спокойствие", count = 6),
        FrequentContent(icon = R.drawable.yellow_image_card, emotion = "Продуктивность", count = 1),
        FrequentContent(icon = R.drawable.yellow_circle_icon, emotion = "Счастье", count = 2),
        FrequentContent(icon = R.drawable.blue_image_card, emotion = "Выгорание", count = 5),
        FrequentContent(icon = R.drawable.blue_shell_icon, emotion = "Усталость", count = 3)
    ).sortedByDescending { it.count }.toMutableList()

    inner class FrequentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = FrequentEmotionItemBinding.bind(view)

        @SuppressLint("SetTextI18n")
        fun bind(item: FrequentContent) = with(binding) {
            emotionIcon.setImageResource(item.icon)
            emotionText.text = item.emotion
            emotionCount.text = item.count.toString()
            val maxFrequency = emotionList.maxOfOrNull { it.count }?.toFloat() ?: 1f
            val barLength = if (maxFrequency > 0) item.count / maxFrequency else 0f
            gradientBar.setBarParams(
                barLength,
                getGradientStartColor(item.icon),
                getGradientEndColor(item.icon)
            )
        }
    }

    private fun getGradientStartColor(iconRes: Int): Int = when (iconRes) {
        R.drawable.green_image_card -> R.color.green_start_gradient
        R.drawable.yellow_image_card, R.drawable.yellow_circle_icon -> R.color.yellow_start_gradient
        R.drawable.red_image_card -> R.color.red_start_gradient
        R.drawable.blue_shell_icon, R.drawable.blue_image_card -> R.color.blue_start_gradient
        else -> Color.GRAY
    }

    private fun getGradientEndColor(iconRes: Int): Int = when (iconRes) {
        R.drawable.green_image_card -> R.color.green_end_gradient
        R.drawable.yellow_image_card, R.drawable.yellow_circle_icon -> R.color.yellow_end_gradient
        R.drawable.red_image_card -> R.color.red_end_gradient
        R.drawable.blue_shell_icon, R.drawable.blue_image_card -> R.color.blue_end_gradient
        else -> Color.LTGRAY
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FrequentViewHolder {
        return FrequentViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.frequent_emotion_item, parent, false)
        )
    }

    override fun getItemCount(): Int = emotionList.size

    override fun onBindViewHolder(holder: FrequentViewHolder, position: Int) {
        holder.bind(emotionList[position])
    }
}