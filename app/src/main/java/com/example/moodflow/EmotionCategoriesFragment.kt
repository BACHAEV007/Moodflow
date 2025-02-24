package com.example.moodflow

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.moodflow.databinding.EmotionCategoriesBinding

class EmotionCategoriesFragment : Fragment(R.layout.emotion_categories) {
    private lateinit var binding: EmotionCategoriesBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = EmotionCategoriesBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

    }
}