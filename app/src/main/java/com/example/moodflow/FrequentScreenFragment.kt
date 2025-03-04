package com.example.moodflow

import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moodflow.adapter.FrequentEmotionAdapter
import com.example.moodflow.databinding.FrequentEmotionsScreenBinding
import com.example.moodflow.decorations.VerticalSpaceItemDecoration

class FrequentScreenFragment : Fragment(R.layout.frequent_emotions_screen) {
    private lateinit var binding: FrequentEmotionsScreenBinding
    private lateinit var emotionAdapter: FrequentEmotionAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        emotionAdapter = FrequentEmotionAdapter()
        binding = FrequentEmotionsScreenBinding.bind(view)
        binding.emotionContainer.apply {
            adapter = emotionAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        val spaceInDp = 8
        val spaceInPx = dpToPx(spaceInDp)
        binding.emotionContainer.addItemDecoration(VerticalSpaceItemDecoration(spaceInPx))
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * Resources.getSystem().displayMetrics.density).toInt()
    }
}