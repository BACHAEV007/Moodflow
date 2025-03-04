package com.example.moodflow

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.moodflow.view.CircleDiagramView
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EmotionCategoriesFragmentTest {

    @Test
    fun testShouldDisplayCirclesCorrectly() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val circles = arrayListOf(
            CircleDiagramView.CircleData(15f, 0xFF33FFBB.toInt(), 0xFF00FF55.toInt()),
            CircleDiagramView.CircleData(60f, 0xFFFFFF33.toInt(), 0xFFFFAA00.toInt()),
            CircleDiagramView.CircleData(25f, 0xFFFF6666.toInt(), 0xFFFF3333.toInt())
        )

        launchFragmentInContainer<EmotionCategoriesFragment>(
            fragmentArgs = Bundle().apply {
                putParcelableArrayList("circles_key", circles)
            },
        )
        EmotionCategoriesFragmentScreen{
            checkCircularDiagramViewVisibility(true)
            checkEmotionCategoryText(context.getString(R.string.category_emotions))
        }
    }

    @Test
    fun testShouldHandleEmptyCirclesList() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val circles = arrayListOf<CircleDiagramView.CircleData>()

        launchFragmentInContainer<EmotionCategoriesFragment>(
            fragmentArgs = Bundle().apply {
                putParcelableArrayList("circles_key", circles)
            },
        )
        EmotionCategoriesFragmentScreen{
            checkCircularDiagramViewVisibility(true)
            checkEmotionCategoryText(context.getString(R.string.category_emotions))
        }
    }
}