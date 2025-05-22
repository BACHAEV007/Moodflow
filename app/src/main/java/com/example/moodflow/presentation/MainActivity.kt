package com.example.moodflow.presentation

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.moodflow.R
import com.example.moodflow.data.firebase.GoogleAuthClient
import com.example.moodflow.databinding.ActivityMainBinding
import com.example.moodflow.presentation.viewmodel.AuthViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val authViewModel: AuthViewModel by viewModel()
    private val googleAuthClient by lazy { GoogleAuthClient(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (googleAuthClient.isSingedIn()){
            authViewModel.syncUser()
            startActivity(Intent(this@MainActivity, BottomNavigationActivity::class.java))
        }
        window.apply {
            statusBarColor = Color.TRANSPARENT
            navigationBarColor = Color.TRANSPARENT
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.welcomeButton.setOnClickListener {
            lifecycleScope.launch {
                val success = if (googleAuthClient.signIn() != null) true else false
                if (success) {
                    authViewModel.signIn()
                    startActivity(Intent(this@MainActivity, BottomNavigationActivity::class.java))
                } else {
                    Toast.makeText(this@MainActivity, "Sign-in failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.bubbleView.startAnimation()
    }
}