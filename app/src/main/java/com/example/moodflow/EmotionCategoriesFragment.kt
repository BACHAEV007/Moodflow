package com.example.moodflow

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.moodflow.databinding.EmotionCategoriesBinding
import com.example.moodflow.view.CircleDiagramView

class EmotionCategoriesFragment : Fragment(R.layout.emotion_categories) {
    private lateinit var binding: EmotionCategoriesBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = EmotionCategoriesBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
        val countNotion = 5
        var circles = binding.circleDiagramView
        circles.submitList(
            arrayListOf(
                CircleDiagramView.CircleData(40f, ContextCompat.getColor(requireContext(), R.color.green_end_gradient),
                    ContextCompat.getColor(requireContext(), R.color.green_start_gradient)),
                CircleDiagramView.CircleData(60f,ContextCompat.getColor(requireContext(), R.color.yellow_end_gradient),
                    ContextCompat.getColor(requireContext(), R.color.yellow_start_gradient)),
//                CircleDiagramView.CircleData(60f,ContextCompat.getColor(requireContext(), R.color.red_end_gradient),
//                    ContextCompat.getColor(requireContext(), R.color.red_start_gradient)),
//                CircleDiagramView.CircleData(60f,ContextCompat.getColor(requireContext(), R.color.blue_end_gradient),
//                    ContextCompat.getColor(requireContext(), R.color.blue_start_gradient))
            )
        )
        arguments?.getParcelableArrayList<CircleDiagramView.CircleData>("circles_key")
            ?.let { circleList ->
                circles.submitList(circleList)
            }

        val notionCount =
            resources.getQuantityString(R.plurals.numberOfNotes, countNotion, countNotion)
        binding.numberOfNotions.text = notionCount
    }
}