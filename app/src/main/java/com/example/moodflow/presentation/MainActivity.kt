package com.example.moodflow.presentation

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.PromptInfo
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.moodflow.R
import com.example.moodflow.data.firebase.GoogleAuthClient
import com.example.moodflow.databinding.ActivityMainBinding
import com.example.moodflow.presentation.viewmodel.AuthViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val authViewModel: AuthViewModel by viewModel()
    private val googleAuthClient by lazy { GoogleAuthClient(this) }

    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: PromptInfo



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        promptInfo = PromptInfo.Builder()
            .setTitle("Вход по биометрии")
            .setSubtitle("Подтвердите личность")
            .setNegativeButtonText("Отмена")
            .build()

        val executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this@MainActivity, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    authViewModel.syncUser()
                    startActivity(Intent(this@MainActivity, BottomNavigationActivity::class.java))
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    authViewModel.syncUser()
                    startActivity(Intent(this@MainActivity, BottomNavigationActivity::class.java))
                }
            }
        )
        lifecycleScope.launch {
            val biometricEnabled = authViewModel.fingerprintEnabled.first()

            if (googleAuthClient.isSingedIn()) {
                if (canAuthenticateBiometric() && biometricEnabled) {
                    biometricPrompt.authenticate(promptInfo)
                } else {
                    authViewModel.syncUser()
                    startActivity(Intent(this@MainActivity, BottomNavigationActivity::class.java))
                }
            }
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

    private fun canAuthenticateBiometric(): Boolean {
        val manager = BiometricManager.from(this)
        return manager.canAuthenticate(
            BiometricManager.Authenticators.BIOMETRIC_STRONG or
                BiometricManager.Authenticators.DEVICE_CREDENTIAL
        ) == BiometricManager.BIOMETRIC_SUCCESS
    }
}