package me.kevincampos.tipcalculator.model

import android.arch.core.executor.testing.InstantTaskExecutorRule
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class TipCalculationRepositoryTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var repository: TipCalculationRepository

    @Before
    fun setup() {
        repository = TipCalculationRepository()
    }

    @Test
    fun testSaveTip() {
        val tipCalculation = TipCalculation(
                locationName = "Pankake Paradise",
                checkAmount = 100.0,
                tipPercent = 25,
                tipAmount = 25.0,
                grandTotal = 125.0
        )

        repository.saveTipCalculation(tipCalculation)

        assertEquals(tipCalculation, repository.loadTipCalculationByName(tipCalculation.locationName))
    }

    @Test
    fun testLoadSavedTipsCalculation() {
        val tipCalculation1 = TipCalculation(
                locationName = "Pankake Paradise",
                checkAmount = 100.0,
                tipPercent = 25,
                tipAmount = 25.0,
                grandTotal = 125.0
        )
        val tipCalculation2 = TipCalculation(
                locationName = "Veggie Sensation",
                checkAmount = 100.0,
                tipPercent = 25,
                tipAmount = 25.0,
                grandTotal = 125.0
        )

        repository.saveTipCalculation(tipCalculation1)
        repository.saveTipCalculation(tipCalculation2)

        repository.loadSavedTipCalculations().observeForever { tipCalculations ->
            assertEquals(tipCalculations?.size, 2)
        }
    }

}