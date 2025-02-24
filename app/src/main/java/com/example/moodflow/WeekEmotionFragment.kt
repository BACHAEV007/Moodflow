package com.example.moodflow

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.moodflow.adapter.WeekEmotionAdapter
import com.example.moodflow.databinding.EmotionCategoriesBinding
import com.example.moodflow.databinding.EmotionInWeekScreenBinding
import com.example.moodflow.databinding.WeekEmotionItemBinding

class WeekEmotionFragment : Fragment(R.layout.emotion_in_week_screen) {
    private lateinit var binding: EmotionInWeekScreenBinding
    private lateinit var weekEmotionAdapter: WeekEmotionAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = EmotionInWeekScreenBinding.bind(view)
        weekEmotionAdapter = WeekEmotionAdapter(requireContext())
        val manager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.weekEmotions.apply {
            layoutManager = manager
            adapter = weekEmotionAdapter
        }
    }
}