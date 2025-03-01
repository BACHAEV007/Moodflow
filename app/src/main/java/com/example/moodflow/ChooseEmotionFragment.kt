package com.example.moodflow

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moodflow.adapter.Emotion
import com.example.moodflow.adapter.EmotionAdapter
import com.example.moodflow.databinding.AddNoteScreenBinding

class ChooseEmotionFragment : Fragment(R.layout.add_note_screen) {
    private lateinit var binding: AddNoteScreenBinding
    private lateinit var emotionAdapter: EmotionAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = AddNoteScreenBinding.bind(view)
        val emotions = listOf(
            Emotion(emotion = "Ярость", color = R.color.red_card_text),
            Emotion(emotion = "Напряжение", color = R.color.red_card_text),
            Emotion(emotion = "Восторг", color = R.color.yellow_card_text),
            Emotion(emotion = "Уверенность", color = R.color.yellow_card_text),
            Emotion(emotion = "Зависть", color = R.color.red_card_text),
            Emotion(emotion = "Беспокойство", color = R.color.red_card_text),
            Emotion(emotion = "Возбуждение", color = R.color.yellow_card_text),
            Emotion(emotion = "Счастье", color = R.color.yellow_card_text),
            Emotion(emotion = "Выгорание", color = R.color.blue_card_text),
            Emotion(emotion = "Усталость", color = R.color.blue_card_text),
            Emotion(emotion = "Спокойствие", color = R.color.green_card_text),
            Emotion(emotion = "Удовлетворённость", color = R.color.green_card_text),
            Emotion(emotion = "Депрессия", color = R.color.blue_card_text),
            Emotion(emotion = "Апатия", color = R.color.blue_card_text),
            Emotion(emotion = "Благодарность", color = R.color.green_card_text),
            Emotion(emotion = "Защищённость", color = R.color.green_card_text)

        )
        emotionAdapter = EmotionAdapter(requireContext(), binding.emotionContainer)
        emotionAdapter.setEmotions(emotions)
        binding.emotionContainer.apply {
            adapter = emotionAdapter
            layoutManager = GridLayoutManager(requireContext(), 4)
        }
    }
}