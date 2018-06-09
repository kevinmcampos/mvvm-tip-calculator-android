package me.kevincampos.tipcalculator.model

import android.arch.lifecycle.LiveData
import java.math.RoundingMode

interface Calculator {
    fun calculateTip(checkAmount: Double, tipPercent: Int): TipCalculation
}

class RestaurantCalculator(private val repository: TipCalculationRepository = TipCalculationRepository()) : Calculator {

    override fun calculateTip(checkAmount: Double, tipPercent: Int): TipCalculation {
        val tipAmount = (checkAmount * (tipPercent.toDouble() / 100.0))
                .toBigDecimal()
                .setScale(2, RoundingMode.HALF_UP)
                .toDouble()

        val grandTotal = checkAmount + tipAmount

        return TipCalculation(
                checkAmount = checkAmount,
                tipPercent = tipPercent,
                tipAmount = tipAmount,
                grandTotal = grandTotal
        )
    }

    fun saveTipCalculation(tipCalculation: TipCalculation) {
        repository.saveTipCalculation(tipCalculation)
    }

    fun loadTipCalculationByName(locationName: String) {
        repository.loadTipCalculationByName(locationName)
    }

    fun loadSavedTipCalculations(): LiveData<List<TipCalculation>> {
        return repository.loadSavedTipCalculations()
    }

}