package com.patrickmota.moviedatabase.view.activities

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.patrickmota.moviedatabase.R
import org.junit.*
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class CastActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(CastActivity::class.java)

    @Before
    fun setUp() {
        Intents.init()
        launch(CastActivity::class.java)

    }

    @After
    fun cleanUp() {
        Intents.release()
    }

    @Test
    fun checkIfPhotoActivityIsDisplayed() {
        onView(withId(R.id.castActivity))
            .check(matches(isDisplayed()))
    }

    @Test
    fun shouldReturnToThePreviousScreenByClickingTheBackButton() {
        onView(withContentDescription("Button to return to the previous screen")).perform(click())

        Assert.assertTrue(activityRule.scenario.state == Lifecycle.State.DESTROYED)
    }

}