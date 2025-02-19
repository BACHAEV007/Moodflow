package com.example.moodflow

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moodflow.databinding.JournalScreenBinding

class JournalActivity : AppCompatActivity() {

    private lateinit var binding: JournalScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = JournalScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.progressCircular.startAnimation()
    }
}