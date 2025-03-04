package com.example.moodflow

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moodflow.adapter.Emotion
import com.example.moodflow.adapter.EmotionAdapter
import com.example.moodflow.databinding.ChooseEmotionScreenBinding

class ChooseEmotionFragment : Fragment(R.layout.choose_emotion_screen) {
    private lateinit var binding: ChooseEmotionScreenBinding
    private lateinit var emotionAdapter: EmotionAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ChooseEmotionScreenBinding.bind(view)
        val emotions = listOf(
            Emotion(getString(R.string.emotion_rage), R.color.red_card_text, getString(R.string.description_rage)),
            Emotion(getString(R.string.emotion_tension), R.color.red_card_text, getString(R.string.description_tension)),
            Emotion(getString(R.string.emotion_excitement), R.color.yellow_card_text, getString(R.string.description_excitement)),
            Emotion(getString(R.string.emotion_confidence), R.color.yellow_card_text, getString(R.string.description_confidence)),
            Emotion(getString(R.string.emotion_envy), R.color.red_card_text, getString(R.string.description_envy)),
            Emotion(getString(R.string.emotion_anxiety), R.color.red_card_text, getString(R.string.description_anxiety)),
            Emotion(getString(R.string.emotion_arousal), R.color.yellow_card_text, getString(R.string.description_arousal)),
            Emotion(getString(R.string.emotion_happiness), R.color.yellow_card_text, getString(R.string.description_happiness)),
            Emotion(getString(R.string.emotion_burnout), R.color.blue_card_text, getString(R.string.description_burnout)),
            Emotion(getString(R.string.emotion_tiredness), R.color.blue_card_text, getString(R.string.description_tiredness)),
            Emotion(getString(R.string.emotion_calmness), R.color.green_card_text, getString(R.string.description_calmness)),
            Emotion(getString(R.string.emotion_satisfaction), R.color.green_card_text, getString(R.string.description_satisfaction)),
            Emotion(getString(R.string.emotion_depression), R.color.blue_card_text, getString(R.string.description_depression)),
            Emotion(getString(R.string.emotion_apathy), R.color.blue_card_text, getString(R.string.description_apathy)),
            Emotion(getString(R.string.emotion_gratitude), R.color.green_card_text, getString(R.string.description_gratitude)),
            Emotion(getString(R.string.emotion_security), R.color.green_card_text, getString(R.string.description_security))
        )


        emotionAdapter = EmotionAdapter(requireContext(), binding.emotionContainer, { emotion ->
            setEmotion(emotion)
        }, {
            resetDescription()
        })

        emotionAdapter.setEmotions(emotions)
        binding.emotionContainer.apply {
            adapter = emotionAdapter
            layoutManager = GridLayoutManager(requireContext(), 4)
        }
        binding.backButton.setOnClickListener{
            parentFragmentManager.popBackStack()
        }
        binding.forwardButtonAllow.setOnClickListener {
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
    }

    private fun resetDescription(){
        binding.chooseEmotion.text = getString(R.string.choose_emotion)
        binding.chooseEmotion.visibility = VISIBLE
        binding.descriptionCont.visibility = INVISIBLE
        binding.forwardButton.visibility = VISIBLE
        binding.forwardButtonAllow.visibility = GONE
    }
}