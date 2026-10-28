package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.PregnancyRepository
import com.example.viewmodel.PregnancyViewModel
import kotlinx.coroutines.delay
import java.util.Calendar

@Composable
fun PregnancyCountdownCard(viewModel: PregnancyViewModel, modifier: Modifier = Modifier) {
    val uiState by viewModel.uiState.collectAsState()

    // Due Date: July 22, 2027 (FET date + 266 days)
    val dpaMillis = remember(uiState.fetDateMillis) {
        uiState.fetDateMillis + (266L * 24L * 60L * 60L * 1000L)
    }

    var remainingDays by remember { mutableStateOf(0L) }
    var remainingHours by remember { mutableStateOf(0L) }
    var remainingMinutes by remember { mutableStateOf(0L) }
    var remainingSeconds by remember { mutableStateOf(0L) }

    // Live ticker for countdown
    LaunchedEffect(dpaMillis) {
        while (true) {
            val now = System.currentTimeMillis()
            val diff = dpaMillis - now
            if (diff > 0) {
                remainingDays = diff / (1000L * 60L * 60L * 24L)
                remainingHours = (diff / (1000L * 60L * 60L) % 24L)
                remainingMinutes = (diff / (1000L * 60L) % 60L)
                remainingSeconds = (diff / 1000L % 60L)
            } else {
                remainingDays = 0
                remainingHours = 0
                remainingMinutes = 0
                remainingSeconds = 0
            }
            delay(1000L)
        }
    }

    val currentWeekData = remember(uiState.currentWeek) {
        PregnancyRepository.weeks.firstOrNull { it.week == uiState.currentWeek } ?: PregnancyRepository.weeks[11]
    }

    val progressFraction = (uiState.currentWeek.toFloat() / 40f).coerceIn(0f, 1f)

    Surface(
        modifier = modifier.fillMaxWidth(),
        color = Color(0xFFFAF4E8),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.5.dp, Color(0xFFBFA278)),
        shadowElevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .background(Color(0xFF16A34A), CircleShape)
                    )
                    Text(
                        text = "TABLEAU DE BORD & DÉCOMPTE DPA",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF92400E)
                    )
                }
                Surface(
                    color = Color(0xFF92400E).copy(alpha = 0.1f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "DPA : 22 Juillet 2027",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF78350F),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }

            // Current Week & Fruit Display
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Semaine Actuelle",
                        fontSize = 11.sp,
                        color = Color(0xFF78350F),
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "Semaine ${uiState.currentWeek} · ${uiState.currentDaysInWeek} jours",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Black,
                        color = Color(0xFF2E1905)
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = currentWeekData.fruit,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF92400E)
                    )
                    Text(
                        text = currentWeekData.metrics,
                        fontSize = 11.sp,
                        color = Color(0xFF78350F)
                    )
                }
            }

            // Progress Bar (40 weeks)
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Progression de la Grossesse", fontSize = 10.sp, color = Color(0xFF78350F))
                    Text("${(progressFraction * 100).toInt()}% (S${uiState.currentWeek}/40)", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF92400E))
                }
                LinearProgressIndicator(
                    progress = { progressFraction },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = Color(0xFF92400E),
                    trackColor = Color(0xFFD97706).copy(alpha = 0.2f),
                )
            }

            Divider(color = Color(0xFFBFA278).copy(alpha = 0.5f))

            // Countdown Timer Grid
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFAF5ED), RoundedCornerShape(12.dp))
                    .border(1.dp, Color(0xFFD97706).copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "Temps restant avant l'arrivée du petit prince",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF78350F)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    CountdownDigitBox(value = remainingDays.toString(), label = "Jours")
                    CountdownDigitBox(value = remainingHours.toString().padStart(2, '0'), label = "Heures")
                    CountdownDigitBox(value = remainingMinutes.toString().padStart(2, '0'), label = "Minutes")
                    CountdownDigitBox(value = remainingSeconds.toString().padStart(2, '0'), label = "Secondes")
                }
            }
        }
    }
}

@Composable
fun CountdownDigitBox(value: String, label: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Color.White, RoundedCornerShape(8.dp))
            .border(1.dp, Color(0xFFD97706).copy(alpha = 0.4f), RoundedCornerShape(8.dp))
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Text(
            text = value,
            fontSize = 18.sp,
            fontWeight = FontWeight.Black,
            color = Color(0xFF92400E)
        )
        Text(
            text = label,
            fontSize = 9.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF78350F)
        )
    }
}
