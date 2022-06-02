package com.patrickmota.moviedatabase.view.activities

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.patrickmota.moviedatabase.R
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieDetailActivityTest {

    //@get:Rule
    //val intentsTestRule = IntentsTestRule(MovieDetailActivity::class.java)

    @Before
    fun setUp() {
        Intents.init()

        ActivityScenario.launch(MovieDetailActivity::class.java)
    }

    @After
    fun cleanUp() {
        Intents.release()
    }

    @Test
    fun appLaunchesSuccessfully() {
        ActivityScenario.launch(MovieDetailActivity::class.java)
    }

    @Test
    fun checkActivityVisibility() {
        onView(withId(R.id.movie_detail)).check(matches(isDisplayed()))
    }

    /*@Test
    fun opensCastActivityAfterClickingTheViewAllCastButton() {
        onView(withId(R.id.viewAllCast))
            .perform(click())

        onView(withId(R.id.castActivity))
            .check(matches(isDisplayed()))

        //intended(hasComponent(CastActivity::class.java.name))
    }

    @Test
    fun opensPhotoActivityAfterClickingTheViewAllPhotosButton() {
        onView(withId(R.id.viewAllPhotos))
            .perform(click())

        onView(withId(R.id.photo_activity))
            .check(matches(isDisplayed()))

        //intended(hasComponent(PhotoActivity::class.java.name))
    }*/

    @Test
    fun checkIfButtonIsNotDisplayed() {
        onView(withId(R.id.showMore))
            .check(matches(not(isDisplayed())))
    }

    @Test
    fun checkIfTheSynopsisTitleTextIsTheSame(){
        onView(withId(R.id.synopsis)).check(matches(withText(R.string.synopsis)))
    }

    @Test
    fun checkIfTheViewAllCastTitleTextIsTheSame(){
        onView(withId(R.id.viewAllCast)).check(matches(withText(R.string.view_all)))
    }

    @Test
    fun checkIfTheViewAllPhotosTitleTextIsTheSame(){
        onView(withId(R.id.viewAllPhotos)).check(matches(withText(R.string.view_all)))
    }

    @Test
    fun checkIfTheCastAndCrewTitleTextIsTheSame(){
        onView(withId(R.id.cast)).check(matches(withText(R.string.cast)))
    }

    @Test
    fun checkIfThePhotoTitleTextIsTheSame(){
        onView(withId(R.id.photos)).check(matches(withText(R.string.photos)))
    }

}