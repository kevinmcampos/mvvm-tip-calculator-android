package me.kevincampos.tipcalculator.model

import android.arch.lifecycle.LiveData
import java.math.RoundingMode

interface Calculator {

    fun calculateTip(checkAmount: Double, tipPercent: Int): TipCalculation

    fun saveTipCalculation(tipCalculation: TipCalculation)

    fun loadTipCalculationByName(locationName: String)

    fun loadSavedTipCalculations(): LiveData<List<TipCalculation>>

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

    override fun saveTipCalculation(tipCalculation: TipCalculation) {
        repository.saveTipCalculation(tipCalculation)
    }

    override fun loadTipCalculationByName(locationName: String) {
        repository.loadTipCalculationByName(locationName)
    }

    override fun loadSavedTipCalculations(): LiveData<List<TipCalculation>> {
        return repository.loadSavedTipCalculations()
    }

}