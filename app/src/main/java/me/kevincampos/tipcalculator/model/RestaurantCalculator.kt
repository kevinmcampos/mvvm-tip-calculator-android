package me.kevincampos.tipcalculator.model

import java.math.RoundingMode

class RestaurantCalculator {

    fun calculateTip(checkAmount: Double, tipPercent: Int): TipCalculation {
        val tipAmount = (checkAmount * (tipPercent.toDouble() / 100.0))
                .toBigDecimal()
                .setScale(2, RoundingMode.HALF_UP)
                .toDouble()

        val grandTotal = checkAmount + tipAmount

        return TipCalculation(
                checkAmount,
                tipPercent,
                tipAmount,
                grandTotal
        )
    }

}