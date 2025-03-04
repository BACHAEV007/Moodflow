package com.example.moodflow

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SettingsScreenTest {
    @Test
    fun whenTimeListIsProvided_thenRecyclerViewDisplaysCorrectItems() {
        val time = arrayListOf("20:00", "19:00", "13:00", "13:00", "13:00", "13:00", "13:00", "13:00", "13:00", "13:00", "13:00")
        launchFragmentInContainer<SettingsFragment>(

            fragmentArgs = Bundle().apply {
                putStringArrayList("time_key", time)
            },
            themeResId = R.style.Base_Theme_Moodflow
        )
        SettingsFragmentScreen{
            checkRecycler(time)
            settingsFragmentAddNotionButton.isDisplayed()
            settingsFragmentAddNotionButton.isClickable()
        }
    }

    @Test
    fun whenTimeListIsEmpty_thenRecyclerViewIsEmpty() {
        val time = arrayListOf<String>()
        launchFragmentInContainer<SettingsFragment>(

            fragmentArgs = Bundle().apply {
                putStringArrayList("time_key", time)
            },
            themeResId = R.style.Base_Theme_Moodflow
        )
        SettingsFragmentScreen{
            checkRecycler(time)
            settingsFragmentAddNotionButton.isDisplayed()
            settingsFragmentAddNotionButton.isClickable()
        }
    }
}