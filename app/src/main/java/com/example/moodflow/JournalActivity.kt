package com.example.moodflow

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moodflow.databinding.JournalScreenBinding

class JournalActivity : AppCompatActivity() {
    private val cardAdapter = CardAdapter()
    private lateinit var binding: JournalScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
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
    }
}