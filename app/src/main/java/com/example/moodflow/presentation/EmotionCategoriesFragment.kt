package com.example.moodflow.presentation

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.moodflow.R
import com.example.moodflow.databinding.EmotionCategoriesBinding
import com.example.moodflow.presentation.view.CircleDiagramView

class EmotionCategoriesFragment : Fragment(R.layout.emotion_categories) {
    private lateinit var binding: EmotionCategoriesBinding
    companion object {
        private const val ARG_CIRCLES = "arg_circles"
        private const val ARG_COUNT   = "arg_count"

        fun newInstance(circles: ArrayList<CircleDiagramView.CircleData>, count: Int) = EmotionCategoriesFragment().apply {
            arguments = bundleOf(
                ARG_CIRCLES to circles,
                ARG_COUNT   to count
            )
        }
    }

    private val circles: List<CircleDiagramView.CircleData> by lazy {
        requireArguments().getParcelableArrayList<CircleDiagramView.CircleData>(ARG_CIRCLES).orEmpty()
    }
    private val count: Int by lazy {
        requireArguments().getInt(ARG_COUNT)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = EmotionCategoriesBinding.bind(view)
        binding.circleDiagramView.submitList(circles)
        binding.numberOfNotions.text =
            resources.getQuantityString(R.plurals.numberOfNotes, count, count)
    }
}