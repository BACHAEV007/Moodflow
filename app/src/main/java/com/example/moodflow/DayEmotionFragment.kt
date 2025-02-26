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
            listOf(
                ColorBlocksView.Block(
                    percentage = 0.5f,
                    startColor = getGradientStartColor(R.drawable.green_image_card),
                    endColor = getGradientEndColor(R.drawable.green_image_card)
                ),
                ColorBlocksView.Block(
                    percentage = 0.2f,
                    startColor = getGradientStartColor(R.drawable.red_image_card),
                    endColor = getGradientEndColor(R.drawable.red_image_card)
                ),
                ColorBlocksView.Block(
                    percentage = 0.3f,
                    startColor = getGradientStartColor(R.drawable.blue_shell_icon),
                    endColor = getGradientEndColor(R.drawable.blue_shell_icon)
                )
            ),
            listOf(
                ColorBlocksView.Block(
                    percentage = 0.1f,
                    startColor = getGradientStartColor(R.drawable.green_image_card),
                    endColor = getGradientEndColor(R.drawable.green_image_card)
                ),
                ColorBlocksView.Block(
                    percentage = 0.9f,
                    startColor = getGradientStartColor(R.drawable.red_image_card),
                    endColor = getGradientEndColor(R.drawable.red_image_card)
                )
            ),
            listOf(
                ColorBlocksView.Block(
                    percentage = 1f,
                    startColor = getGradientStartColor(R.drawable.yellow_circle_icon),
                    endColor = getGradientEndColor(R.drawable.yellow_circle_icon)
                )
            ),
            listOf(),
            listOf()

        )

        firstColorBlocksView.setBlocks(blocks[0])
        binding.secondColumn.setBlocks(blocks[1])
        binding.thirdColumn.setBlocks(blocks[2])
        binding.fourthColumn.setBlocks()
        binding.fifthColumn.setBlocks()
        binding.earlyMorning.text = blocks[0].size.toString()
        binding.morning.text = blocks[1].size.toString()
        binding.day.text = blocks[2].size.toString()
        binding.evening.text = blocks[3].size.toString()
        binding.lateEvening.text = blocks[4].size.toString()
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
}