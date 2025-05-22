package com.example.moodflow.presentation

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moodflow.R
import com.example.moodflow.presentation.adapter.Emotion
import com.example.moodflow.presentation.adapter.EmotionAdapter
import com.example.moodflow.databinding.ChooseEmotionScreenBinding
import com.example.moodflow.domain.model.EmotionTypeModel
import com.example.moodflow.presentation.viewmodel.AddEmotionViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ChooseEmotionFragment : Fragment(R.layout.choose_emotion_screen) {
    private lateinit var binding: ChooseEmotionScreenBinding
    private lateinit var emotionAdapter: EmotionAdapter
    private val viewModel: AddEmotionViewModel by sharedViewModel()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ChooseEmotionScreenBinding.bind(view)
        val emotions = arguments?.getParcelableArrayList("circles_key") ?: listOf(
            Emotion(getString(R.string.emotion_rage), R.color.red_card_text, getString(R.string.description_rage), "red_image_card"),
            Emotion(getString(R.string.emotion_tension), R.color.red_card_text, getString(R.string.description_tension), "red_image_card"),
            Emotion(getString(R.string.emotion_excitement), R.color.yellow_card_text, getString(R.string.description_excitement), "yellow_image_card"),
            Emotion(getString(R.string.emotion_confidence), R.color.yellow_card_text, getString(R.string.description_confidence), "yellow_image_card"),
            Emotion(getString(R.string.emotion_envy), R.color.red_card_text, getString(R.string.description_envy), "red_image_card"),
            Emotion(getString(R.string.emotion_anxiety), R.color.red_card_text, getString(R.string.description_anxiety), "red_image_card"),
            Emotion(getString(R.string.emotion_arousal), R.color.yellow_card_text, getString(R.string.description_arousal), "yellow_image_card"),
            Emotion(getString(R.string.emotion_happiness), R.color.yellow_card_text, getString(R.string.description_happiness), "yellow_image_card"),
            Emotion(getString(R.string.emotion_burnout), R.color.blue_card_text, getString(R.string.description_burnout), "blue_image_card"),
            Emotion(getString(R.string.emotion_tiredness), R.color.blue_card_text, getString(R.string.description_tiredness), "blue_image_card"),
            Emotion(getString(R.string.emotion_calmness), R.color.green_card_text, getString(R.string.description_calmness), "green_image_card"),
            Emotion(getString(R.string.emotion_satisfaction), R.color.green_card_text, getString(R.string.description_satisfaction), "green_image_card"),
            Emotion(getString(R.string.emotion_depression), R.color.blue_card_text, getString(R.string.description_depression), "blue_image_card"),
            Emotion(getString(R.string.emotion_apathy), R.color.blue_card_text, getString(R.string.description_apathy), "blue_image_card"),
            Emotion(getString(R.string.emotion_gratitude), R.color.green_card_text, getString(R.string.description_gratitude), "green_image_card"),
            Emotion(getString(R.string.emotion_security), R.color.green_card_text, getString(R.string.description_security), "green_image_card")
        )


        emotionAdapter = EmotionAdapter(requireContext(), binding.chooseEmotionContainer, { emotion ->
            setEmotion(emotion)
        }, {
            resetDescription()
        })

        emotionAdapter.setEmotions(emotions)
        binding.chooseEmotionContainer.apply {
            adapter = emotionAdapter
            layoutManager = GridLayoutManager(requireContext(), 4)
        }
        binding.chooseBackButton.setOnClickListener{
            parentFragmentManager.popBackStack()
        }
        binding.forwardButtonAllow.setOnClickListener {
            viewModel.syncTime()
            findNavController().navigate(R.id.addNotionFragment)
        }
    }

    private fun setEmotion(emotion: Emotion){
        binding.emotionName.text = emotion.emotion
        binding.emotionName.setTextColor(ContextCompat.getColor(requireContext(), emotion.color))
        binding.chooseEmotion.visibility = INVISIBLE
        binding.descriptionCont.visibility = VISIBLE
        binding.description.text = emotion.description
        binding.forwardButton.visibility = INVISIBLE
        binding.forwardButtonAllow.visibility = VISIBLE

        viewModel.setEmotion(
            emotion = emotion
        )
    }

    private fun resetDescription(){
        binding.chooseEmotion.text = getString(R.string.choose_emotion)
        binding.chooseEmotion.visibility = VISIBLE
        binding.descriptionCont.visibility = INVISIBLE
        binding.forwardButton.visibility = VISIBLE
        binding.forwardButtonAllow.visibility = GONE
    }
}