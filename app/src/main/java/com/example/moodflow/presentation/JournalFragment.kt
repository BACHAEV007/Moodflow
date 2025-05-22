package com.example.moodflow.presentation

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.compose.runtime.collectAsState
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moodflow.R
import com.example.moodflow.presentation.adapter.CardAdapter
import com.example.moodflow.presentation.adapter.SpaceItemDecoration
import com.example.moodflow.presentation.adapter.dpToPx
import com.example.moodflow.databinding.JournalScreenBinding
import com.example.moodflow.presentation.state.JournalState
import com.example.moodflow.presentation.uicontent.CardColor
import com.example.moodflow.presentation.uicontent.CardContent
import com.example.moodflow.presentation.uicontent.GradientColor
import com.example.moodflow.presentation.viewmodel.JournalViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import org.koin.androidx.viewmodel.ext.android.viewModel

class JournalFragment : Fragment(R.layout.journal_screen) {

	private val cardAdapter = CardAdapter { cardId -> navigateToAddNotionFragment(cardId = cardId) }
	lateinit var binding: JournalScreenBinding
	private val viewModel: JournalViewModel by viewModel()
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		binding = JournalScreenBinding.bind(view)
		val state = arguments?.getParcelableArrayList<CardContent>("cards_key") ?: getDefaultCards()
		val progressMap = arguments?.getParcelable("progress_key") ?: ProgressMap(
			mapOf(GradientColor.YELLOW to 0.5f),
//                GradientColor.RED to 0.5f),
//            emptyMap()
		)
		lifecycleScope.launch {
			viewModel.state.collect { state ->
				cardAdapter.submitList(state.emotions)
			}
		}
		val gradientCircleView = binding.gradientCircle

		binding.progressCircular.startAnimation()
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

		val carouselManager =
			LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
		binding.cards.apply {
			layoutManager = carouselManager
			adapter = cardAdapter
			setNestedScrollingEnabled(false)
			addItemDecoration(SpaceItemDecoration(8.dpToPx()))
		}
		binding.addEmotionButton.setOnClickListener {
			findNavController().navigate(R.id.chooseEmotionFragment)
		}

//        val daysText = resources.getQuantityString(
//			R.plurals.numberOfDaySeria,
//            journalState.cardStreak,
//            journalState.cardStreak
//        )
//        binding.streak.text = daysText
//        val cardsInDay = resources.getQuantityString(
//			R.plurals.numberOfNotes,
//            journalState.cardInDay,
//            journalState.cardInDay
//        )
//        binding.cardsInDay.text = cardsInDay
//        val cardsCount = resources.getQuantityString(
//			R.plurals.numberOfNotes,
//            journalState.cardsCount,
//            journalState.cardsCount
//        )
//        binding.cardsCount.text = cardsCount
	}

	private fun navigateToAddNotionFragment(cardId: Long?) {
		setFragmentResult("requestKey", bundleOf("bundleKey" to cardId))
		findNavController().navigate(R.id.addNotionFragment)
	}

	private fun getDefaultCards(): List<CardContent> {
		return listOf(
			CardContent(data = "вчера, 23:40", color = CardColor.GREEN, feeling = "спокойствие"),
			CardContent(data = "вчера, 23:40", color = CardColor.BLUE, feeling = "выгорание"),
			CardContent(
				data = "воскресенье, 23:40",
				color = CardColor.YELLOW,
				feeling = "продуктивность"
			),
			CardContent(
				data = "воскресенье, 23:40",
				color = CardColor.RED,
				feeling = "беспокойство"
			)
		)
	}

}

@Parcelize
data class ProgressMap(
	val map: Map<GradientColor, Float>
) : Parcelable

