package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Keyboard
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.model.RecentHistoryItem
import com.example.model.SolveResult
import com.example.ui.components.GoldGradient
import com.example.ui.components.GoldenButton
import com.example.ui.components.GoldenCard
import com.example.ui.components.MathGraphView
import com.example.ui.components.MathKeyboard
import com.example.ui.components.StepViewItem
import com.example.ui.components.VerifiedSolutionBadge
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

@Composable
fun SolverScreen(
    initialInput: String = "2x² + 5x - 12 = 0",
    onCameraClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var inputQuery by remember { mutableStateOf(initialInput) }
    var currentResult by remember { mutableStateOf<SolveResult?>(MathSolverEngine.solve(initialInput)) }
    var showGraph by remember { mutableStateOf(false) }
    var showKeyboard by remember { mutableStateOf(true) }
    var doubtExplanation by remember { mutableStateOf<String?>(null) }
    var copyStatus by remember { mutableStateOf(false) }

    val recentItems = remember {
        mutableListOf(
            RecentHistoryItem(problem = "2x² + 5x - 12 = 0", answer = "x = 3/2 , x = -4", category = "Quadratic", timestamp = "Just now"),
            RecentHistoryItem(problem = "x³ - 6x² + 11x - 6 = 0", answer = "x = 1, 2, 3", category = "Polynomial", timestamp = "Today • 14:32"),
            RecentHistoryItem(problem = "sin(x) + cos(x) = 1", answer = "x = 0, π/2", category = "Trigonometry", timestamp = "Yesterday • 09:17")
        )
    }

    fun executeSolve() {
        val res = MathSolverEngine.solve(inputQuery)
        currentResult = res
        recentItems.add(
            0,
            RecentHistoryItem(
                problem = inputQuery,
                answer = res.finalAnswer,
                category = res.category,
                timestamp = "Just now"
            )
        )
        doubtExplanation = null
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ObsidianBackground)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(14.dp)
        ) {
            // MATH SOLVER CARD (Matches Uploaded Design)
            GoldenCard {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = "Spark",
                                tint = GoldPrimary,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "MATH SOLVER",
                                color = GoldPrimary,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 1.sp
                            )
                        }

                        IconButton(
                            onClick = { showKeyboard = !showKeyboard },
                            modifier = Modifier.size(28.dp).testTag("toggle_keyboard_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Keyboard,
                                contentDescription = "Toggle Keyboard",
                                tint = GoldSecondary,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Enter Problem",
                        color = TextSecondary,
                        fontSize = 11.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    // Input Box
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(ObsidianBackground)
                            .border(1.dp, GoldSecondary, RoundedCornerShape(12.dp))
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            TextField(
                                value = inputQuery,
                                onValueChange = { inputQuery = it },
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("math_problem_input"),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                    focusedTextColor = GoldPrimary,
                                    unfocusedTextColor = GoldPrimary,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent
                                ),
                                textStyle = androidx.compose.ui.text.TextStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Monospace
                                )
                            )
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit",
                                tint = GoldSecondary,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Three Buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        GoldenButton(
                            text = "SOLVE",
                            icon = "✨",
                            isPrimary = true,
                            onClick = { executeSolve() },
                            modifier = Modifier.weight(1f)
                        )
                        GoldenButton(
                            text = "STEPS",
                            icon = "📋",
                            isPrimary = false,
                            onClick = { showGraph = false },
                            modifier = Modifier.weight(1f)
                        )
                        GoldenButton(
                            text = "GRAPH",
                            icon = "📈",
                            isPrimary = showGraph,
                            onClick = { showGraph = !showGraph },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Graph Section (if active)
            AnimatedVisibility(visible = showGraph && currentResult?.graphPoints?.isNotEmpty() == true) {
                currentResult?.let { res ->
                    Column {
                        MathGraphView(
                            equation = res.graphEquation ?: res.problem,
                            points = res.graphPoints,
                            roots = res.rootsOrValues
                        )
                        Spacer(modifier = Modifier.height(14.dp))
                    }
                }
            }

            // SOLUTION CARD (Matches Uploaded Design)
            currentResult?.let { res ->
                GoldenCard {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "SOLUTION",
                                color = GoldSecondary,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 1.sp
                            )
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Color(0xFF1E1700))
                                    .border(1.dp, GoldSecondary, RoundedCornerShape(12.dp))
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = "Solved ✓",
                                    color = GoldPrimary,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Large Answer
                        Text(
                            text = res.finalAnswer,
                            color = GoldPrimary,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.ExtraBold,
                            fontFamily = FontFamily.Serif
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Subtle Divider
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(GoldSecondary.copy(alpha = 0.4f))
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "STEP-BY-STEP SOLUTION",
                                color = GoldSecondary,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.8.sp
                            )
                            IconButton(
                                onClick = { copyStatus = true },
                                modifier = Modifier.size(24.dp).testTag("copy_steps_btn")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ContentCopy,
                                    contentDescription = "Copy",
                                    tint = if (copyStatus) VerifiedGreen else GoldSecondary,
                                    modifier = Modifier.size(15.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Steps list
                        res.steps.forEach { step ->
                            StepViewItem(step = step)
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        VerifiedSolutionBadge()

                        // AI Doubt Solver prompt buttons
                        Spacer(modifier = Modifier.height(14.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFF111118))
                                .border(1.dp, Color(0xFF262634), RoundedCornerShape(12.dp))
                                .padding(12.dp)
                        ) {
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.AutoAwesome,
                                        contentDescription = "AI",
                                        tint = GoldPrimary,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "Boss AI Doubt Assistant",
                                        color = GoldTertiary,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Ask any step doubt or request a short trick for competitive exams.",
                                    color = TextSecondary,
                                    fontSize = 11.sp
                                )

                                Spacer(modifier = Modifier.height(8.dp))
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(16.dp))
                                            .background(ObsidianSurfaceVariant)
                                            .border(1.dp, GoldSecondary.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                                            .clickable {
                                                doubtExplanation = "In Step 2, Discriminant D = b² - 4ac = 5² - 4(2)(-12) = 25 - (-96) = 121. Since 121 > 0 and a perfect square (11²), the roots are rational and distinct!"
                                            }
                                            .padding(horizontal = 10.dp, vertical = 6.dp)
                                            .testTag("doubt_step2_btn")
                                    ) {
                                        Text("Step 2 samjhao", color = GoldPrimary, fontSize = 11.sp)
                                    }

                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(16.dp))
                                            .background(ObsidianSurfaceVariant)
                                            .border(1.dp, GoldSecondary.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                                            .clickable {
                                                doubtExplanation = "Short Trick: For 2x² + 5x - 12 = 0, product of extremes is 2 × (-12) = -24. Find two numbers multiplying to -24 and adding to 5: (+8 and -3). Divide by a=2: +8/2 = +4 and -3/2. Reverse signs → x = -4, +3/2 in 5 seconds!"
                                            }
                                            .padding(horizontal = 10.dp, vertical = 6.dp)
                                            .testTag("doubt_short_trick_btn")
                                    ) {
                                        Text("Short Trick batao ⚡", color = GoldPrimary, fontSize = 11.sp)
                                    }
                                }

                                if (doubtExplanation != null) {
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(Color(0xFF191924))
                                            .padding(10.dp)
                                    ) {
                                        Text(
                                            text = doubtExplanation ?: "",
                                            color = GoldTertiary,
                                            fontSize = 12.sp,
                                            lineHeight = 17.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // RECENT SOLUTIONS SECTION (Matches Uploaded Design)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(ObsidianSurface)
                    .border(1.dp, ObsidianCardBorder.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                    .padding(14.dp)
            ) {
                Text(
                    text = "🕒 RECENT SOLUTIONS",
                    color = GoldSecondary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(10.dp))

                recentItems.take(4).forEach { item ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(ObsidianSurfaceVariant)
                            .clickable {
                                inputQuery = item.problem
                                executeSolve()
                            }
                            .padding(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = item.problem,
                                    color = TextPrimary,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Monospace
                                )
                                Text(
                                    text = "→ ${item.answer}",
                                    color = GoldPrimary,
                                    fontSize = 11.sp
                                )
                            }
                            Text(
                                text = item.timestamp,
                                color = TextMuted,
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }

        // Collapsible Math Keyboard
        AnimatedVisibility(visible = showKeyboard) {
            MathKeyboard(
                onKeyPress = { key ->
                    inputQuery = if (key.contains("2x²")) key else inputQuery + key
                },
                onBackspace = {
                    if (inputQuery.isNotEmpty()) {
                        inputQuery = inputQuery.dropLast(1)
                    }
                },
                onClear = { inputQuery = "" },
                onSolve = { executeSolve() }
            )
        }
    }
}
