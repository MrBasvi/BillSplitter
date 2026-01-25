package com.example.billsplitter.ui.state

import com.example.billsplitter.model.SplitCalculation

data class SplitterUiState(
    val billAmount: String = "",
    val numberOfPeople: String = "",
    val tipPercentage: Double = 15.0,
    val calculations: List<SplitCalculation> = emptyList(),
    val currentCalculation: SplitCalculation? = null,
    val editingCalculationId: String? = null
) {
    val isInputValid: Boolean
        get() {
            val amount = billAmount.toDoubleOrNull()
            val people = numberOfPeople.toIntOrNull()
            return amount != null && amount > 0 && people != null && people > 0
        }
}
