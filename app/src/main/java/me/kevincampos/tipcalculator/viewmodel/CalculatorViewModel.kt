package me.kevincampos.tipcalculator.viewmodel

import android.app.Application
import me.kevincampos.tipcalculator.R
import me.kevincampos.tipcalculator.model.Calculator
import me.kevincampos.tipcalculator.model.RestaurantCalculator
import me.kevincampos.tipcalculator.model.TipCalculation

class CalculatorViewModel @JvmOverloads constructor(
        application: Application,
        private val calculator: Calculator = RestaurantCalculator()
) : ObservableViewModel(application) {

    var inputCheckAmount = ""
    var inputTipPercentage = ""

    var outputCheckAmount = ""
    var outputTipAmount = ""
    var outputTotalAmount = ""

    init {
        updateOutputs(TipCalculation())
    }

    private fun updateOutputs(tipCalculation: TipCalculation) {
        outputCheckAmount = getApplication<Application>().getString(R.string.dollar_format, tipCalculation.checkAmount)
        outputTipAmount = getApplication<Application>().getString(R.string.dollar_format, tipCalculation.tipAmount)
        outputTotalAmount = getApplication<Application>().getString(R.string.dollar_format, tipCalculation.grandTotal)
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