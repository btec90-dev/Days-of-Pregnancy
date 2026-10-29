package com.example.ui.prenatal

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

data class KickSession(
    val timestamp: Long,
    val kickCount: Int,
    val durationSeconds: Long,
    val week: Int
)

@Composable
fun KickCounterSection(currentWeek: Int, isFrench: Boolean) {
    var kickCount by remember { mutableStateOf(0) }
    var isTimerRunning by remember { mutableStateOf(false) }
    var elapsedSeconds by remember { mutableStateOf(0L) }
    var sessionHistory by remember {
        mutableStateOf(
            listOf(
                KickSession(System.currentTimeMillis() - 86400000L, 10, 840, 26),
                KickSession(System.currentTimeMillis() - 172800000L, 10, 1120, 25)
            )
        )
    }

    LaunchedEffect(isTimerRunning) {
        while (isTimerRunning) {
            delay(1000L)
            elapsedSeconds++
        }
    }

    val minutes = elapsedSeconds / 60
    val seconds = elapsedSeconds % 60
    val timerText = String.format("%02d:%02d", minutes, seconds)
    val isCompleted = kickCount >= 10

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
            verticalArrangement = Arrangement.spacedBy(12.dp)
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
                        imageVector = Icons.Default.DirectionsRun,
                        contentDescription = null,
                        tint = Color(0xFF92400E),
                        modifier = Modifier.size(22.dp)
                    )
                    Column {
                        Text(
                            text = if (isFrench) "Compteur de Mouvements Fœtaux (10 Coups)" else "Fetal Kick Counter (10 Kicks Protocol)",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2E1905)
                        )
                        Text(
                            text = if (isFrench) "Recommandé dès la Semaine 24 (RMA of New York)" else "Recommended from Week 24 onwards (RMA Protocol)",
                            fontSize = 10.sp,
                            color = Color(0xFF78350F)
                        )
                    }
                }

                Surface(
                    color = if (isTimerRunning) Color(0xFFDC2626).copy(alpha = 0.15f) else Color(0xFF92400E).copy(alpha = 0.1f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = timerText,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Black,
                        color = if (isTimerRunning) Color(0xFFB91C1C) else Color(0xFF78350F),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            // Big Kick Button & Progress
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "$kickCount / 10 ${if (isFrench) "coups ressentis" else "kicks counted"}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                    color = if (isCompleted) Color(0xFF16A34A) else Color(0xFF92400E)
                )

                LinearProgressIndicator(
                    progress = { (kickCount / 10f).coerceIn(0f, 1f) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp),
                    color = Color(0xFF16A34A),
                    trackColor = Color(0xFF92400E).copy(alpha = 0.15f)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            if (!isTimerRunning && kickCount == 0) {
                                isTimerRunning = true
                            }
                            if (kickCount < 10) {
                                kickCount++
                                if (kickCount == 10) {
                                    isTimerRunning = false
                                    sessionHistory = listOf(
                                        KickSession(System.currentTimeMillis(), 10, elapsedSeconds, currentWeek)
                                    ) + sessionHistory
                                }
                            }
                        },
                        enabled = !isCompleted,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF92400E),
                            disabledContainerColor = Color(0xFF16A34A)
                        ),
                        modifier = Modifier
                            .size(72.dp)
                            .testTag("record_kick_button"),
                        shape = CircleShape
                    ) {
                        Icon(
                            imageVector = if (isCompleted) Icons.Default.Check else Icons.Default.TouchApp,
                            contentDescription = "Enregistrer un coup",
                            modifier = Modifier.size(32.dp),
                            tint = Color.White
                        )
                    }
                }

                Text(
                    text = if (isCompleted)
                        (if (isFrench) "🎉 10 mouvements enregistrés avec succès !" else "🎉 10 kicks completed successfully!")
                    else
                        (if (isFrench) "Touchez à chaque coup ou vague de bébé" else "Tap each time baby boy moves or kicks"),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (isCompleted) Color(0xFF16A34A) else Color(0xFF593210)
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = {
                            kickCount = 0
                            elapsedSeconds = 0L
                            isTimerRunning = false
                        },
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF78350F))
                    ) {
                        Text(if (isFrench) "Réinitialiser" else "Reset Session", fontSize = 11.sp)
                    }

                    if (!isCompleted) {
                        Button(
                            onClick = { isTimerRunning = !isTimerRunning },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isTimerRunning) Color(0xFFB45309) else Color(0xFF16A34A)
                            )
                        ) {
                            Text(
                                if (isTimerRunning)
                                    (if (isFrench) "Pause Chrono" else "Pause Timer")
                                else
                                    (if (isFrench) "Démarrer Chrono" else "Start Timer"),
                                fontSize = 11.sp
                            )
                        }
                    }
                }
            }

            // Session History Log
            if (sessionHistory.isNotEmpty()) {
                Divider(color = Color(0xFFBFA278).copy(alpha = 0.3f))
                Text(
                    text = if (isFrench) "Historique des séances de mouvements" else "Fetal Movement Session Log",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF78350F)
                )

                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    sessionHistory.take(3).forEach { session ->
                        val sessionMin = session.durationSeconds / 60
                        val sessionSec = session.durationSeconds % 60
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
                                    text = "Semaine ${session.week} • 10 coups",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFF2E1905)
                                )
                                Text(
                                    text = "⏱️ ${sessionMin}m ${sessionSec}s",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF16A34A)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
