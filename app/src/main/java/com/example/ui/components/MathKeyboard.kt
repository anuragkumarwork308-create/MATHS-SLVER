package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.GoldSecondary
import com.example.ui.theme.ObsidianBackground
import com.example.ui.theme.ObsidianSurfaceVariant

@Composable
fun MathKeyboard(
    onKeyPress: (String) -> Unit,
    onBackspace: () -> Unit,
    onClear: () -> Unit,
    onSolve: () -> Unit,
    modifier: Modifier = Modifier
) {
    var keyboardTab by remember { mutableStateOf("Basic") }
    val tabs = listOf("Basic", "Algebra", "Calculus", "Trig")

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(ObsidianBackground)
            .border(
                1.dp,
                GoldSecondary.copy(alpha = 0.4f),
                RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            )
            .padding(8.dp)
    ) {
        // Quick Tab Switcher
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            tabs.forEach { tab ->
                val isSelected = keyboardTab == tab
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(if (isSelected) GoldPrimary else ObsidianSurfaceVariant)
                        .border(
                            1.dp,
                            if (isSelected) GoldPrimary else Color(0xFF33333E),
                            RoundedCornerShape(20.dp)
                        )
                        .clickable { keyboardTab = tab }
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                        .testTag("tab_keyboard_$tab"),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = tab,
                        color = if (isSelected) ObsidianBackground else GoldSecondary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Key Rows based on Tab
        val rows: List<List<String>> = when (keyboardTab) {
            "Algebra" -> listOf(
                listOf("x", "y", "x²", "x³", "^", "√"),
                listOf("7", "8", "9", "÷", "(", ")"),
                listOf("4", "5", "6", "×", "+", "-"),
                listOf("1", "2", "3", "=", "π", "|x|"),
                listOf("0", ".", "CLEAR", "⌫", "SOLVE")
            )
            "Calculus" -> listOf(
                listOf("d/dx", "∫", "lim", "dx", "x", "^"),
                listOf("sin(x)", "cos(x)", "tan(x)", "ln", "e^x", "√"),
                listOf("7", "8", "9", "÷", "(", ")"),
                listOf("4", "5", "6", "×", "+", "-"),
                listOf("1", "2", "3", "0", "⌫", "SOLVE")
            )
            "Trig" -> listOf(
                listOf("sin", "cos", "tan", "π", "θ", "°"),
                listOf("sin²", "cos²", "tan²", "arcsin", "arccos", "arctan"),
                listOf("7", "8", "9", "÷", "(", ")"),
                listOf("4", "5", "6", "×", "+", "-"),
                listOf("1", "2", "3", "0", "⌫", "SOLVE")
            )
            else -> listOf( // "Basic" iOS Photomath style
                listOf("x", "x²", "√", "(", ")", "^"),
                listOf("7", "8", "9", "÷", "π", "⌫"),
                listOf("4", "5", "6", "×", "=", "-"),
                listOf("1", "2", "3", "+", ".", "CLEAR"),
                listOf("0", "2x² + 5x - 12 = 0", "SOLVE")
            )
        }

        rows.forEach { rowKeys ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 3.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                rowKeys.forEach { key ->
                    val isSolve = key == "SOLVE"
                    val isClear = key == "CLEAR"
                    val isBackspace = key == "⌫"
                    val isPreset = key.contains("2x²")

                    Box(
                        modifier = Modifier
                            .weight(if (isPreset) 2.2f else if (isSolve) 1.5f else 1f)
                            .height(44.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .then(
                            if (isSolve) {
                                Modifier.background(GoldGradient)
                            } else if (isClear) {
                                Modifier.background(Color(0xFF4A1010))
                            } else if (isBackspace) {
                                Modifier.background(Color(0xFF262634))
                            } else {
                                Modifier
                                    .background(ObsidianSurfaceVariant)
                                    .border(1.dp, Color(0xFF2A2A38), RoundedCornerShape(8.dp))
                            }
                        )
                        .clickable {
                            when (key) {
                                "SOLVE" -> onSolve()
                                "CLEAR" -> onClear()
                                "⌫" -> onBackspace()
                                else -> onKeyPress(key)
                            }
                        }
                        .testTag("key_$key"),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = key,
                            color = if (isSolve) ObsidianBackground else if (isClear) Color(0xFFFF8888) else GoldPrimary,
                            fontSize = if (isPreset) 11.sp else 13.sp,
                            fontWeight = if (isSolve || isClear) FontWeight.ExtraBold else FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
