package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.GoldSecondary
import com.example.ui.theme.ObsidianBackground
import com.example.ui.theme.TextMuted
import com.example.ui.theme.VerifiedGreen

@Composable
fun MathGraphView(
    equation: String,
    points: List<Pair<Float, Float>>,
    roots: List<String> = emptyList(),
    modifier: Modifier = Modifier
) {
    var scaleFactor by remember { mutableFloatStateOf(16f) }
    val drawAnimProgress = remember { Animatable(0f) }
    var animTrigger by remember { mutableIntStateOf(0) }

    LaunchedEffect(equation, points, animTrigger) {
        drawAnimProgress.snapTo(0f)
        drawAnimProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 850, easing = FastOutSlowInEasing)
        )
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(ObsidianBackground)
            .border(1.dp, GoldSecondary.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
            .padding(14.dp)
            .testTag("math_graph_view")
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "📈 2D Function Plot: $equation",
                    color = GoldPrimary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Drawing Loop Animated • Scale: ${scaleFactor.toInt()}x",
                    color = TextMuted,
                    fontSize = 10.sp
                )
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF221A00))
                    .border(1.dp, GoldSecondary.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                    .clickable { animTrigger++ }
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .testTag("reanimate_graph_btn")
            ) {
                Text(
                    text = "▶ Draw",
                    color = GoldPrimary,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Canvas Area
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF07070C))
                .border(1.dp, Color(0xFF1F1F2C), RoundedCornerShape(12.dp))
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val w = size.width
                val h = size.height
                val centerX = w / 2f
                val centerY = h / 2f

                // Grid Lines
                val gridStep = scaleFactor
                var xGrid = centerX % gridStep
                while (xGrid < w) {
                    drawLine(
                        color = Color(0xFF151522),
                        start = Offset(xGrid, 0f),
                        end = Offset(xGrid, h),
                        strokeWidth = 1f
                    )
                    xGrid += gridStep
                }

                var yGrid = centerY % gridStep
                while (yGrid < h) {
                    drawLine(
                        color = Color(0xFF151522),
                        start = Offset(0f, yGrid),
                        end = Offset(w, yGrid),
                        strokeWidth = 1f
                    )
                    yGrid += gridStep
                }

                // X and Y Axes
                drawLine(
                    color = Color(0xFF3B3B4F),
                    start = Offset(0f, centerY),
                    end = Offset(w, centerY),
                    strokeWidth = 2f
                )
                drawLine(
                    color = Color(0xFF3B3B4F),
                    start = Offset(centerX, 0f),
                    end = Offset(centerX, h),
                    strokeWidth = 2f
                )

                // Animated Plot Curve with Progressive Loop
                val animProgress = drawAnimProgress.value
                val visibleCount = (points.size * animProgress).toInt()

                if (points.isNotEmpty()) {
                    val path = Path()
                    var first = true
                    var lastPt: Offset? = null

                    for (i in 0 until visibleCount) {
                        val (xVal, yVal) = points[i]
                        val px = centerX + xVal * scaleFactor
                        val py = centerY - yVal * scaleFactor // Y inverted in screen coords

                        if (px in -50f..(w + 50f) && py in -50f..(h + 50f)) {
                            if (first) {
                                path.moveTo(px, py)
                                first = false
                            } else {
                                path.lineTo(px, py)
                            }
                            lastPt = Offset(px, py)
                        }
                    }

                    drawPath(
                        path = path,
                        color = GoldPrimary,
                        style = Stroke(width = 3.5f)
                    )

                    // Glowing Pen / Tracer Cursor
                    if (animProgress < 1f && lastPt != null) {
                        drawCircle(
                            color = Color(0x66FFD700),
                            radius = 9f,
                            center = lastPt
                        )
                        drawCircle(
                            color = Color(0xFFFFD700),
                            radius = 4.5f,
                            center = lastPt
                        )
                    }
                }

                // Center Origin Point
                drawCircle(
                    color = GoldSecondary,
                    radius = 3.5f,
                    center = Offset(centerX, centerY)
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Zoom Slider & Roots Info
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Scale",
                color = TextMuted,
                fontSize = 11.sp
            )
            Spacer(modifier = Modifier.width(8.dp))
            Slider(
                value = scaleFactor,
                onValueChange = { scaleFactor = it },
                valueRange = 8f..32f,
                modifier = Modifier
                    .weight(1f)
                    .testTag("graph_zoom_slider"),
                colors = SliderDefaults.colors(
                    thumbColor = GoldPrimary,
                    activeTrackColor = GoldSecondary,
                    inactiveTrackColor = Color(0xFF222230)
                )
            )
        }

        if (roots.isNotEmpty()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "X-Intercepts (Roots):",
                    color = VerifiedGreen,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = roots.joinToString(" , "),
                    color = GoldPrimary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
