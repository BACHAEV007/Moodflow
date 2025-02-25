package com.example.moodflow

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.moodflow.databinding.DayEmotionScreenBinding
import com.example.moodflow.view.ColorBlocksView

class DayEmotionFragment : Fragment(R.layout.day_emotion_screen) {
    private lateinit var binding: DayEmotionScreenBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DayEmotionScreenBinding.bind(view)
        val firstColorBlocksView = binding.firstColumn

        val blocks = listOf(
            ColorBlocksView.Block(color = Color.YELLOW, percentage = 0.5f),
            ColorBlocksView.Block(color = Color.RED, percentage = 0.2f),
            ColorBlocksView.Block(color = Color.GREEN, percentage = 0.3f)
        )

        firstColorBlocksView.setBlocks(blocks)
        binding.secondColumn.setBlocks()
        binding.thirdColumn.setBlocks()
        binding.fourthColumn.setBlocks()
        binding.fifthColumn.setBlocks()


    }
}