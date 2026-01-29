package com.example.billsplitter.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.billsplitter.model.SplitCalculation
import com.example.billsplitter.ui.state.SplitterUiState

class SplitterViewModel : ViewModel() {
    var uiState by mutableStateOf(SplitterUiState())
        private set

    fun updateBillAmount(amount: String) {
        uiState = uiState.copy(billAmount = amount.toDoubleOrNull())
    }

    fun updateNumberOfPeople(people: String) {
        uiState = uiState.copy(numberOfPeople = people.toIntOrNull())
    }

    fun updateTipPercentage(percentage: Double) {
        uiState = uiState.copy(tipPercentage = percentage)
    }

    fun calculateSplit(): String? {
        if (!uiState.isInputValid) return null
        
        val calculation = createCalculation()
        updateHistory(calculation)
        
        return calculation.id
    }

    private fun createCalculation(): SplitCalculation {
        val billAmount = uiState.billAmount!!
        val numberOfPeople = uiState.numberOfPeople!!
        val tipPercentage = uiState.tipPercentage
        
        return SplitCalculation(
            billAmount = billAmount,
            numberOfPeople = numberOfPeople,
            tipPercentage = tipPercentage
        )
    }

    private fun updateHistory(calculation: SplitCalculation) {
        val updatedCalculations = (listOf(calculation) + uiState.calculations).take(5)
        
        uiState = uiState.copy(
            currentCalculation = calculation,
            calculations = updatedCalculations
        )
    }

    fun getCalculationById(id: String): SplitCalculation? {
        return uiState.calculations.find { it.id == id }
            ?: uiState.currentCalculation?.takeIf { it.id == id }
    }

    fun resetCalculation() {
        uiState = uiState.copy(
            billAmount = null,
            numberOfPeople = null,
            tipPercentage = 15.0,
            currentCalculation = null
        )
    }
}
