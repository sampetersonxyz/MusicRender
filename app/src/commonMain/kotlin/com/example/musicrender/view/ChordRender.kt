package com.example.musicrender.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.musicrender.model.GuitarFingering
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun ChordRender(chordName: String, fingering: GuitarFingering) {
    val textMeasurer = rememberTextMeasurer()
    val style = MaterialTheme.typography.headlineSmall.copy(
        fontWeight = FontWeight.Bold
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = chordName,
                color = Color.White,
                fontSize = 32.sp,
                modifier = Modifier.weight(0.3f)
            )

            Canvas(
                modifier = Modifier
                    .weight(0.7f)
                    .height(180.dp)
                    .background(color = Color.Black)
            ) {
                val paddingX = 40f
                val paddingY = 30f
                val renderHeight = size.height - paddingY * 2
                val renderWidth = (size.width - paddingX * 2) * 0.8f
                
                val distanceBetweenStr = renderHeight / 5
                val fretSize = renderWidth / 4

                val startX = paddingX
                val startY = paddingY

                for (i in 0..5) {
                    drawLine(
                        color = Color.White,
                        start = Offset(startX, startY + i * distanceBetweenStr),
                        end = Offset(startX + renderWidth + 20f, startY + i * distanceBetweenStr),
                        strokeWidth = 4f
                    )
                }
                for (i in 0..4) {
                    drawLine(
                        color = Color.White,
                        start = Offset(startX + i * fretSize, startY),
                        end = Offset(startX + i * fretSize, startY + 5 * distanceBetweenStr),
                        strokeWidth = 4f
                    )
                }
                drawLine(
                    color = Color.White,
                    start = Offset(startX + 4 * fretSize + 20f, startY),
                    end = Offset(startX + 4 * fretSize + 20f, startY + 5 * distanceBetweenStr),
                    strokeWidth = 10f
                )

                for (i in 0..5) {
                    val f = fingering.frets[i]
                    val yPos = startY + i * distanceBetweenStr
                    val centerX_open = startX + 4 * fretSize + 35f
                    
                    when (f) {
                        0 -> drawCircle(
                            color = Color.Cyan,
                            center = Offset(centerX_open, yPos),
                            radius = distanceBetweenStr / 6,
                            style = Stroke(width = 4f)
                        )
                        null -> drawXShape(
                            color = Color.Red,
                            center = Offset(centerX_open, yPos),
                            radius = distanceBetweenStr / 4,
                            strokeWidth = 4f
                        )
                        else -> drawCircle(
                            color = Color.Blue,
                            center = Offset(startX + fretSize * (4 - f) + fretSize / 2, yPos),
                            radius = distanceBetweenStr / 2.5f,
                            style = Fill
                        )
                    }
                }
            }
        }
        HorizontalDivider(
            color = Color.White.copy(alpha = 0.5f),
            thickness = 1.dp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

fun DrawScope.drawXShape(center: Offset, radius: Float, color: Color, strokeWidth: Float = 0f) {
    drawLine(
        start = Offset(center.x - radius, center.y - radius),
        end = Offset(center.x + radius, center.y + radius),
        color = color, strokeWidth = strokeWidth
    )
    drawLine(
        start = Offset(center.x + radius, center.y - radius),
        end = Offset(center.x - radius, center.y + radius),
        color = color, strokeWidth = strokeWidth
    )
}
