package com.skyscanner.skytracker.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.skyscanner.skytracker.R
import com.skyscanner.skytracker.util.RecyclerViewMatcher

class TripListActivityRobot {

    fun assertDataDisplayed() = apply {
        onView(recyclerViewMatcher).check(matches(isDisplayed()))
    }

    fun assertErrorButtonDisplayed() = apply {
        onView(buttonViewMatcher).check(matches(isDisplayed()))
    }

    fun clickErrorButton() = apply {
        onView(buttonViewMatcher).perform(click())
    }

    fun assertTotalCounterDisplayed(text: String) = apply {
        onView(tvCountTotalResultViewMatcher).check(matches(withText(text)))
    }

    fun waitForCondition(idlingResource: IdlingResource?) = apply {
        IdlingRegistry.getInstance().register(idlingResource)
    }

    fun assertTripAtPosition(position: Int, priceTag: String) = apply {
        val itemMatcher = RecyclerViewMatcher(recyclerViewId)
            .atPositionOnView(position, tvPrice)
        onView(itemMatcher).check(matches(withText(priceTag)))
    }

    fun clickItem(position: Int) = apply {
        val itemMatcher = RecyclerViewMatcher(recyclerViewId).atPosition(position)
        onView(itemMatcher).perform(click())
    }

    companion object {
        private const val tvCountTotalResult = R.id.tvCountTotalResult
        private val tvCountTotalResultViewMatcher = withId(tvCountTotalResult)

        private const val recyclerViewId = R.id.rvTripList
        private val recyclerViewMatcher = withId(recyclerViewId)

        private const val buttonId = R.id.btnReload
        private val buttonViewMatcher = withId(buttonId)

        private const val tvPrice = R.id.tvPrice
    }
}