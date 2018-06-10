package me.kevincampos.tipcalculator.viewmodel

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import me.kevincampos.tipcalculator.R
import me.kevincampos.tipcalculator.model.Calculator
import me.kevincampos.tipcalculator.model.RestaurantCalculator
import me.kevincampos.tipcalculator.model.TipCalculation

class CalculatorViewModel @JvmOverloads constructor(
        application: Application,
        private val calculator: Calculator = RestaurantCalculator()
) : ObservableViewModel(application) {

    var lastTipCalculation = TipCalculation()

    var inputCheckAmount = ""
    var inputTipPercentage = ""

    val outputCheckAmount: String get() = getApplication<Application>().getString(R.string.dollar_format, lastTipCalculation.checkAmount)
    val outputTipAmount: String get() = getApplication<Application>().getString(R.string.dollar_format, lastTipCalculation.tipAmount)
    val outputTotalAmount: String get() = getApplication<Application>().getString(R.string.dollar_format, lastTipCalculation.grandTotal)
    val outputLocationName: String get() = lastTipCalculation.locationName

    init {
        updateOutputs(TipCalculation())
    }

    private fun updateOutputs(tipCalculation: TipCalculation) {
        lastTipCalculation = tipCalculation
        notifyChange()
    }

    fun saveCurrentTip(locationName: String) {
        val savedTip: TipCalculation = lastTipCalculation.copy(locationName = locationName)

        calculator.saveTipCalculation(savedTip)

        updateOutputs(savedTip)
    }

    fun calculateTip() {
        val checkAmount = inputCheckAmount.toDoubleOrNull()
        val tipPercentage = inputTipPercentage.toIntOrNull()

        if (checkAmount != null && tipPercentage != null) {
            val tipCalculation = calculator.calculateTip(checkAmount, tipPercentage)
            updateOutputs(tipCalculation)
        }
    }

    fun loadSavedTipCalculationSummaries(): LiveData<List<TipCalculationSummaryItem>> {
        return Transformations.map(calculator.loadSavedTipCalculations(), { tipCalculationObjects ->
            tipCalculationObjects.map {
                TipCalculationSummaryItem(
                    it.locationName,
                    getApplication<Application>().getString(R.string.dollar_format, it.grandTotal)
                )
            }
        })
    }

    fun loadTipCalculation(locationName: String) {
        val tipCalculation = calculator.loadTipCalculationByName(locationName)

        if (tipCalculation != null) {
            inputCheckAmount = tipCalculation.checkAmount.toString()
            inputTipPercentage = tipCalculation.tipPercent.toString()

            updateOutputs(tipCalculation)
        }
    }

}