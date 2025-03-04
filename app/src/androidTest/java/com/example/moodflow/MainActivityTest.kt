package com.example.moodflow

import android.content.Context
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Test
    fun startTest() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        launchActivity()

        MainActivityScreen {
            checkText(context.getString(R.string.welcome))
            clickWelcomeButton()
        }
    }

    private fun launchActivity() {
        ActivityScenario.launch(MainActivity::class.java)
    }
}