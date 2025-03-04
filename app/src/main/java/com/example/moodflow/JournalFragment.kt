package com.example.moodflow

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moodflow.adapter.CardAdapter
import com.example.moodflow.adapter.SpaceItemDecoration
import com.example.moodflow.adapter.dpToPx
import com.example.moodflow.databinding.JournalScreenBinding
import com.example.moodflow.state.JournalState
import com.example.moodflow.uicontent.CardColor
import com.example.moodflow.uicontent.CardContent
import com.example.moodflow.uicontent.GradientColor
import com.google.android.flexbox.FlexboxLayout
import kotlinx.parcelize.Parcelize

class JournalFragment : Fragment(R.layout.journal_screen) {
    private val cardAdapter = CardAdapter()
    lateinit var binding: JournalScreenBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = JournalScreenBinding.bind(view)
        val state = arguments?.getParcelableArrayList<CardContent>("cards_key") ?: getDefaultCards()
        val progressMap = arguments?.getParcelable("progress_key") ?: ProgressMap(
            mapOf(GradientColor.YELLOW to 0.5f),
//                GradientColor.RED to 0.5f),
//            emptyMap()
        )
        cardAdapter.submitList(state)
        val gradientCircleView = binding.gradientCircle

        binding.progressCircular.startAnimation()
        val journalState = JournalState()
        val progressCircular = binding.progressCircular

        if (progressMap.map.isEmpty()) {
            binding.progressCircular.visibility = View.VISIBLE
            gradientCircleView.visibility = View.INVISIBLE
            binding.progressCircular.startAnimation()
        } else {
            progressCircular.stopAnimation()
            binding.progressCircular.visibility = View.INVISIBLE
            gradientCircleView.visibility = View.VISIBLE
            gradientCircleView.setProgress(progressMap.map)
            gradientCircleView.setPoint(progressMap.map.size)
        }



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

    private fun getDefaultCards(): List<CardContent> {
        return listOf(
            CardContent(data = "вчера, 23:40", color = CardColor.GREEN, feeling = "спокойствие"),
            CardContent(data = "вчера, 23:40", color = CardColor.BLUE, feeling = "выгорание"),
            CardContent(data = "воскресенье, 23:40", color = CardColor.YELLOW, feeling = "продуктивность"),
            CardContent(data = "воскресенье, 23:40", color = CardColor.RED, feeling = "беспокойство")
        )
    }

}

@Parcelize
data class ProgressMap(
    val map: Map<GradientColor, Float>
) : Parcelable

