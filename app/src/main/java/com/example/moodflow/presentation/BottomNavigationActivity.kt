package com.example.moodflow.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.moodflow.R
import com.example.moodflow.databinding.BottomNavigationBinding

class BottomNavigationActivity : AppCompatActivity() {
    private lateinit var binding: BottomNavigationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BottomNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val bottomNavigationView = binding.bottomNavigation
        val navController = navHostFragment.navController
        bottomNavigationView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.journalFragment,
				R.id.settingsFragment,
				R.id.statisticFragment -> binding.bottomNavigation.isVisible = true

                else -> binding.bottomNavigation.isVisible = false
            }
        }
    }
}

