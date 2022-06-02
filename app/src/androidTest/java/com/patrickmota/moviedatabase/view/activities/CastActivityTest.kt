package com.patrickmota.moviedatabase.view.activities

import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
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
    fun checkIfPhotoActivityIsDisplayed(){
        onView(withId(R.id.castActivity))
            .check(matches(isDisplayed()))
    }

    @Test
    fun shouldReturnToThePreviousScreenByClickingTheBackButton() {

//        onView(withId(R.id.castActivity)).check(matches(isDisplayed()))
//
//        onView(withId(R.id.topAppBar))
//            .perform(click())
//
//        Assert.assertTrue(activityRule.scenario.state == Lifecycle.State.DESTROYED)
//
//        launch(MovieDetailActivity::class.java)
//
//        onView(withId(R.id.movie_detail)).check(matches(isDisplayed()))

    }

}