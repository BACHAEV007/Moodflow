package com.example.moodflow.presentation

import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moodflow.R
import com.example.moodflow.databinding.FrequentEmotionsScreenBinding
import com.example.moodflow.presentation.adapter.FrequentEmotionAdapter
import com.example.moodflow.presentation.decorations.VerticalSpaceItemDecoration
import com.example.moodflow.presentation.uicontent.FrequentContent

class FrequentScreenFragment : Fragment(R.layout.frequent_emotions_screen) {
    private lateinit var binding: FrequentEmotionsScreenBinding
    private lateinit var emotionAdapter: FrequentEmotionAdapter
    private val frequentEmotions: List<FrequentContent> by lazy {
        arguments?.getParcelableArrayList<FrequentContent>(ARG_FREQUENT_EMOTIONS).orEmpty()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        emotionAdapter = FrequentEmotionAdapter(requireContext()).apply {
            updateData(frequentEmotions)
        }
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

    companion object {
        private const val ARG_FREQUENT_EMOTIONS = "arg_frequent_emotions"

        fun newInstance(frequentEmotions: ArrayList<FrequentContent>): FrequentScreenFragment {
            val fragment = FrequentScreenFragment()
            fragment.arguments = Bundle().apply {
                putParcelableArrayList(ARG_FREQUENT_EMOTIONS, frequentEmotions)
            }
            return fragment
        }
    }
}