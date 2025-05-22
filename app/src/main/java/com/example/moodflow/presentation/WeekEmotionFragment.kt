package com.example.moodflow.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moodflow.R
import com.example.moodflow.presentation.adapter.WeekEmotionAdapter
import com.example.moodflow.databinding.EmotionInWeekScreenBinding

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