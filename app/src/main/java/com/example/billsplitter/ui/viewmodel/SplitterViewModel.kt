package com.example.billsplitter.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.billsplitter.model.SplitCalculation
import com.example.billsplitter.ui.state.SplitterUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SplitterViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SplitterUiState())
    val uiState: StateFlow<SplitterUiState> = _uiState.asStateFlow()

    fun updateBillAmount(amount: String) {
        _uiState.update { it.copy(billAmount = amount) }
    }

    fun updateNumberOfPeople(people: String) {
        _uiState.update { it.copy(numberOfPeople = people) }
    }

    fun updateTipPercentage(percentage: Double) {
        _uiState.update { it.copy(tipPercentage = percentage) }
    }

    fun calculateSplit(): String? {
        val state = _uiState.value
        if (!state.isInputValid) return null

        val billAmount = state.billAmount.toDouble()
        val numberOfPeople = state.numberOfPeople.toInt()
        val editingId = state.editingCalculationId
        
        val calculation = if (editingId != null) {
            val existingCalc = state.calculations.find { it.id == editingId }
            existingCalc?.copy(
                billAmount = billAmount,
                numberOfPeople = numberOfPeople,
                tipPercentage = state.tipPercentage
            ) ?: SplitCalculation(
                billAmount = billAmount,
                numberOfPeople = numberOfPeople,
                tipPercentage = state.tipPercentage
            )
        } else {
            SplitCalculation(
                billAmount = billAmount,
                numberOfPeople = numberOfPeople,
                tipPercentage = state.tipPercentage
            )
        }

        _uiState.update { currentState ->
            val updatedCalculations = if (editingId != null) {
                currentState.calculations.map { if (it.id == editingId) calculation else it }
            } else {
                (listOf(calculation) + currentState.calculations).take(5)
            }
            
            currentState.copy(
                currentCalculation = calculation,
                calculations = updatedCalculations,
                editingCalculationId = null
            )
        }

        return calculation.id
    }

    fun getCalculationById(id: String): SplitCalculation? {
        return _uiState.value.calculations.find { it.id == id }
            ?: _uiState.value.currentCalculation?.takeIf { it.id == id }
    }

    fun resetCalculation() {
        _uiState.update {
            it.copy(
                billAmount = "",
                numberOfPeople = "",
                tipPercentage = 15.0,
                currentCalculation = null,
                editingCalculationId = null
            )
        }
    }
    
    fun startEditingCalculation(calculationId: String) {
        val calculation = getCalculationById(calculationId)
        if (calculation != null) {
            _uiState.update {
                it.copy(
                    billAmount = calculation.billAmount.toString(),
                    numberOfPeople = calculation.numberOfPeople.toString(),
                    tipPercentage = calculation.tipPercentage.toDouble(),
                    editingCalculationId = calculationId
                )
            }
        }
    }
}
