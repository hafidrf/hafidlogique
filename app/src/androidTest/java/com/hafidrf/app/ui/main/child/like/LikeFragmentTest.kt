package com.hafidrf.app.ui.main.child.like

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import com.hafidrf.app.R

@RunWith(AndroidJUnit4::class)
class LikeFragmentTest : TestCase(){

    private lateinit var scenario : FragmentScenario<LikeFragment>

    private var itemCount = 0
    private var resId = R.id.rvLike

    @Before
    fun setup(){
        scenario = launchFragmentInContainer(themeResId = R.style.Theme_hafidlogique)
        scenario.moveToState(Lifecycle.State.STARTED)
    }

    @Test
    fun deleteLikePost(){
        Espresso.onView((withId(resId))).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )
    }
}