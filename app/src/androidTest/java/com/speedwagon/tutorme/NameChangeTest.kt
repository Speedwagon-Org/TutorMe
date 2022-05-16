package com.speedwagon.tutorme

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NameChangeTest {

    @Rule @JvmField

    var activityTest =  ActivityTestRule(home_main::class.java)

    //@Before
    //fun setup(){
        //var scenario: FragmentScenario<UpdateProfileFragment>
        //scenario = launchFragmentInContainer(themeResId = R.style.Theme_TutorMe)
        //scenario.moveToState(Lifecycle.State.STARTED)
    //}

    //@Test
    //fun ChangeNameProfile(){
        //onView(withId(R.id.UsernameUpdateEditText)).perform(ViewActions.typeText("Test"))
        //onView(withId(R.id.Test)).perform(ViewActions.click())
        //onView(withId(R.id.currentname)).check(matches(withText("Test")))
   // }

    //masih error
    //@Test
    //fun ChangeNameProfile2(){
        //onView(withId(R.id.UsernameUpdateEditText)).perform(ViewActions.typeText("Test"))
        //onView(withId(R.id.ConfirmUpdate)).perform(ViewActions.click())
        //onView(withId(R.id.usernameid)).check(matches(withText("Test")))
    //}

}