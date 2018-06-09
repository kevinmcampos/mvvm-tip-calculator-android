package me.kevincampos.tipcalculator.viewmodel

import android.app.Application
import junit.framework.Assert.assertEquals
import me.kevincampos.tipcalculator.R
import me.kevincampos.tipcalculator.model.Calculator
import me.kevincampos.tipcalculator.model.TipCalculation
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class CalculatorViewModelTest {

    private lateinit var calculatorViewModel: CalculatorViewModel

    @Mock
    private lateinit var mockCalculator: Calculator

    @Mock
    private lateinit var application: Application

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        stubResources(0.0, "$0.00")
        calculatorViewModel = CalculatorViewModel(application, mockCalculator)
    }

    private fun stubResources(given: Double, returnStub: String) {
        `when`(application.getString(R.string.dollar_format, given)).thenReturn(returnStub)
    }

    @Test
    fun testCalculateTip() {
        calculatorViewModel.inputCheckAmount = "10.00"
        calculatorViewModel.inputTipPercentage = "15"

        val stub = TipCalculation(checkAmount = 10.00, tipAmount = 1.5, grandTotal = 11.5)
        `when`(mockCalculator.calculateTip(10.00, 15)).thenReturn(stub)

        stubResources(10.00, "$10.00")
        stubResources(1.50, "$1.50")
        stubResources(11.50, "$11.50")

        calculatorViewModel.calculateTip()

        assertEquals(calculatorViewModel.outputCheckAmount, "$10.00")
        assertEquals(calculatorViewModel.outputTipAmount, "$1.50")
        assertEquals(calculatorViewModel.outputTotalAmount, "$11.50")
    }

    @Test
    fun testCalculateTipBadTipPercent() {
        calculatorViewModel.inputCheckAmount = "10.00"
        calculatorViewModel.inputTipPercentage = "" // Bad tip percent

        calculatorViewModel.calculateTip()

        verify(mockCalculator, never()).calculateTip(anyDouble(), anyInt())
    }

    @Test
    fun testCalculateTipBadCheckAmount() {
        calculatorViewModel.inputCheckAmount = "" // Bad check amount
        calculatorViewModel.inputTipPercentage = "15"

        calculatorViewModel.calculateTip()

        verify(mockCalculator, never()).calculateTip(anyDouble(), anyInt())
    }
}