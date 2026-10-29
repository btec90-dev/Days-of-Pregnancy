package com.example.ui.prenatal

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class WeightEntry(
    val week: Int,
    val weightKg: Float,
    val dateText: String
)

@Composable
fun MaternalWeightTracker(currentWeek: Int, isFrench: Boolean) {
    var baselineWeightText by remember { mutableStateOf("60.0") }
    var currentWeightInput by remember { mutableStateOf("63.2") }
    var entries by remember {
        mutableStateOf(
            listOf(
                WeightEntry(12, 63.2f, "22 Jan 2027"),
                WeightEntry(8, 61.5f, "25 Déc 2026"),
                WeightEntry(4, 60.5f, "27 Nov 2026"),
                WeightEntry(1, 60.0f, "05 Nov 2026 (FET)")
            )
        )
    }

    val baseline = baselineWeightText.toFloatOrNull() ?: 60.0f
    val latest = entries.firstOrNull()?.weightKg ?: baseline
    val totalGain = latest - baseline

    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFAF5ED)),
        border = BorderStroke(1.dp, Color(0xFFD97706).copy(alpha = 0.35f)),
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.MonitorWeight,
                        contentDescription = null,
                        tint = Color(0xFF92400E),
                        modifier = Modifier.size(22.dp)
                    )
                    Column {
                        Text(
                            text = if (isFrench) "Courbe de Poids & Croissance Maternelle" else "Weight Gain & Maternal Growth Tracker",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2E1905)
                        )
                        Text(
                            text = if (isFrench) "Recommandation : +11.5 à 16 kg au total" else "Target guideline: +11.5 to 16 kg overall",
                            fontSize = 10.sp,
                            color = Color(0xFF78350F)
                        )
                    }
                }

                Surface(
                    color = Color(0xFF16A34A).copy(alpha = 0.15f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "+${String.format("%.1f", totalGain)} kg",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Black,
                        color = Color(0xFF15803D),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            // Input Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = currentWeightInput,
                    onValueChange = { currentWeightInput = it },
                    label = { Text(if (isFrench) "Poids S$currentWeek (kg)" else "Weight W$currentWeek (kg)", fontSize = 10.sp) },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF92400E),
                        unfocusedBorderColor = Color(0xFFBFA278),
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )

                Button(
                    onClick = {
                        val weight = currentWeightInput.toFloatOrNull()
                        if (weight != null) {
                            entries = listOf(
                                WeightEntry(currentWeek, weight, "Aujourd'hui")
                            ) + entries.filter { it.week != currentWeek }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF92400E))
                ) {
                    Text(if (isFrench) "Enregistrer" else "Log Weight", fontSize = 11.sp)
                }
            }

            // Summary progress bar
            val progressFraction = (totalGain / 14f).coerceIn(0f, 1f)
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = if (isFrench) "Prise de poids globale" else "Total gestational weight gain",
                        fontSize = 10.sp,
                        color = Color(0xFF78350F)
                    )
                    Text(
                        text = "${String.format("%.1f", totalGain)} kg / 14 kg",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF92400E)
                    )
                }
                LinearProgressIndicator(
                    progress = { progressFraction },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp),
                    color = Color(0xFF92400E),
                    trackColor = Color(0xFF92400E).copy(alpha = 0.15f)
                )
            }

            // History Log
            Divider(color = Color(0xFFBFA278).copy(alpha = 0.3f))
            Text(
                text = if (isFrench) "Historique des pesées" else "Weight Log History",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF78350F)
            )

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                entries.take(4).forEach { entry ->
                    Surface(
                        color = Color.White,
                        shape = RoundedCornerShape(6.dp),
                        border = BorderStroke(0.5.dp, Color(0xFFBFA278).copy(alpha = 0.5f))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp, vertical = 6.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Semaine ${entry.week} (${entry.dateText})",
                                fontSize = 11.sp,
                                color = Color(0xFF2E1905)
                            )
                            Text(
                                text = "${entry.weightKg} kg (${if (entry.weightKg >= baseline) "+" else ""}${String.format("%.1f", entry.weightKg - baseline)} kg)",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF78350F)
                            )
                        }
                    }
                }
            }
        }
    }
}
