package me.kevincampos.tipcalculator.model

import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RestaurantCalculatorTest {

    lateinit var calculator: RestaurantCalculator

    @Before
    fun setup() {
        calculator = RestaurantCalculator()
    }

    @Test
    fun testCalculateTip() {
        val baseTipCalculation = TipCalculation(checkAmount = 10.00)

        val testValues = listOf(
                baseTipCalculation.copy(tipPercent = 25, tipAmount = 2.5, grandTotal = 12.50),
                baseTipCalculation.copy(tipPercent = 15, tipAmount = 1.5, grandTotal = 11.50),
                baseTipCalculation.copy(tipPercent = 18, tipAmount = 1.8, grandTotal = 11.80)
        )

        testValues.forEach({
            assertEquals(it,
                    calculator.calculateTip(it.checkAmount, it.tipPercent))
        })
    }

}