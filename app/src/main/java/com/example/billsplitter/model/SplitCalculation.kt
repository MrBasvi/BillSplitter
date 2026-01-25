package com.example.billsplitter.model

import java.util.UUID

data class SplitCalculation(
    val id: String = UUID.randomUUID().toString(),
    val billAmount: Double,
    val numberOfPeople: Int,
    val tipPercentage: Double = 15.0,
    val timestamp: Long = System.currentTimeMillis()
) {
    val tipAmount: Double
        get() = billAmount * (tipPercentage / 100.0)
    
    val totalWithTip: Double
        get() = billAmount + tipAmount
    
    val perPerson: Double
        get() = if (numberOfPeople > 0) totalWithTip / numberOfPeople else 0.0
}
