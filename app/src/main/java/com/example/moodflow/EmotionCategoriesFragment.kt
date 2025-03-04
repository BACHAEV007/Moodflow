package com.example.moodflow

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.example.moodflow.databinding.EmotionCategoriesBinding
import com.example.moodflow.view.CircleDiagramView

class EmotionCategoriesFragment : Fragment(R.layout.emotion_categories) {
    private lateinit var binding: EmotionCategoriesBinding
    fun updateData(circleData: ArrayList<CircleDiagramView.CircleData>) {
        binding.circleDiagramView.submitList(circleData)
        Log.d("EmotionCategoriesFragment", "Updated with data: $circleData")
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = EmotionCategoriesBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
        val countNotion = 5
        var circles = binding.circleDiagramView
        circles.submitList(arrayListOf(
            CircleDiagramView.CircleData(40f, 0xFF33FFBB.toInt(), 0xFF00FF55.toInt()),
            CircleDiagramView.CircleData(60f, 0xFFFFFF33.toInt(), 0xFFFFAA00.toInt())))
        arguments?.getParcelableArrayList<CircleDiagramView.CircleData>("circles_key")?.let { circleList ->
            circles.submitList(circleList)
        }

        val notionCount = resources.getQuantityString(R.plurals.numberOfNotes, countNotion, countNotion)
        binding.numberOfNotions.text = notionCount
    }
}