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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.data.QuizRepository
import com.example.model.QuizQuestion
import com.example.ui.components.GoldGradient
import com.example.ui.components.GoldenButton
import com.example.ui.components.GoldenCard
import com.example.ui.theme.AccentRed
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.GoldSecondary
import com.example.ui.theme.GoldTertiary
import com.example.ui.theme.ObsidianBackground
import com.example.ui.theme.ObsidianSurfaceVariant
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.VerifiedGreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun QuizScreen(
    initialClass: Int = 10,
    modifier: Modifier = Modifier
) {
    var selectedClassLevel by remember { mutableIntStateOf(initialClass) }
    var inTestSession by remember { mutableStateOf(false) }
    var currentQuestionIdx by remember { mutableIntStateOf(0) }
    var scoreCount by remember { mutableIntStateOf(0) }
    var selectedAnswer by remember { mutableStateOf<String?>(null) }
    var isTestFinished by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val currentQuestions = remember(selectedClassLevel) {
        QuizRepository.classQuizzes[selectedClassLevel] ?: QuizRepository.classQuizzes[10]!!
    }

    fun startQuiz(classNum: Int) {
        selectedClassLevel = classNum
        currentQuestionIdx = 0
        scoreCount = 0
        selectedAnswer = null
        isTestFinished = false
        inTestSession = true
    }

    fun selectOption(opt: String, currentQ: QuizQuestion) {
        if (selectedAnswer != null) return
        selectedAnswer = opt
        val isCorrect = opt == currentQ.answer
        if (isCorrect) {
            scoreCount += 1
        }
        coroutineScope.launch {
            delay(900)
            if (currentQuestionIdx + 1 < currentQuestions.size) {
                currentQuestionIdx += 1
                selectedAnswer = null
            } else {
                isTestFinished = true
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ObsidianBackground)
            .verticalScroll(rememberScrollState())
            .padding(14.dp)
    ) {
        // Class Selection Overview when not in test
        if (!inTestSession) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "🧠 CLASS QUIZZES & TESTS",
                        color = GoldPrimary,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "Classes 1 to 12 • Instant Scoring & Certificate",
                        color = TextSecondary,
                        fontSize = 11.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Class Grid
            val classList = (1..12).toList()
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                for (rowItems in classList.chunked(2)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        rowItems.forEach { c ->
                            val meta = QuizRepository.getClassMetadata(c)
                            GoldenCard(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { startQuiz(c) }
                                    .testTag("quiz_class_card_$c")
                            ) {
                                Column {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(text = meta.second, fontSize = 22.sp)
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(12.dp))
                                                .background(GoldGradient)
                                                .padding(horizontal = 8.dp, vertical = 2.dp)
                                        ) {
                                            Text(
                                                text = "TEST",
                                                color = ObsidianBackground,
                                                fontSize = 9.sp,
                                                fontWeight = FontWeight.ExtraBold
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = meta.first,
                                        color = GoldPrimary,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = meta.third,
                                        color = TextMuted,
                                        fontSize = 10.sp,
                                        maxLines = 2
                                    )

                                    Spacer(modifier = Modifier.height(10.dp))
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(ObsidianBackground)
                                            .border(1.dp, Color(0xFF262634), RoundedCornerShape(8.dp))
                                            .padding(6.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "⚡ START TEST",
                                            color = GoldSecondary,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else if (isTestFinished) {
            // GOLDEN CERTIFICATE SCREEN
            val percent = (scoreCount * 100) / currentQuestions.size
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                GoldenCard {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = if (percent >= 80) "🏆" else if (percent >= 50) "🎖️" else "📚",
                            fontSize = 46.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "GOLDEN CERTIFICATE",
                            color = GoldPrimary,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            fontFamily = FontFamily.Serif,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = "Class $selectedClassLevel Mathematics Champion",
                            color = GoldTertiary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(14.dp))
                        Text(
                            text = "$scoreCount / ${currentQuestions.size}",
                            color = GoldPrimary,
                            fontSize = 38.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                        Text(
                            text = if (percent >= 80) "$percent% • GOLD TOPPER" else if (percent >= 50) "$percent% • PASSED WITH MERIT" else "$percent% • KEEP PRACTICING",
                            color = if (percent >= 80) VerifiedGreen else GoldPrimary,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Certificate Box
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(ObsidianBackground)
                                .border(1.5.dp, GoldSecondary, RoundedCornerShape(12.dp))
                                .padding(14.dp)
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "OFFICIAL AURUM CERTIFICATE",
                                    color = GoldSecondary,
                                    fontSize = 10.sp,
                                    letterSpacing = 1.5.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "Awarded for exceptional mathematical reasoning and precision in Class $selectedClassLevel Curriculum.",
                                    color = TextSecondary,
                                    fontSize = 11.sp,
                                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                                    lineHeight = 16.sp
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Signature: Σ Aurum AI Examiner",
                                    color = GoldTertiary,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Serif
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            GoldenButton(
                                text = "BACK",
                                isPrimary = false,
                                onClick = { inTestSession = false },
                                modifier = Modifier.weight(1f)
                            )
                            GoldenButton(
                                text = "RETAKE TEST",
                                isPrimary = true,
                                onClick = { startQuiz(selectedClassLevel) },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        } else {
            // ACTIVE TEST QUESTION SESSION
            val q = currentQuestions[currentQuestionIdx]

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "‹ Exit",
                    color = GoldSecondary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { inTestSession = false }
                )
                Text(
                    text = "CLASS $selectedClassLevel TEST • Q${currentQuestionIdx + 1}/${currentQuestions.size}",
                    color = GoldPrimary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(GoldGradient)
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "Score: $scoreCount",
                        color = ObsidianBackground,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Progress Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(ObsidianSurfaceVariant)
            ) {
                val progressFraction = (currentQuestionIdx + 1).toFloat() / currentQuestions.size.toFloat()
                Box(
                    modifier = Modifier
                        .fillMaxWidth(progressFraction)
                        .height(6.dp)
                        .background(GoldGradient)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // QUESTION CARD
            GoldenCard {
                Column {
                    Text(
                        text = "QUESTION ${currentQuestionIdx + 1}",
                        color = GoldSecondary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = q.question,
                        color = TextPrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        lineHeight = 24.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // OPTIONS LIST
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                q.options.forEach { opt ->
                    val isSelected = selectedAnswer == opt
                    val isCorrect = opt == q.answer

                    val bgColor = when {
                        isSelected && isCorrect -> Color(0xFF132A17)
                        isSelected && !isCorrect -> Color(0xFF321313)
                        else -> ObsidianSurfaceVariant
                    }
                    val borderColor = when {
                        isSelected && isCorrect -> VerifiedGreen
                        isSelected && !isCorrect -> AccentRed
                        else -> GoldSecondary.copy(alpha = 0.4f)
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(bgColor)
                            .border(1.dp, borderColor, RoundedCornerShape(12.dp))
                            .clickable { selectOption(opt, q) }
                            .padding(14.dp)
                            .testTag("quiz_option_${opt.replace(" ", "_")}")
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = opt,
                                color = if (isSelected && isCorrect) VerifiedGreen else GoldPrimary,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )

                            if (isSelected) {
                                Icon(
                                    imageVector = if (isCorrect) Icons.Default.Check else Icons.Default.Close,
                                    contentDescription = "Status",
                                    tint = if (isCorrect) VerifiedGreen else AccentRed,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }

            // Show instant explanation after selection
            if (selectedAnswer != null) {
                Spacer(modifier = Modifier.height(14.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFF13131E))
                        .border(1.dp, Color(0xFF26263A), RoundedCornerShape(10.dp))
                        .padding(12.dp)
                ) {
                    Column {
                        Text(
                            text = "💡 EXPLANATION:",
                            color = GoldPrimary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = q.explanation,
                            color = TextSecondary,
                            fontSize = 12.sp,
                            lineHeight = 16.sp
                        )
                    }
                }
            }
        }
    }
}
