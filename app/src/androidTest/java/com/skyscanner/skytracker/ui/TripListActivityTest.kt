package com.skyscanner.skytracker.ui

import android.view.View
import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.skyscanner.skytracker.R
import com.skyscanner.skytracker.util.*
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TripListActivityTest {

    private val activityTestRule = ActivityTestRule(TripListActivity::class.java, true, false)

    @JvmField
    @Rule
    val ruleChain: RuleChain = RuleChain.outerRule(activityTestRule).around(ClearPreferencesRule())

    private val mockWebServer = MockWebServer()

    private var progressBarGoneIdlingResource: ViewVisibilityIdlingResource? = null

    @Before
    fun setup() {
        activityTestRule
        mockWebServer.start(PORT)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
        IdlingRegistry.getInstance().unregister(progressBarGoneIdlingResource)
    }

    @Test
    fun shouldFlightListLoaded() {
        mockWebServer.setDispatcher(SuccessDispatcher())
        activityTestRule.launchActivity(null)
        progressBarGoneIdlingResource =
            ViewVisibilityIdlingResource(
                activityTestRule.activity.findViewById(R.id.progressBar),
                View.GONE
            )

        TripListActivityRobot()
            .waitForCondition(progressBarGoneIdlingResource)
            .assertTotalCounterDisplayed("16 flights found")
            .assertDataDisplayed()
            .assertTripAtPosition(0, "£329.00")
    }

    @Test
    fun shouldNotLoadFlightList() {
        mockWebServer.setDispatcher(ErrorDispatcher())
        activityTestRule.launchActivity(null)
        progressBarGoneIdlingResource =
            ViewVisibilityIdlingResource(
                activityTestRule.activity.findViewById(R.id.progressBar),
                View.GONE
            )

        TripListActivityRobot()
            .waitForCondition(progressBarGoneIdlingResource)
            .assertErrorButtonDisplayed()
    }

    @Test
    fun shouldBeLoadedReloadButtonClick() {
        mockWebServer.setDispatcher(ErrorDispatcher())
        activityTestRule.launchActivity(null)
        progressBarGoneIdlingResource =
            ViewVisibilityIdlingResource(
                activityTestRule.activity.findViewById(R.id.progressBar),
                View.GONE
            )

        TripListActivityRobot()
            .waitForCondition(progressBarGoneIdlingResource)
            .assertErrorButtonDisplayed()

        mockWebServer.setDispatcher(SuccessDispatcher())

        TripListActivityRobot().clickErrorButton()

        progressBarGoneIdlingResource =
            ViewVisibilityIdlingResource(
                activityTestRule.activity.findViewById(R.id.progressBar),
                View.GONE
            )

        TripListActivityRobot()
            .waitForCondition(progressBarGoneIdlingResource)
            .assertTotalCounterDisplayed("16 flights found")
            .assertDataDisplayed()
            .assertTripAtPosition(0, "£329.00")
    }
}