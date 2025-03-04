package com.example.moodflow

import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.delay
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Test
    fun startTest() {
        launchActivity()

        MainActivityScreen{
            checkText("Добро пожаловать")
            clickWelcomeButton()
        }
    }

    private fun launchActivity() {
        ActivityScenario.launch(MainActivity::class.java)
    }
}