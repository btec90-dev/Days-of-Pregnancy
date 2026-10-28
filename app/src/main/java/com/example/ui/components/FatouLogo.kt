package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FatouLogo(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(72.dp)
            .clip(CircleShape)
            .background(
                Brush.radialGradient(
                    colors = listOf(Color(0xFF8B5F30), Color(0xFF382312))
                )
            )
            .border(2.5.dp, Color(0xFFD97706), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(56.dp)) {
            val center = Offset(size.width / 2f, size.height / 2f)

            // Brass outer ring
            drawCircle(
                color = Color(0xFFFDE68A),
                radius = size.minDimension / 2f - 2f,
                style = Stroke(width = 3f)
            )

            // Glowing embryonic spark / heart core
            drawCircle(
                color = Color(0xFFFDA4AF),
                radius = 14f,
                center = center
            )

            // Crown crest rays
            for (i in 0..7) {
                val angle = i * (Math.PI / 4)
                val startX = center.x + (18f * kotlin.math.cos(angle)).toFloat()
                val startY = center.y + (18f * kotlin.math.sin(angle)).toFloat()
                val endX = center.x + (26f * kotlin.math.cos(angle)).toFloat()
                val endY = center.y + (26f * kotlin.math.sin(angle)).toFloat()
                drawLine(
                    color = Color(0xFFFCD34D),
                    start = Offset(startX, startY),
                    end = Offset(endX, endY),
                    strokeWidth = 2.5f,
                    cap = StrokeCap.Round
                )
            }
        }

        Text(
            text = "F",
            fontSize = 26.sp,
            fontWeight = FontWeight.Black,
            color = Color(0xFFFEF3C7)
        )
    }
}
