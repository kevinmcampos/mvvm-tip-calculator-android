package me.kevincampos.tipcalculator.viewmodel

import android.app.Application
import android.databinding.BaseObservable
import me.kevincampos.tipcalculator.R
import me.kevincampos.tipcalculator.model.Calculator
import me.kevincampos.tipcalculator.model.RestaurantCalculator
import me.kevincampos.tipcalculator.model.TipCalculation

class CalculatorViewModel(
        private val application: Application,
        private val calculator: Calculator = RestaurantCalculator()
) : BaseObservable() {

    var inputCheckAmount = ""
    var inputTipPercentage = ""

    var outputCheckAmount = ""
    var outputTipAmount = ""
    var outputTotalAmount = ""

    init {
        updateOutputs(TipCalculation())
    }

    private fun updateOutputs(tipCalculation: TipCalculation) {
        outputCheckAmount = application.getString(R.string.dollar_format, tipCalculation.checkAmount)
        outputTipAmount = application.getString(R.string.dollar_format, tipCalculation.tipAmount)
        outputTotalAmount = application.getString(R.string.dollar_format, tipCalculation.grandTotal)
    }

    fun calculateTip() {
        val checkAmount = inputCheckAmount.toDoubleOrNull()
        val tipPercentage = inputTipPercentage.toIntOrNull()

        if (checkAmount != null && tipPercentage != null) {
            val tipCalculation = calculator.calculateTip(checkAmount, tipPercentage)
            updateOutputs(tipCalculation)
            notifyChange()
        }
    }

}