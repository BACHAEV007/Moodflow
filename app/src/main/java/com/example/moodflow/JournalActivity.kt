package com.example.moodflow

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moodflow.databinding.JournalScreenBinding
import com.example.moodflow.state.JournalState

class JournalActivity : AppCompatActivity() {
    private val cardAdapter = CardAdapter()
    private lateinit var binding: JournalScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        val journalState = JournalState.Content()
        cardAdapter.submitList(journalState.cards)
        super.onCreate(savedInstanceState)
        binding = JournalScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.progressCircular.startAnimation()
        val carouselManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding?.cards?.apply {
            layoutManager = carouselManager
            adapter = cardAdapter
            setNestedScrollingEnabled(false)
            addItemDecoration(SpaceItemDecoration(8.dpToPx()))
        }
        val daysText = resources.getQuantityString(R.plurals.numberOfDaySeria, journalState.cardStreak, journalState.cardStreak)
        binding.streak.text = daysText
        val cardsInDay = resources.getQuantityString(R.plurals.numberOfNotes, journalState.cardInDay, journalState.cardInDay)
        binding.cardsInDay.text = cardsInDay
        val cardsCount = resources.getQuantityString(R.plurals.numberOfNotes, journalState.cardsCount, journalState.cardsCount)
        binding.cardsCount.text = cardsCount
    }
}