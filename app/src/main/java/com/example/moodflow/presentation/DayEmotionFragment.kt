package com.example.moodflow.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.moodflow.R
import com.example.moodflow.databinding.DayEmotionScreenBinding
import com.example.moodflow.presentation.view.ColorBlocksView
import com.example.moodflow.presentation.viewmodel.StatisticViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class DayEmotionFragment : Fragment(R.layout.day_emotion_screen) {
    private lateinit var binding: DayEmotionScreenBinding
    private val viewModel: StatisticViewModel by sharedViewModel()
    companion object {
        private const val ARG_WEEK_INDEX = "arg_week_index"
    }
    private val weekIndex: Int by lazy {
        requireArguments().getInt(ARG_WEEK_INDEX)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DayEmotionScreenBinding.bind(view)

        val blocks: List<Pair<List<ColorBlocksView.Block>, Int>> =
            viewModel.getDayEmotionBlocks(weekIndex)

        val columns = listOf(
            binding.firstColumn,
            binding.secondColumn,
            binding.thirdColumn,
            binding.fourthColumn,
            binding.fifthColumn
        )

        columns.forEachIndexed { idx, colView ->
            if (blocks[idx].first.isEmpty()){
                colView.setBlocks()
            } else{
                colView.setBlocks(blocks.getOrNull(idx)?.first.orEmpty())
            }
        }

        binding.earlyMorning.text = blocks[0].second.toString()
        binding.morning.text = blocks[1].second.toString()
        binding.day.text = blocks[2].second.toString()
        binding.evening.text = blocks[3].second.toString()
        binding.lateEvening.text = blocks[4].second.toString()
    }
}