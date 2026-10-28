package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.PregnancyWeek

@Composable
fun UltrasoundView(
    weekData: PregnancyWeek,
    is3dMode: Boolean,
    onToggleMode: (Boolean) -> Unit,
    isDopplerPlaying: Boolean,
    onToggleDoppler: () -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "fetal_pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.96f,
        targetValue = 1.04f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.7f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFF0D0604))
            .border(2.dp, Color(0xFF78350F), RoundedCornerShape(20.dp))
    ) {
        // Background warm uterine gradient & canvas animation
        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = Offset(size.width / 2f, size.height / 2f)
            val radius = size.minDimension * 0.38f * pulseScale

            // Amniotic glowing halo
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(Color(0xFF9F1239).copy(alpha = glowAlpha), Color(0xFF4C0519).copy(alpha = 0.4f), Color.Transparent),
                    center = center,
                    radius = radius * 1.5f
                ),
                radius = radius * 1.5f,
                center = center
            )

            // Uterine wall ring
            drawCircle(
                color = Color(0xFFD97706).copy(alpha = 0.35f),
                radius = radius,
                center = center,
                style = Stroke(width = 3.dp.toPx())
            )

            // Spiral umbilical cord representation
            val path = Path().apply {
                moveTo(center.x, center.y + radius * 0.4f)
                cubicTo(
                    center.x - 40f, center.y,
                    center.x + 50f, center.y - 40f,
                    center.x - 10f, center.y - radius * 0.3f
                )
            }
            drawPath(
                path = path,
                color = Color(0xFFFCA5A5).copy(alpha = 0.7f),
                style = Stroke(width = 4.dp.toPx(), cap = StrokeCap.Round)
            )
        }

        // Mode switch pills at top
        Row(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 10.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFF1C1917).copy(alpha = 0.85f))
                .border(1.dp, Color(0xFFD97706).copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                .padding(2.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Button(
                onClick = { onToggleMode(true) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (is3dMode) Color(0xFF92400E) else Color.Transparent,
                    contentColor = if (is3dMode) Color.White else Color(0xFFD1D5DB)
                ),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                modifier = Modifier.height(28.dp)
            ) {
                Text("3D Temps Réel", fontSize = 11.dp.value.sp, fontWeight = FontWeight.Bold)
            }
            Button(
                onClick = { onToggleMode(false) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (!is3dMode) Color(0xFF92400E) else Color.Transparent,
                    contentColor = if (!is3dMode) Color.White else Color(0xFFD1D5DB)
                ),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                modifier = Modifier.height(28.dp)
            ) {
                Text("Illustration Womb", fontSize = 11.dp.value.sp, fontWeight = FontWeight.Bold)
            }
        }

        // Top-left Telemetry badge
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(12.dp)
                .background(Color.Black.copy(alpha = 0.6f), RoundedCornerShape(8.dp))
                .border(1.dp, Color(0xFFF59E0B).copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                .padding(8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(Color(0xFFEF4444), CircleShape)
                ) {
                    // Pulsing dot simulation
                }
                Text("Cœur : ${weekData.heartRate} bpm", color = Color(0xFFFCA5A5), fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
            Text("ÂGE : S${weekData.week} (${weekData.week} SA)", color = Color(0xFFFEF3C7).copy(alpha = 0.8f), fontSize = 10.sp)
        }

        // Top-right Live HD badge
        Column(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(12.dp)
                .background(Color.Black.copy(alpha = 0.6f), RoundedCornerShape(8.dp))
                .border(1.dp, Color(0xFFF59E0B).copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                .padding(8.dp),
            horizontalAlignment = Alignment.End
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .background(Color(0xFFDC2626), CircleShape)
                )
                Text("LIVE HD-4D", color = Color(0xFFEF4444), fontSize = 10.sp, fontWeight = FontWeight.Bold)
            }
            Text(weekData.metrics, color = Color(0xFFFDE68A), fontSize = 10.sp)
        }

        // Center visual (3D or Art)
        Box(
            modifier = Modifier.align(Alignment.Center),
            contentAlignment = Alignment.Center
        ) {
            if (is3dMode) {
                // 3D Fetal representation / fruit avatar
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(90.dp)
                            .background(
                                Brush.radialGradient(listOf(Color(0xFFFBCFE8).copy(alpha = 0.5f), Color(0xFF9F1239).copy(alpha = 0.3f))),
                                CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(weekData.icon, fontSize = 42.sp)
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = weekData.fruit,
                        color = Color(0xFFFEF3C7),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            } else {
                // Illustration HD Womb View
                Column(
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .background(Color.Black.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                        .border(1.dp, Color(0xFFFDA4AF).copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                        .padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(weekData.icon, fontSize = 32.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Illustration Médicale In Utero · Semaine ${weekData.week}",
                        color = Color(0xFFFEF3C7),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = weekData.inUtero,
                        color = Color(0xFFFDE68A).copy(alpha = 0.9f),
                        fontSize = 11.sp,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                    )
                }
            }
        }

        // Bottom control bar
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(Color(0xFF090503).copy(alpha = 0.95f))
                .border(BorderStroke(1.dp, Color(0xFF78350F).copy(alpha = 0.5f)))
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = onToggleDoppler,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isDopplerPlaying) Color(0xFF881337) else Color(0xFF4C0519),
                    contentColor = Color(0xFFFECDD3)
                ),
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                modifier = Modifier.height(30.dp)
            ) {
                Text(
                    text = if (isDopplerPlaying) "🔊 Doppler Actif (${weekData.heartRate} bpm)" else "❤️ Doppler Cardiaque",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                text = "Pivoter 360°",
                color = Color(0xFFD97706),
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
