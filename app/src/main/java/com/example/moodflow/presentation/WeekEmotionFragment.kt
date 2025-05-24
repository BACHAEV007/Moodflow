package com.example.moodflow.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moodflow.R
import com.example.moodflow.databinding.EmotionInWeekScreenBinding
import com.example.moodflow.presentation.adapter.WeekEmotionAdapter
import com.example.moodflow.presentation.uicontent.WeekEmotionContent

class WeekEmotionFragment : Fragment(R.layout.emotion_in_week_screen) {
    private lateinit var binding: EmotionInWeekScreenBinding
    private lateinit var weekEmotionAdapter: WeekEmotionAdapter
    private val weekEmotions: List<WeekEmotionContent> by lazy {
        arguments?.getParcelableArrayList<WeekEmotionContent>(ARG_WEEK_EMOTIONS).orEmpty()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = EmotionInWeekScreenBinding.bind(view)
        weekEmotionAdapter = WeekEmotionAdapter(requireContext()).apply {
            updateData(weekEmotions)
        }
        val manager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.weekEmotions.apply {
            layoutManager = manager
            adapter = weekEmotionAdapter
        }
    }

    companion object {
        private const val ARG_WEEK_EMOTIONS = "arg_week_emotions"

        fun newInstance(weekEmotions: ArrayList<WeekEmotionContent>): WeekEmotionFragment {
            val fragment = WeekEmotionFragment()
            fragment.arguments = Bundle().apply {
                putParcelableArrayList(ARG_WEEK_EMOTIONS, weekEmotions)
            }
            return fragment
        }
    }
}