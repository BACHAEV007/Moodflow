package com.example.moodflow

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeUp
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.moodflow.presentation.BottomNavigationActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavigationScreenTest : com.kaspersky.kaspresso.testcases.api.testcase.TestCase(){

    @get:Rule
    val activityTestRule = ActivityTestRule(BottomNavigationActivity::class.java, true, true)

    @Test
    fun testFragmentNavigation() = run {

        step("Проверяем навигацию на Journal фрагмент") {
            NavigationScreen {
                journalFragmentContent {
                    isVisible()
                }
                journalFragmentButton {
                    isVisible()
                    isClickable()
                    click()
                }
            }
        }

        step("Подготавливаем UI для отображения Choose Emotion фрагмента") {
            NavigationScreen {
                onView(withId(R.id.choose_emotion_container)).perform(
                    RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                        0,
                        click()
                    )
                )
                chooseEmotionFragmentButton.isDisplayed()
            }
        }

        step("Проверяем навигацию на Choose Emotion фрагмент") {
            NavigationScreen {
                onView(withId(R.id.forward_button_allow))
                    .perform(click())
            }
        }

        step("Проверяем навигацию на Add Notion фрагмент") {
            NavigationScreen {
                addNotionFragmentButton {
                    isVisible()
                    isClickable()
                    click()
                }
            }
        }

        step("Проверяем навигацию на Settings фрагмент") {
            NavigationScreen {
                settingsFragmentButtonMenu{
                    isVisible()
                    isClickable()
                    click()
                }
                SettingsFragmentContent {
                    isVisible()
                }
            }
        }
        step("Проверяем навигацию на Statistic фрагмент") {
            NavigationScreen {
                statisticFragmentButtonMenu{
                    isVisible()
                    isClickable()
                    click()
                }
                StatisticFragmentContent {
                    onView(withId(R.id.vertical_pager))
                        .perform(swipeUp())
                }
                StatisticFragmentContent {
                    onView(withId(R.id.vertical_pager))
                        .perform(swipeUp())
                }
                StatisticFragmentContent {
                    onView(withId(R.id.vertical_pager))
                        .perform(swipeUp())
                }
            }
        }
    }
}
