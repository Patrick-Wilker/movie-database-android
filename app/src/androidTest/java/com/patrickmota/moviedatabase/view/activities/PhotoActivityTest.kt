package com.patrickmota.moviedatabase.view.activities

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.patrickmota.moviedatabase.R
import org.junit.*

class PhotoActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(PhotoActivity::class.java)

    @Before
    fun setUp() {
        Intents.init()
        ActivityScenario.launch(PhotoActivity::class.java)

    }

    @After
    fun cleanUp() {
        Intents.release()
    }

    @Test
    fun checkIfPhotoActivityIsDisplayed() {
        onView(withId(R.id.photo_activity))
            .check(matches(isDisplayed()))
    }

    @Test
    fun shouldReturnToThePreviousScreenByClickingTheBackButton() {
        onView(withContentDescription(R.string.button_return_previous_screen)).perform(click())

        Assert.assertTrue(activityRule.scenario.state == Lifecycle.State.DESTROYED)
    }
}