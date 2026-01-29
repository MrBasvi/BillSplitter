package com.example.billsplitter.ui.state

import com.example.billsplitter.model.SplitCalculation

data class SplitterUiState(
    val billAmount: Double? = null,
    val numberOfPeople: Int? = null,
    val tipPercentage: Double = 15.0,
    val calculations: List<SplitCalculation> = emptyList(),
    val currentCalculation: SplitCalculation? = null
) {
    val isInputValid: Boolean
        get() = billAmount != null && billAmount > 0 && numberOfPeople != null && numberOfPeople > 0
}
