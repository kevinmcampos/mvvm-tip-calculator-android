package me.kevincampos.tipcalculator.view

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.replaceText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import me.kevincampos.tipcalculator.R
import org.junit.Rule
import org.junit.Test

class TipCalculatorActivityTest {

    @get:Rule
    var activityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testCalculatedTip() {
        // input values and calculate tip
        enter(checkAmount = 15.99, tipPercent = 15)
        calculateTip()
        assertOutputs(locationName = "", checkAmount = "$15.99", tipAmount = "$2.40", total = "$18.39")

        // save tip
        saveTip("Veggie Max")
        assertOutputs(locationName = "Veggie Max", checkAmount = "$15.99", tipAmount = "$2.40", total = "$18.39")

        // clear outputs
        clearOutPuts()
        assertOutputs(locationName = "", checkAmount = "$0.00", tipAmount = "$0.00", total = "$0.00")

        // load tip
        loadTip("Veggie Max")
        assertOutputs(locationName = "Veggie Max", checkAmount = "$15.99", tipAmount = "$2.40", total = "$18.39")
    }

    private fun enter(checkAmount: Double, tipPercent: Int) {
        onView(withId(R.id.input_check_amount)).perform(replaceText(checkAmount.toString()))
        onView(withId(R.id.input_tip_percentage)).perform(replaceText(tipPercent.toString()))
    }

    private fun calculateTip() {
        onView(withId(R.id.fab)).perform(click())
    }

    private fun assertOutputs(locationName: String, checkAmount: String, tipAmount: String, total: String) {
        onView(withId(R.id.bill_amount)).check(matches(withText(checkAmount)))
        onView(withId(R.id.tip_amount)).check(matches(withText(tipAmount)))
        onView(withId(R.id.grant_total)).check(matches(withText(total)))
        onView(withId(R.id.location_name)).check(matches(withText(locationName)))
    }

    private fun clearOutPuts() {
        enter(checkAmount = 0.0, tipPercent = 0)
        calculateTip()
    }

    private fun saveTip(locationName: String) {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getContext())

        onView(withText(R.string.action_save)).perform(click())

        onView(withHint(R.string.input_location_name_hint)).perform(replaceText(locationName))

        onView(withText(R.string.action_save)).perform(click())
    }

    private fun loadTip(locationName: String) {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getContext())

        onView(withText(R.string.action_load)).perform(click())

        onView(withText(locationName)).perform(click())
    }

}