package com.example.billsplitter.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.billsplitter.model.SplitCalculation

@Composable
fun ResultScreen(
    calculation: SplitCalculation,
    onBackToEdit: () -> Unit,
    onNewCalculation: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Результаты",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 24.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Счет:", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("${String.format("%.2f", calculation.billAmount)} ₽", fontSize = 14.sp)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Чаевые (${calculation.tipPercentage.toInt()}%):", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("${String.format("%.2f", calculation.tipAmount)} ₽", fontSize = 14.sp)
                    }
                }
                
                Divider()
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Итого:",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        "${String.format("%.2f", calculation.totalWithTip)} ₽",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                
                Divider()
                
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "С каждого:",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        "${String.format("%.2f", calculation.perPerson)} ₽",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        "(${calculation.numberOfPeople} чел.)",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onBackToEdit,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(bottom = 12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary
            )
        ) {
            Text("Редактировать", fontSize = 16.sp)
        }

        Button(
            onClick = onNewCalculation,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(bottom = 12.dp)
        ) {
            Text("Новый расчет", fontSize = 16.sp)
        }
    }
}
