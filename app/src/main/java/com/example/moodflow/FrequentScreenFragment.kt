package com.example.moodflow

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moodflow.adapter.FrequentEmotionAdapter
import com.example.moodflow.databinding.FrequentEmotionsScreenBinding

class FrequentScreenFragment : Fragment(R.layout.frequent_emotions_screen) {
    private lateinit var binding: FrequentEmotionsScreenBinding
    private lateinit var emotionAdapter: FrequentEmotionAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        emotionAdapter = FrequentEmotionAdapter()
        binding = FrequentEmotionsScreenBinding.bind(view)
        binding.emotionContainer.apply {
            adapter = emotionAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }
}