package com.example.moodflow.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moodflow.R
import com.example.moodflow.databinding.FrequentEmotionItemBinding
import com.example.moodflow.uicontent.FrequentContent

class FrequentEmotionAdapter : RecyclerView.Adapter<FrequentEmotionAdapter.FrequentViewHolder>() {
    var emotionList = mutableListOf<FrequentContent>(
        FrequentContent(icon = R.drawable.green_image_card, emotion = "Спокойствие", count = 2),
        FrequentContent(icon = R.drawable.yellow_image_card, emotion = "Продуктивность", count = 1),
        FrequentContent(icon = R.drawable.yellow_image_card, emotion = "Счастье", count = 1),
        FrequentContent(icon = R.drawable.blue_image_card, emotion = "Выгорание", count = 1),
        FrequentContent(icon = R.drawable.blue_image_card, emotion = "Усталость", count = 1)
    )
    inner class FrequentViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val binding = FrequentEmotionItemBinding.bind(view)
        fun bind(item: FrequentContent) = with(binding) {
            emotionIcon.setImageResource(item.icon)
            emotionText.text = item.emotion
        }
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