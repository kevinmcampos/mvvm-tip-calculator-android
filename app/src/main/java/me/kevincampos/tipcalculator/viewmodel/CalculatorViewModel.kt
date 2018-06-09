package me.kevincampos.tipcalculator.viewmodel

import me.kevincampos.tipcalculator.model.Calculator
import me.kevincampos.tipcalculator.model.RestaurantCalculator
import me.kevincampos.tipcalculator.model.TipCalculation

class CalculatorViewModel(
        private val calculator: Calculator = RestaurantCalculator()
) {

    var inputCheckAmount = ""

    var inputTipPercentage = ""

    var tipCalculation = TipCalculation()

    fun calculateTip() {
        val checkAmount = inputCheckAmount.toDoubleOrNull()
        val tipPercentage = inputTipPercentage.toIntOrNull()

        if (checkAmount != null && tipPercentage != null) {
            tipCalculation = calculator.calculateTip(checkAmount, tipPercentage)
        }
    }

}