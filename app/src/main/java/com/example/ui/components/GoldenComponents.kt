package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.SolutionStep
import com.example.ui.theme.GoldDark
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.GoldSecondary
import com.example.ui.theme.GoldTertiary
import com.example.ui.theme.ObsidianBackground
import com.example.ui.theme.ObsidianCardBorder
import com.example.ui.theme.ObsidianSurface
import com.example.ui.theme.ObsidianSurfaceVariant
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.VerifiedGreen

val GoldGradient = Brush.horizontalGradient(
    colors = listOf(GoldPrimary, GoldSecondary, GoldDark)
)

val GoldSubtleBorder = Brush.linearGradient(
    colors = listOf(GoldPrimary.copy(alpha = 0.8f), GoldSecondary.copy(alpha = 0.3f), Color.Transparent)
)

val ObsidianCardGradient = Brush.verticalGradient(
    colors = listOf(ObsidianSurface, ObsidianSurfaceVariant)
)

@Composable
fun AurumHeader(
    onMenuClick: () -> Unit,
    streak: Int = 7,
    coins: Int = 8400,
    onCoinsClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Surface(
        color = ObsidianBackground,
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                brush = Brush.verticalGradient(listOf(Color.Transparent, GoldSecondary.copy(alpha = 0.6f))),
                shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Menu Icon
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(ObsidianSurfaceVariant)
                    .border(1.dp, GoldSecondary.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
                    .clickable { onMenuClick() }
                    .testTag("drawer_toggle_button"),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "☰",
                    color = GoldPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            // Sigma Emblem Logo
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(GoldGradient)
                    .border(1.5.dp, GoldPrimary, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Σ",
                    color = ObsidianBackground,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = FontFamily.Serif
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "AURUM PRO",
                    color = GoldPrimary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 0.5.sp,
                    fontFamily = FontFamily.Serif
                )
                Text(
                    text = "GOLD TIER",
                    color = GoldSecondary,
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            }

            // Streak badge
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(ObsidianSurfaceVariant)
                    .border(1.dp, GoldSecondary.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "🔥 $streak",
                    color = GoldTertiary,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(6.dp))

            // Coins badge
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(GoldGradient)
                    .clickable { onCoinsClick() }
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .testTag("coins_header_badge")
            ) {
                Text(
                    text = "💰 $coins",
                    color = ObsidianBackground,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}

@Composable
fun GoldenCard(
    modifier: Modifier = Modifier,
    borderColor: Color = ObsidianCardBorder,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(ObsidianCardGradient)
            .border(1.dp, borderColor.copy(alpha = 0.7f), RoundedCornerShape(18.dp))
            .padding(16.dp)
    ) {
        content()
    }
}

@Composable
fun GoldenButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: String? = null,
    isPrimary: Boolean = true
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .then(
                if (isPrimary) {
                    Modifier.background(GoldGradient)
                } else {
                    Modifier
                        .background(ObsidianSurfaceVariant)
                        .border(1.dp, GoldSecondary, RoundedCornerShape(12.dp))
                }
            )
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .testTag("golden_button_${text.lowercase().replace(" ", "_")}"),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (icon != null) {
                Text(text = icon, fontSize = 14.sp)
                Spacer(modifier = Modifier.width(6.dp))
            }
            Text(
                text = text,
                color = if (isPrimary) ObsidianBackground else GoldPrimary,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 13.sp,
                letterSpacing = 0.5.sp
            )
        }
    }
}

@Composable
fun StepViewItem(step: SolutionStep) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(ObsidianSurfaceVariant)
            .border(
                width = 1.dp,
                color = if (step.isFinal) VerifiedGreen.copy(alpha = 0.8f) else GoldSecondary.copy(alpha = 0.4f),
                shape = RoundedCornerShape(14.dp)
            )
            .padding(14.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            // Step Number Badge
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(if (step.isFinal) VerifiedGreen else GoldPrimary),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${step.stepNumber}",
                    color = ObsidianBackground,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = step.title,
                    color = if (step.isFinal) GoldTertiary else TextPrimary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Math formulation display container
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(ObsidianBackground)
                        .border(1.dp, Color(0xFF262630), RoundedCornerShape(8.dp))
                        .padding(horizontal = 10.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = step.mathExpression,
                        color = GoldPrimary,
                        fontSize = 13.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                if (step.explanation.isNotBlank()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = step.explanation,
                        color = TextSecondary,
                        fontSize = 11.sp,
                        lineHeight = 16.sp
                    )
                }

                if (step.note != null) {
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = "💡 ${step.note}",
                        color = TextMuted,
                        fontSize = 10.sp
                    )
                }
            }
        }
    }
}

@Composable
fun VerifiedSolutionBadge(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(ObsidianSurface)
            .border(1.dp, VerifiedGreen.copy(alpha = 0.4f), RoundedCornerShape(10.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = "Verified",
            tint = VerifiedGreen,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Solution verified ✓  |  Exact form shown  |  Aurum AI",
            color = VerifiedGreen,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}
