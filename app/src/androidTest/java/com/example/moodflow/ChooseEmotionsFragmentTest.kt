package com.example.moodflow

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.moodflow.NavigationScreen.chooseEmotionFragmentButton
import com.example.moodflow.NavigationScreen.chooseEmotionFragmentButtonDisable
import com.example.moodflow.presentation.ChooseEmotionFragment
import com.example.moodflow.presentation.adapter.Emotion
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ChooseEmotionsFragmentTest {

    @Test
    fun testShouldDisplayCirclesCorrectly() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val circles = arrayListOf(
            Emotion(
                context.getString(R.string.emotion_rage),
                R.color.red_card_text,
                context.getString(R.string.description_rage),
                "red_image_card"
            ),
            Emotion(
                context.getString(R.string.emotion_tension),
                R.color.red_card_text,
                context.getString(R.string.description_tension),
                "red_image_card"
            ),
            Emotion(
                context.getString(R.string.emotion_excitement),
                R.color.yellow_card_text,
                context.getString(R.string.description_excitement),
                "red_image_card"
            ),
            Emotion(
                context.getString(R.string.emotion_confidence),
                R.color.yellow_card_text,
                context.getString(R.string.description_confidence),
                "red_image_card"
            ),
            Emotion(
                context.getString(R.string.emotion_envy),
                R.color.red_card_text,
                context.getString(R.string.description_envy),
                "red_image_card"
            ),
            Emotion(
                context.getString(R.string.emotion_anxiety),
                R.color.red_card_text,
                context.getString(R.string.description_anxiety),
                "red_image_card"
            ),
            Emotion(
                context.getString(R.string.emotion_arousal),
                R.color.yellow_card_text,
                context.getString(R.string.description_arousal),
                "red_image_card"
            ),
            Emotion(
                context.getString(R.string.emotion_happiness),
                R.color.yellow_card_text,
                context.getString(R.string.description_happiness),
                "red_image_card"
            ),
            Emotion(
                context.getString(R.string.emotion_burnout),
                R.color.blue_card_text,
                context.getString(R.string.description_burnout),
                "red_image_card"
            ),
            Emotion(
                context.getString(R.string.emotion_tiredness),
                R.color.blue_card_text,
                context.getString(R.string.description_tiredness),
                "red_image_card"
            ),
            Emotion(
                context.getString(R.string.emotion_calmness),
                R.color.green_card_text,
                context.getString(R.string.description_calmness),
                "red_image_card"
            ),
            Emotion(
                context.getString(R.string.emotion_satisfaction),
                R.color.green_card_text,
                context.getString(R.string.description_satisfaction),
                "red_image_card"
            ),
            Emotion(
                context.getString(R.string.emotion_depression),
                R.color.blue_card_text,
                context.getString(R.string.description_depression),
                "red_image_card"
            ),
            Emotion(
                context.getString(R.string.emotion_apathy),
                R.color.blue_card_text,
                context.getString(R.string.description_apathy),
                "red_image_card"
            ),
            Emotion(
                context.getString(R.string.emotion_gratitude),
                R.color.green_card_text,
                context.getString(R.string.description_gratitude),
                "red_image_card"
            ),
            Emotion(
                context.getString(R.string.emotion_security),
                R.color.green_card_text,
                context.getString(R.string.description_security),
                "red_image_card"
            )
        )

        launchFragmentInContainer<ChooseEmotionFragment>(
            fragmentArgs = Bundle().apply {
                putParcelableArrayList("circles_key", circles)
            },
        )
        ChooseEmotionFragmentScreen {
            onView(withId(R.id.choose_emotion_container)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    click()
                )
            )
            chooseEmotionFragmentButton.isVisible()
            chooseEmotionFragmentButtonDisable.isInvisible()
            ChooseEmotionFragmentBackButton.isClickable()
        }
    }

    @Test
    fun testShouldDisplayZeroCirclesCorrectly() {
        val circles = arrayListOf<Emotion>()

        launchFragmentInContainer<ChooseEmotionFragment>(
            fragmentArgs = Bundle().apply {
                putParcelableArrayList("circles_key", circles)
            },
        )
        ChooseEmotionFragmentScreen {
            onView(withId(R.id.choose_emotion_container))
                .check(matches(withItemCount(0)))
            chooseEmotionFragmentButton.isInvisible()
            chooseEmotionFragmentButtonDisable.isVisible()
            ChooseEmotionFragmentBackButton.isClickable()
        }
    }
}

fun withItemCount(expectedCount: Int): Matcher<View> {
    return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
        override fun describeTo(description: Description?) {
            description?.appendText("with item count: $expectedCount")
        }

        override fun matchesSafely(recyclerView: RecyclerView?): Boolean {
            return recyclerView?.adapter?.itemCount == expectedCount
        }
    }
}