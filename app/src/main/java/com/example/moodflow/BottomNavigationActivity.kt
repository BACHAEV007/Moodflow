package com.example.moodflow

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.moodflow.databinding.BottomNavigationBinding
import com.example.moodflow.databinding.JournalScreenBinding

class BottomNavigationActivity : AppCompatActivity(R.layout.bottom_navigation) {
    private lateinit var binding: BottomNavigationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BottomNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(JournalFragment())
        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_journal -> {
                    replaceFragment(JournalFragment())
                    true
                }
                R.id.menu_settings-> {
                    replaceFragment(JournalFragment())
                    true
                }
                R.id.menu_statistic -> {
                    replaceFragment(JournalFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_bottom_nav, fragment)
            .addToBackStack(null)
            .commit()
    }
}