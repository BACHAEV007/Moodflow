package com.example.moodflow

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moodflow.adapter.CardAdapter
import com.example.moodflow.adapter.SpaceItemDecoration
import com.example.moodflow.adapter.dpToPx
import com.example.moodflow.databinding.JournalScreenBinding
import com.example.moodflow.state.JournalState
import com.example.moodflow.uicontent.GradientColor

class JournalFragment : Fragment(R.layout.journal_screen) {
    private val cardAdapter = CardAdapter()
    private lateinit var binding: JournalScreenBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = JournalScreenBinding.bind(view)
        val journalState = JournalState.Content()
        cardAdapter.submitList(journalState.cards)
        val gradientCircleView = binding.gradientCircle
        val progressMap = mapOf(
            GradientColor.YELLOW to 0.4f,
            GradientColor.GREEN to 0.5f,
            GradientColor.RED to 0.1f
        )
        gradientCircleView.setProgress(progressMap)

        binding.progressCircular.startAnimation()
        val carouselManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.cards.apply {
            layoutManager = carouselManager
            adapter = cardAdapter
            setNestedScrollingEnabled(false)
            addItemDecoration(SpaceItemDecoration(8.dpToPx()))
        }
        binding.addEmotionButton.setOnClickListener{
            findNavController().navigate(R.id.chooseEmotionFragment)
        }

        val daysText = resources.getQuantityString(R.plurals.numberOfDaySeria, journalState.cardStreak, journalState.cardStreak)
        binding.streak.text = daysText
        val cardsInDay = resources.getQuantityString(R.plurals.numberOfNotes, journalState.cardInDay, journalState.cardInDay)
        binding.cardsInDay.text = cardsInDay
        val cardsCount = resources.getQuantityString(R.plurals.numberOfNotes, journalState.cardsCount, journalState.cardsCount)
        binding.cardsCount.text = cardsCount
    }
}