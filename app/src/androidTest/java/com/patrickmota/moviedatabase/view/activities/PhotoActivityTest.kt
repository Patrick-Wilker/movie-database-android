package com.patrickmota.moviedatabase.view.activities

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.patrickmota.moviedatabase.R
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

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
    fun checkIfPhotoActivityIsDisplayed(){
        onView(withId(R.id.photo_activity))
            .check(matches(isDisplayed()))
    }

    @Test
    fun shouldReturnToThePreviousScreenByClickingTheBackButton() {

//        onView(withId(R.id.photo_activity))
//            .check(matches(isDisplayed()))
//
//        onView(withId(android.R.id.home))
//            .perform(click())

//        Espresso.pressBack()
//
//        assertTrue(activityRule.scenario.state == Lifecycle.State.DESTROYED)

//        ActivityScenario.launch(MovieDetailActivity::class.java)
//
//        onView(withId(R.id.movie_detail))
//            .check(matches(isDisplayed()))

    }
}