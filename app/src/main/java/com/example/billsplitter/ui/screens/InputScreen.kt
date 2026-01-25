package com.example.billsplitter.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InputScreen(
    billAmount: String,
    numberOfPeople: String,
    tipPercentage: Double,
    onBillAmountChange: (String) -> Unit,
    onNumberOfPeopleChange: (String) -> Unit,
    onTipPercentageChange: (Double) -> Unit,
    onCalculateClick: () -> Unit,
    isCalculateEnabled: Boolean,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            TextButton(onClick = onBackClick) {
                Text("Назад")
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = billAmount,
            onValueChange = onBillAmountChange,
            label = { Text("Сумма счета") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
        )

        OutlinedTextField(
            value = numberOfPeople,
            onValueChange = onNumberOfPeopleChange,
            label = { Text("Количество человек") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
        )

        Text(
            text = "Выберите чаевые",
            fontSize = 18.sp,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf(5, 10, 15, 20).forEach { percent ->
                FilterChip(
                    selected = tipPercentage.toInt() == percent,
                    onClick = { onTipPercentageChange(percent.toDouble()) },
                    label = { Text("$percent%") },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onCalculateClick,
            enabled = isCalculateEnabled,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text("Рассчитать", fontSize = 18.sp)
        }
    }
}
