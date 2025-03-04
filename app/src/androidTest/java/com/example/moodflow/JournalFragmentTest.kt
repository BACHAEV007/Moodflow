package com.example.moodflow

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.moodflow.state.JournalState
import com.example.moodflow.uicontent.CardColor
import com.example.moodflow.uicontent.CardContent
import com.example.moodflow.uicontent.GradientColor
import com.kaspersky.kaspresso.testcases.api.testcase.DocLocScreenshotTestCase
import junit.framework.TestCase.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(AndroidJUnit4::class)
class JournalFragmentTest{
    @Test
    fun journalFragmentRecyclerTwelveMockCardsDisplaysCorrectData() {
        val mockCards: List<CardContent> = listOf(
            CardContent(data = "вчера, 23:40", color = CardColor.GREEN, feeling = "спокойствие"),
            CardContent(data = "вчера, 23:40", color = CardColor.BLUE, feeling = "выгорание"),
            CardContent(data = "вчера, 23:40", color = CardColor.GREEN, feeling = "спокойствие"),
            CardContent(data = "вчера, 23:40", color = CardColor.BLUE, feeling = "выгорание"),
            CardContent(data = "вчера, 23:40", color = CardColor.GREEN, feeling = "спокойствие"),
            CardContent(data = "вчера, 23:40", color = CardColor.BLUE, feeling = "выгорание"),
            CardContent(data = "вчера, 23:40", color = CardColor.GREEN, feeling = "спокойствие"),
            CardContent(data = "вчера, 23:40", color = CardColor.BLUE, feeling = "выгорание"),
            CardContent(data = "вчера, 23:40", color = CardColor.GREEN, feeling = "спокойствие"),
            CardContent(data = "вчера, 23:40", color = CardColor.BLUE, feeling = "выгорание"),
            CardContent(data = "вчера, 23:40", color = CardColor.GREEN, feeling = "спокойствие"),
            CardContent(data = "вчера, 23:40", color = CardColor.BLUE, feeling = "выгорание")
        )

        launchFragmentInContainer<JournalFragment>(
            fragmentArgs = Bundle().apply {
                putParcelableArrayList("cards_key", ArrayList(mockCards))
            }
        )
        JournalFragmentScreen{
            checkRecycler(mockCards)
        }
    }

    @Test
    fun journalFragmentRecyclerZeroMockCardsDisplaysCorrectData() {
        val mockCards: List<CardContent> = listOf()

        launchFragmentInContainer<JournalFragment>(
            fragmentArgs = Bundle().apply {
                putParcelableArrayList("cards_key", ArrayList(mockCards))
            }
        )
        JournalFragmentScreen{
            checkRecycler(mockCards)
        }
    }

    @Test
    fun journalFragment_circleDiagramWithColor_displaysCorrectSegments() {

        val progressMap = mapOf(
            GradientColor.YELLOW to 0.5f,
            GradientColor.GREEN to 0.5f,
        )
        val progressData = ProgressMap(progressMap)

        val fragmentArgs = Bundle().apply {
            putParcelable("progress_key", progressData)
        }

        launchFragmentInContainer<JournalFragment>(
            fragmentArgs = fragmentArgs,
            factory = null
        )
        JournalFragmentScreen {
            checkProgressViewVisibility(false)
        }
    }

    @Test
    fun journalFragment_circleProgressWithAnimation_playCorrectAnimation() {

        val progressMap = emptyMap<GradientColor, Float>()
        val progressData = ProgressMap(progressMap)

        val fragmentArgs = Bundle().apply {
            putParcelable("progress_key", progressData)
        }

        launchFragmentInContainer<JournalFragment>(
            fragmentArgs = fragmentArgs,
            factory = null
        )
        val scenario = launchFragmentInContainer<JournalFragment>()

        scenario.onFragment { fragment ->
            val binding = fragment.binding
            val progressCircular = binding.progressCircular

            assertTrue("Animation should be playing", progressCircular.isAnimationPlaying())
        }
    }

}