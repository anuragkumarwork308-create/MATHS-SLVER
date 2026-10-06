package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.engine.MathSolverEngine
import com.example.ui.components.CanvasGraphCalculator
import com.example.ui.components.GoldenButton
import com.example.ui.components.GoldenCard
import com.example.ui.components.MathGraphView
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.GoldSecondary
import com.example.ui.theme.ObsidianBackground
import com.example.ui.theme.ObsidianSurfaceVariant
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun GraphScreen(
    modifier: Modifier = Modifier
) {
    var graphEquationInput by remember { mutableStateOf("y = 2x² + 5x - 12") }
    var plottedEquation by remember { mutableStateOf("y = 2x² + 5x - 12") }
    var currentResult by remember { mutableStateOf(MathSolverEngine.solve("2x² + 5x - 12 = 0")) }
    var activeGraphTab by remember { mutableStateOf("interactive") } // "interactive" or "custom"

    val presets = listOf(
        "y = 2x² + 5x - 12" to "Quadratic Parabola",
        "y = x² - 4" to "Standard Parabola",
        "y = 2x + 3" to "Linear Line",
        "y = 2x³ - 4x² + 3x - 6" to "Cubic Polynomial",
        "y = sin(x)" to "Trig Sine Wave"
    )

    fun plot(eq: String) {
        plottedEquation = eq
        val normalizedForSolver = eq.replace("y =", "").trim() + " = 0"
        currentResult = MathSolverEngine.solve(normalizedForSolver)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ObsidianBackground)
            .verticalScroll(rememberScrollState())
            .padding(14.dp)
    ) {
        Text(
            text = "📈 FUNCTION GRAPH CALCULATOR",
            color = GoldPrimary,
            fontSize = 17.sp,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = 1.sp
        )
        Text(
            text = "Plot y=mx+c, y=x² and curves with Canvas API • 100% Offline",
            color = TextSecondary,
            fontSize = 11.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Tab Switcher between Interactive Canvas Calculator and Custom Function Plotter
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(if (activeGraphTab == "interactive") Color(0xFF221A00) else ObsidianSurfaceVariant)
                    .border(
                        1.dp,
                        if (activeGraphTab == "interactive") GoldPrimary else Color(0xFF2E2E3E),
                        RoundedCornerShape(12.dp)
                    )
                    .clickable { activeGraphTab = "interactive" }
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Interactive Canvas",
                    color = if (activeGraphTab == "interactive") GoldPrimary else TextSecondary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(if (activeGraphTab == "custom") Color(0xFF221A00) else ObsidianSurfaceVariant)
                    .border(
                        1.dp,
                        if (activeGraphTab == "custom") GoldPrimary else Color(0xFF2E2E3E),
                        RoundedCornerShape(12.dp)
                    )
                    .clickable { activeGraphTab = "custom" }
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Equation Solver Plot",
                    color = if (activeGraphTab == "custom") GoldPrimary else TextSecondary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        if (activeGraphTab == "interactive") {
            // Interactive Offline-capable Canvas Graph Calculator Component
            CanvasGraphCalculator()
        } else {
            // Equation input
            GoldenCard {
                Column {
                    Text(
                        text = "Function f(x):",
                        color = GoldSecondary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(ObsidianBackground)
                            .border(1.dp, GoldSecondary, RoundedCornerShape(10.dp))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        TextField(
                            value = graphEquationInput,
                            onValueChange = { graphEquationInput = it },
                            modifier = Modifier.fillMaxWidth().testTag("graph_function_input"),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedTextColor = GoldPrimary,
                                unfocusedTextColor = GoldPrimary,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            textStyle = androidx.compose.ui.text.TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    GoldenButton(
                        text = "PLOT FUNCTION",
                        icon = "📈",
                        isPrimary = true,
                        onClick = { plot(graphEquationInput) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Presets Chips
            Text(
                text = "PRESET CURVES",
                color = GoldSecondary,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                presets.forEach { (eq, desc) ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(ObsidianSurfaceVariant)
                            .border(1.dp, GoldSecondary.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                            .clickable {
                                graphEquationInput = eq
                                plot(eq)
                            }
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Column {
                            Text(text = eq, color = GoldPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                            Text(text = desc, color = TextMuted, fontSize = 9.sp)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // The Graph View
            MathGraphView(
                equation = plottedEquation,
                points = currentResult.graphPoints,
                roots = currentResult.rootsOrValues
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Function Analysis Card
            GoldenCard {
                Column {
                    Text(
                        text = "FUNCTION PROPERTIES",
                        color = GoldSecondary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "Domain:", color = TextSecondary, fontSize = 12.sp)
                        Text(text = "(-∞ , +∞)", color = TextPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "Real Roots / Zeroes:", color = TextSecondary, fontSize = 12.sp)
                        Text(
                            text = if (currentResult.rootsOrValues.isNotEmpty()) currentResult.rootsOrValues.joinToString(", ") else "None",
                            color = GoldPrimary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
