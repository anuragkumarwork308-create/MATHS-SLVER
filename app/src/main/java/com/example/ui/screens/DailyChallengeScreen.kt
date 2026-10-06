package com.example.ui.screens

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.viewinterop.AndroidView
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

data class DailyQuestion(
    val classLevel: Int,
    val question: String,
    val options: List<String>,
    val answer: String,
    val videoUrl: String,
    val explanation: String
)

data class LeaderboardUser(
    val rank: Int,
    val name: String,
    val classLevel: Int,
    val score: Int,
    val avatar: String,
    val badge: String
)

@Composable
fun DailyChallengeScreen(
    userScore: Int,
    onScoreEarned: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var activeSubTab by remember { mutableStateOf("daily") }
    var dailyDone by remember { mutableStateOf(false) }
    var selectedDailyOpt by remember { mutableStateOf<String?>(null) }
    var alertMsg by remember { mutableStateOf<String?>(null) }

    val dailyQuestions = remember {
        listOf(
            DailyQuestion(
                classLevel = 10,
                question = "sin(30°) + cos(60°) = ?",
                options = listOf("1", "1/2", "0"),
                answer = "1",
                videoUrl = "https://www.youtube.com/embed/Cn1Y8K1Lzro",
                explanation = "sin(30°) = 1/2, cos(60°) = 1/2  →  1/2 + 1/2 = 1."
            ),
            DailyQuestion(
                classLevel = 9,
                question = "(a + b)² = ?",
                options = listOf("a² + 2ab + b²", "a² + b²", "a² - b²"),
                answer = "a² + 2ab + b²",
                videoUrl = "https://www.youtube.com/embed/2Kqia_R2b1w",
                explanation = "Standard algebraic identity: (a + b)(a + b) = a² + 2ab + b²."
            ),
            DailyQuestion(
                classLevel = 5,
                question = "LCM of 12 and 18 = ?",
                options = listOf("36", "18", "12"),
                answer = "36",
                videoUrl = "https://www.youtube.com/embed/J7a7u7D7m5E",
                explanation = "Multiples of 12: 12, 24, 36. Multiples of 18: 18, 36. Smallest common is 36."
            )
        )
    }

    val dailyQ = dailyQuestions[0]

    val leaderboard = listOf(
        LeaderboardUser(1, "Aman Kumar", 10, 9850, "👑", "GOLD TOPPER"),
        LeaderboardUser(2, "Priya Singh", 10, 9720, "🥈", "SILVER"),
        LeaderboardUser(3, "Rahul Raj", 9, 9650, "🥉", "BRONZE"),
        LeaderboardUser(4, "Sneha Gupta", 8, 9420, "🔥", "FIRE"),
        LeaderboardUser(5, "Vikash Yadav", 10, 9300, "⚡", "STREAK"),
        LeaderboardUser(6, "Anjali", 9, 9150, "📚", "HARD WORKER"),
        LeaderboardUser(7, "Rohit", 7, 8900, "🎯", "SHARP"),
        LeaderboardUser(8, "Pooja", 6, 8750, "✏️", "RISING"),
        LeaderboardUser(9, "Sonu", 5, 8600, "🧸", "STAR"),
        LeaderboardUser(10, "You", 10, 8400 + userScore, "😎", "YOU")
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ObsidianBackground)
    ) {
        // Sub-Tab Switcher
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 14.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf(
                "daily" to "🔥 Daily Challenge",
                "leaderboard" to "🏆 Leaderboard Top 10",
                "video" to "🎬 Video Solution"
            ).forEach { (id, label) ->
                val isSelected = activeSubTab == id
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .then(
                            if (isSelected) Modifier.background(GoldGradient)
                            else Modifier.background(ObsidianSurfaceVariant)
                        )
                        .border(
                            1.dp,
                            if (isSelected) GoldPrimary else GoldSecondary.copy(alpha = 0.4f),
                            RoundedCornerShape(20.dp)
                        )
                        .clickable { activeSubTab = id }
                        .padding(horizontal = 14.dp, vertical = 7.dp)
                        .testTag("daily_tab_$id"),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = label,
                        color = if (isSelected) ObsidianBackground else GoldPrimary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
        }

        // Sub-Tab Content
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 14.dp, vertical = 6.dp)
        ) {
            when (activeSubTab) {
                "daily" -> {
                    // DAILY CHALLENGE CARD
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(18.dp))
                            .background(Color(0xFF161205))
                            .border(2.dp, GoldPrimary, RoundedCornerShape(18.dp))
                            .padding(16.dp)
                    ) {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.LocalFireDepartment,
                                        contentDescription = "Live",
                                        tint = AccentRed,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "DAILY CHALLENGE • TODAY",
                                        color = GoldPrimary,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        letterSpacing = 1.sp
                                    )
                                }

                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(AccentRed)
                                        .padding(horizontal = 8.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = "LIVE",
                                        color = Color.White,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.ExtraBold
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // Question Box
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(ObsidianBackground)
                                    .border(1.dp, GoldSecondary, RoundedCornerShape(14.dp))
                                    .padding(14.dp)
                            ) {
                                Column {
                                    Text(
                                        text = "Class ${dailyQ.classLevel} • Golden Hard Question",
                                        color = TextMuted,
                                        fontSize = 10.sp
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = dailyQ.question,
                                        color = TextPrimary,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        fontFamily = FontFamily.Monospace
                                    )

                                    Spacer(modifier = Modifier.height(14.dp))

                                    // Options
                                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                        dailyQ.options.forEach { opt ->
                                            val isChosen = selectedDailyOpt == opt
                                            val isCorrect = opt == dailyQ.answer

                                            val bg = when {
                                                dailyDone && isCorrect -> Color(0xFF132A17)
                                                isChosen && !isCorrect -> Color(0xFF321313)
                                                else -> ObsidianSurfaceVariant
                                            }
                                            val border = when {
                                                dailyDone && isCorrect -> VerifiedGreen
                                                isChosen && !isCorrect -> AccentRed
                                                else -> GoldSecondary.copy(alpha = 0.5f)
                                            }

                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .clip(RoundedCornerShape(10.dp))
                                                    .background(bg)
                                                    .border(1.dp, border, RoundedCornerShape(10.dp))
                                                    .clickable {
                                                        selectedDailyOpt = opt
                                                        if (opt == dailyQ.answer) {
                                                            if (!dailyDone) {
                                                                dailyDone = true
                                                                onScoreEarned(100)
                                                                alertMsg = "Correct! +100 Coins 🏆"
                                                            }
                                                        } else {
                                                            alertMsg = "Wrong! Check Video Solution 🎬"
                                                            activeSubTab = "video"
                                                        }
                                                    }
                                                    .padding(12.dp)
                                                    .testTag("daily_opt_$opt")
                                            ) {
                                                Row(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.SpaceBetween,
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Text(
                                                        text = opt,
                                                        color = if (dailyDone && isCorrect) VerifiedGreen else GoldPrimary,
                                                        fontSize = 14.sp,
                                                        fontWeight = FontWeight.Bold
                                                    )
                                                    if (dailyDone && isCorrect) {
                                                        Text("✅ +100 Coins", color = VerifiedGreen, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // Stats row
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(ObsidianBackground)
                                        .border(1.dp, Color(0xFF222230), RoundedCornerShape(8.dp))
                                        .padding(8.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text("⏰", fontSize = 14.sp)
                                        Text("23h 45m left", color = TextSecondary, fontSize = 10.sp)
                                    }
                                }
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(ObsidianBackground)
                                        .border(1.dp, Color(0xFF222230), RoundedCornerShape(8.dp))
                                        .padding(8.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text("👥", fontSize = 14.sp)
                                        Text("12.5k playing", color = TextSecondary, fontSize = 10.sp)
                                    }
                                }
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(ObsidianBackground)
                                        .border(1.dp, GoldPrimary, RoundedCornerShape(8.dp))
                                        .padding(8.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text("💰", fontSize = 14.sp)
                                        Text("+100 Coins", color = GoldPrimary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // WEEKLY STREAK CARD
                    GoldenCard {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "📅 WEEKLY STREAK",
                                    color = GoldSecondary,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "🔥 7 Days Active",
                                    color = GoldPrimary,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun").forEachIndexed { i, d ->
                                    val active = i < 5 || dailyDone
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(if (active) GoldPrimary else Color(0xFF1E1E28))
                                            .padding(vertical = 8.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            Text(text = d, color = if (active) ObsidianBackground else TextMuted, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                                            Text(text = if (active) "🔥" else "○", fontSize = 12.sp)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                "leaderboard" -> {
                    // LEADERBOARD TOP 10 CARD
                    GoldenCard {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "🏆 TOP 10 TOPPERS • GOLDEN LEAGUE",
                                    color = GoldPrimary,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    letterSpacing = 1.sp
                                )
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(GoldPrimary)
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text("LIVE", color = ObsidianBackground, fontSize = 9.sp, fontWeight = FontWeight.ExtraBold)
                                }
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            // Podium for Top 3 (2nd, 1st, 3rd)
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.Bottom
                            ) {
                                // 2nd Place
                                PodiumItem(user = leaderboard[1], isFirst = false, modifier = Modifier.weight(1f))
                                Spacer(modifier = Modifier.width(8.dp))
                                // 1st Place (Center and elevated)
                                PodiumItem(user = leaderboard[0], isFirst = true, modifier = Modifier.weight(1.2f))
                                Spacer(modifier = Modifier.width(8.dp))
                                // 3rd Place
                                PodiumItem(user = leaderboard[2], isFirst = false, modifier = Modifier.weight(1f))
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Ranks 4 to 10
                            leaderboard.drop(3).forEach { u ->
                                val isMe = u.name == "You"
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(if (isMe) Color(0xFF1E1700) else ObsidianSurfaceVariant)
                                        .border(
                                            1.dp,
                                            if (isMe) GoldPrimary else Color(0xFF222230),
                                            RoundedCornerShape(10.dp)
                                        )
                                        .padding(10.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(
                                                text = "#${u.rank}",
                                                color = if (isMe) GoldPrimary else TextMuted,
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.ExtraBold,
                                                modifier = Modifier.width(26.dp)
                                            )
                                            Text(text = u.avatar, fontSize = 18.sp)
                                            Spacer(modifier = Modifier.width(10.dp))
                                            Column {
                                                Text(
                                                    text = if (isMe) "${u.name} (You)" else u.name,
                                                    color = if (isMe) GoldPrimary else TextPrimary,
                                                    fontSize = 13.sp,
                                                    fontWeight = FontWeight.Bold
                                                )
                                                Text(
                                                    text = "Class ${u.classLevel} • ${u.badge}",
                                                    color = TextMuted,
                                                    fontSize = 10.sp
                                                )
                                            }
                                        }

                                        Column(horizontalAlignment = Alignment.End) {
                                            Text(
                                                text = "${u.score}",
                                                color = GoldPrimary,
                                                fontSize = 13.sp,
                                                fontWeight = FontWeight.ExtraBold
                                            )
                                            Text(text = "pts", color = TextMuted, fontSize = 9.sp)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                "video" -> {
                    // VIDEO EXPLANATION SCREEN
                    GoldenCard {
                        Column {
                            Text(
                                text = "🎬 VIDEO EXPLANATION & THEORY",
                                color = GoldPrimary,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 1.sp
                            )
                            Text(
                                text = "Teacher: Golden Sir • 2.5M views • HD",
                                color = TextMuted,
                                fontSize = 11.sp
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            // Animated/Embedded Video Box
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Color(0xFF000000))
                                    .border(1.dp, GoldSecondary, RoundedCornerShape(12.dp))
                            ) {
                                AndroidView(
                                    factory = { ctx ->
                                        WebView(ctx).apply {
                                            webViewClient = WebViewClient()
                                            settings.javaScriptEnabled = true
                                            settings.domStorageEnabled = true
                                            loadUrl(dailyQ.videoUrl)
                                        }
                                    },
                                    modifier = Modifier.fillMaxSize()
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = "${dailyQ.question} - Video Explanation",
                                color = TextPrimary,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            // Explanation Card
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(ObsidianBackground)
                                    .border(1.dp, Color(0xFF262634), RoundedCornerShape(10.dp))
                                    .padding(12.dp)
                            ) {
                                Column {
                                    Text(
                                        text = "📝 FULL THEORY & STEPS:",
                                        color = GoldSecondary,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = dailyQ.explanation,
                                        color = GoldTertiary,
                                        fontSize = 12.sp,
                                        lineHeight = 17.sp,
                                        fontFamily = FontFamily.Monospace
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = "• Step 1: Recall Trigonometric Standard Table\n• Step 2: Substitute sin(30°)=0.5, cos(60°)=0.5\n• Step 3: Compute sum = 1.0 (Exact Answer)",
                                        color = TextSecondary,
                                        fontSize = 11.sp,
                                        lineHeight = 16.sp
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                GoldenButton(
                                    text = "▶ Play 0.5x",
                                    isPrimary = false,
                                    onClick = {},
                                    modifier = Modifier.weight(1f)
                                )
                                GoldenButton(
                                    text = "📄 Get PDF Notes",
                                    isPrimary = true,
                                    onClick = {},
                                    modifier = Modifier.weight(1f)
                                )
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            // Related Videos
                            Text(
                                text = "📚 RELATED LESSONS • CLASS ${dailyQ.classLevel}",
                                color = GoldSecondary,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            listOf(
                                "Class ${dailyQ.classLevel} - Full Chapter Marathon" to "15:23",
                                "Tricks for Fast Trigonometric Solving" to "08:12",
                                "Previous Year Board Exam Questions" to "12:45"
                            ).forEach { (t, dur) ->
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(ObsidianSurfaceVariant)
                                        .padding(8.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Box(
                                                modifier = Modifier
                                                    .size(36.dp, 28.dp)
                                                    .clip(RoundedCornerShape(6.dp))
                                                    .background(Color(0xFF262634)),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text("▶", color = GoldPrimary, fontSize = 12.sp)
                                            }
                                            Spacer(modifier = Modifier.width(10.dp))
                                            Column {
                                                Text(text = t, color = TextPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                                Text(text = "Golden Sir • HD", color = TextMuted, fontSize = 9.sp)
                                            }
                                        }
                                        Text(text = dur, color = GoldSecondary, fontSize = 10.sp)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun PodiumItem(
    user: LeaderboardUser,
    isFirst: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .then(
                if (isFirst) Modifier.background(GoldGradient)
                else Modifier.background(ObsidianSurfaceVariant)
            )
            .border(
                1.5.dp,
                if (isFirst) GoldPrimary else Color(0xFF333344),
                RoundedCornerShape(12.dp)
            )
            .padding(vertical = if (isFirst) 14.dp else 10.dp, horizontal = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = user.avatar, fontSize = if (isFirst) 28.sp else 22.sp)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = user.name.substringBefore(" "),
            color = if (isFirst) ObsidianBackground else GoldPrimary,
            fontSize = 11.sp,
            fontWeight = FontWeight.ExtraBold
        )
        Text(
            text = "${user.score}",
            color = if (isFirst) Color(0xFF221A00) else TextSecondary,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(if (isFirst) ObsidianBackground else Color(0xFF1E1700))
                .padding(horizontal = 6.dp, vertical = 2.dp)
        ) {
            Text(
                text = user.badge,
                color = GoldPrimary,
                fontSize = 8.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}
