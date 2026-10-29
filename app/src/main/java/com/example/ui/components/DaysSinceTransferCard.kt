package com.example.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.viewmodel.PregnancyUiState

@Composable
fun DaysSinceTransferCard(
    uiState: PregnancyUiState,
    modifier: Modifier = Modifier
) {
    val animatedProgress by animateFloatAsState(
        targetValue = uiState.dptProgressFraction,
        animationSpec = tween(durationMillis = 1000),
        label = "dpt_progress"
    )

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .testTag("days_since_transfer_card"),
        color = Color(0xFFFAF4E8),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.5.dp, Color(0xFFBFA278)),
        shadowElevation = 6.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Header: Protocol info
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .background(Color(0xFF2563EB), CircleShape)
                    )
                    Text(
                        text = "SUIVI PROTOCOLE FET (BLASTOCYSTE J5)",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF92400E),
                        letterSpacing = 0.5.sp
                    )
                }
                Surface(
                    color = Color(0xFF1D4ED8).copy(alpha = 0.12f),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(0.5.dp, Color(0xFF2563EB))
                ) {
                    Text(
                        text = "Garçon 46, XY",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E40AF),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }

            // Big Counter & Circular Progress
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(Color(0xFFFEF3C7), Color(0xFFFDE68A))
                        ),
                        RoundedCornerShape(14.dp)
                    )
                    .border(1.dp, Color(0xFFD97706).copy(alpha = 0.3f), RoundedCornerShape(14.dp))
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "COMPTEUR POST-TRANSFERT",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF78350F)
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = "J + ${uiState.daysSinceTransfer}",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFF2E1905)
                        )
                        Text(
                            text = "DPT",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF92400E),
                            modifier = Modifier.padding(bottom = 6.dp)
                        )
                    }
                    Text(
                        text = "Blastocyste J5 euploïde • ${uiState.currentWeek} SA + ${uiState.currentDaysInWeek} j",
                        fontSize = 11.sp,
                        color = Color(0xFF593210),
                        fontWeight = FontWeight.Medium
                    )
                }

                // Circular Progress Indicator
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(70.dp)
                        .padding(4.dp)
                ) {
                    CircularProgressIndicator(
                        progress = { 1f },
                        modifier = Modifier.fillMaxSize(),
                        color = Color(0xFFD97706).copy(alpha = 0.2f),
                        strokeWidth = 6.dp
                    )
                    CircularProgressIndicator(
                        progress = { animatedProgress },
                        modifier = Modifier.fillMaxSize(),
                        color = Color(0xFF92400E),
                        strokeWidth = 6.dp,
                        strokeCap = StrokeCap.Round
                    )
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "${(uiState.dptProgressFraction * 100).toInt()}%",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFF2E1905)
                        )
                        Text(
                            text = "Terme",
                            fontSize = 8.sp,
                            color = Color(0xFF78350F)
                        )
                    }
                }
            }

            // Linear Progress Visual with Target Days
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Progression DPT (Transfert → Terme)",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF78350F)
                    )
                    Text(
                        text = "${uiState.daysSinceTransfer} / ${uiState.totalTransferDays} jours",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF92400E)
                    )
                }

                LinearProgressIndicator(
                    progress = { animatedProgress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = Color(0xFF92400E),
                    trackColor = Color(0xFFD97706).copy(alpha = 0.2f),
                    strokeCap = StrokeCap.Round
                )
            }

            // Milestone Path (Key J5 FET Milestones)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFAF5ED), RoundedCornerShape(12.dp))
                    .border(1.dp, Color(0xFFD97706).copy(alpha = 0.25f), RoundedCornerShape(12.dp))
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "Étapes Clés du Blastocyste J5 Euploïde (RMA NY) :",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF92400E)
                )

                DptMilestoneRow("J0 (05 Nov)", "Transfert embryon 46, XY", isPassed = uiState.daysSinceTransfer >= 0, isCurrent = uiState.daysSinceTransfer in 0..3)
                DptMilestoneRow("J4-J5", "Nidation & sécrétion Bêta-hCG", isPassed = uiState.daysSinceTransfer >= 5, isCurrent = uiState.daysSinceTransfer in 4..8)
                DptMilestoneRow("J9-J14", "Tests Bêta-hCG (148 → 1 490 UI/L)", isPassed = uiState.daysSinceTransfer >= 14, isCurrent = uiState.daysSinceTransfer in 9..20)
                DptMilestoneRow("J35 (~S7)", "Battement cardiaque embryonnaire", isPassed = uiState.daysSinceTransfer >= 35, isCurrent = uiState.daysSinceTransfer in 21..50)
                DptMilestoneRow("J70 (~S12)", "Clarté nucale T1 & DPNI de sécurité", isPassed = uiState.daysSinceTransfer >= 70, isCurrent = uiState.daysSinceTransfer in 51..90)
                DptMilestoneRow("J140 (~S22)", "Échographie morphologique T2 (mi-parcours)", isPassed = uiState.daysSinceTransfer >= 140, isCurrent = uiState.daysSinceTransfer in 91..180)
                DptMilestoneRow("J266 (~S40)", "Date Prévue d'Accouchement (22/07/2027)", isPassed = uiState.daysSinceTransfer >= 266, isCurrent = uiState.daysSinceTransfer >= 266)
            }
        }
    }
}

@Composable
private fun DptMilestoneRow(
    dpt: String,
    title: String,
    isPassed: Boolean,
    isCurrent: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(
                        when {
                            isCurrent -> Color(0xFFEAB308)
                            isPassed -> Color(0xFF16A34A)
                            else -> Color(0xFFD1D5DB)
                        },
                        CircleShape
                    )
            )
            Text(
                text = dpt,
                fontSize = 10.sp,
                fontWeight = if (isCurrent) FontWeight.Black else FontWeight.Bold,
                color = if (isCurrent) Color(0xFF92400E) else if (isPassed) Color(0xFF2E1905) else Color(0xFF6B7280),
                modifier = Modifier.width(68.dp)
            )
            Text(
                text = title,
                fontSize = 10.sp,
                fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
                color = if (isCurrent) Color(0xFF92400E) else if (isPassed) Color(0xFF3B2814) else Color(0xFF6B7280)
            )
        }

        if (isCurrent) {
            Surface(
                color = Color(0xFFFEF3C7),
                shape = RoundedCornerShape(4.dp),
                border = BorderStroke(0.5.dp, Color(0xFFD97706))
            ) {
                Text(
                    text = "En cours",
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF92400E),
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                )
            }
        } else if (isPassed) {
            Text(
                text = "✓",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF16A34A)
            )
        }
    }
}
