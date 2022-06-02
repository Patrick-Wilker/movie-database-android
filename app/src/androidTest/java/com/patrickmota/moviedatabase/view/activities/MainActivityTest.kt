package com.patrickmota.moviedatabase.view.activities

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.patrickmota.moviedatabase.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Before
    fun setUp() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun checkIfMainActivityIsDisplayed(){
        onView(withId(R.id.mainActivity))
            .check(matches(isDisplayed()))
    }

    @Test
    fun checkIfMainFragmentIsDisplayed(){
        onView(withId(R.id.mainFragment))
            .check(matches(isDisplayed()))
    }

}