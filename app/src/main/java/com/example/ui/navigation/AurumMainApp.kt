package com.example.ui.navigation

import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
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
import com.example.ui.components.AurumHeader
import com.example.ui.components.GoldGradient
import com.example.ui.screens.DailyChallengeScreen
import com.example.ui.screens.FormulasScreen
import com.example.ui.screens.GraphScreen
import com.example.ui.screens.PhotoSolverScreen
import com.example.ui.screens.QuizScreen
import com.example.ui.screens.SolverScreen
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.GoldSecondary
import com.example.ui.theme.ObsidianBackground
import com.example.ui.theme.ObsidianSurface
import com.example.ui.theme.ObsidianSurfaceVariant
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import kotlinx.coroutines.launch

enum class ScreenTab {
    SOLVE,
    SCAN,
    GRAPH,
    FORMULAS,
    QUIZ,
    CHALLENGE
}

@Composable
fun AurumMainApp() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    var currentTab by remember { mutableStateOf(ScreenTab.SOLVE) }
    var solverInitialProblem by remember { mutableStateOf("2x² + 5x - 12 = 0") }
    var quizTargetClass by remember { mutableIntStateOf(10) }
    var drawerQuizExpanded by remember { mutableStateOf(true) }
    var userCoins by remember { mutableIntStateOf(8400) }
    val streakDays = 7

    BackHandler(enabled = drawerState.isOpen || currentTab != ScreenTab.SOLVE) {
        if (drawerState.isOpen) {
            coroutineScope.launch { drawerState.close() }
        } else {
            currentTab = ScreenTab.SOLVE
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = ObsidianBackground,
                modifier = Modifier
                    .width(300.dp)
                    .border(
                        1.dp,
                        GoldSecondary.copy(alpha = 0.5f),
                        RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp)
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(ObsidianBackground)
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    // Drawer Header
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(GoldGradient),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Σ",
                                    color = ObsidianBackground,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    fontFamily = FontFamily.Serif
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "AURUM PRO MAX",
                                    color = GoldPrimary,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    fontFamily = FontFamily.Serif
                                )
                                Text(
                                    text = "PREMIUM GOLD • CLASS 1-12",
                                    color = GoldSecondary,
                                    fontSize = 8.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.sp
                                )
                            }
                        }

                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = GoldSecondary,
                            modifier = Modifier
                                .size(22.dp)
                                .clickable { coroutineScope.launch { drawerState.close() } }
                        )
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    Text(
                        text = "AURUM PRO SPECIALS",
                        color = GoldSecondary,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    DrawerMenuItem(icon = "🔥", title = "Daily Challenge (Live)", selected = currentTab == ScreenTab.CHALLENGE) {
                        currentTab = ScreenTab.CHALLENGE
                        coroutineScope.launch { drawerState.close() }
                    }
                    DrawerMenuItem(icon = "🏆", title = "Leaderboard Top 10", selected = currentTab == ScreenTab.CHALLENGE) {
                        currentTab = ScreenTab.CHALLENGE
                        coroutineScope.launch { drawerState.close() }
                    }
                    DrawerMenuItem(icon = "🎬", title = "Video Solution & Theory", selected = currentTab == ScreenTab.CHALLENGE) {
                        currentTab = ScreenTab.CHALLENGE
                        coroutineScope.launch { drawerState.close() }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "TOOLS & ENGINE",
                        color = TextMuted,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    DrawerMenuItem(icon = "💡", title = "Math Solver (Step-by-Step)", selected = currentTab == ScreenTab.SOLVE) {
                        currentTab = ScreenTab.SOLVE
                        coroutineScope.launch { drawerState.close() }
                    }
                    DrawerMenuItem(icon = "📷", title = "Photo Solver (HD Camera)", selected = currentTab == ScreenTab.SCAN) {
                        currentTab = ScreenTab.SCAN
                        coroutineScope.launch { drawerState.close() }
                    }
                    DrawerMenuItem(icon = "📈", title = "2D Function Grapher", selected = currentTab == ScreenTab.GRAPH) {
                        currentTab = ScreenTab.GRAPH
                        coroutineScope.launch { drawerState.close() }
                    }
                    DrawerMenuItem(icon = "📚", title = "Formula Bank (500+ Items)", selected = currentTab == ScreenTab.FORMULAS) {
                        currentTab = ScreenTab.FORMULAS
                        coroutineScope.launch { drawerState.close() }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Expandable Quiz Menu
                    Text(
                        text = "QUIZ SECTION • CLASS WISE",
                        color = GoldSecondary,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(GoldGradient)
                            .clickable { drawerQuizExpanded = !drawerQuizExpanded }
                            .padding(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("🧠", fontSize = 16.sp)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "QUIZ • Class Section",
                                    color = ObsidianBackground,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.ExtraBold
                                )
                            }
                            Text(
                                text = if (drawerQuizExpanded) "▼" else "▶",
                                color = ObsidianBackground,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    AnimatedVisibility(visible = drawerQuizExpanded) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 6.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color(0xFF0C0C12))
                                .border(1.dp, GoldSecondary.copy(alpha = 0.4f), RoundedCornerShape(10.dp))
                        ) {
                            (1..10).forEach { c ->
                                val meta = QuizRepository.getClassMetadata(c)
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            quizTargetClass = c
                                            currentTab = ScreenTab.QUIZ
                                            coroutineScope.launch { drawerState.close() }
                                        }
                                        .padding(horizontal = 12.dp, vertical = 9.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(text = meta.second, fontSize = 14.sp)
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = "Class $c Quiz",
                                            color = if (c == 10) GoldPrimary else TextPrimary,
                                            fontSize = 12.sp,
                                            fontWeight = if (c == 10) FontWeight.Bold else FontWeight.Normal
                                        )
                                    }
                                    Text(text = "›", color = GoldSecondary, fontSize = 14.sp)
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "CLASS SECTION GRID",
                        color = TextMuted,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Class buttons grid
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        for (rowList in (1..10).toList().chunked(5)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                rowList.forEach { c ->
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(ObsidianSurfaceVariant)
                                            .border(1.dp, Color(0xFF262634), RoundedCornerShape(8.dp))
                                            .clickable {
                                                quizTargetClass = c
                                                currentTab = ScreenTab.QUIZ
                                                coroutineScope.launch { drawerState.close() }
                                            }
                                            .padding(vertical = 8.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "$c",
                                            color = GoldPrimary,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Gold Tier Box
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF161205))
                            .border(1.dp, GoldSecondary, RoundedCornerShape(12.dp))
                            .padding(12.dp)
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("👑", fontSize = 16.sp)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "GOLDEN PREMIUM",
                                    color = GoldPrimary,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Unlimited Step-by-Step, 4K Camera Scanner & Offline Formulas Unlocked",
                                color = TextMuted,
                                fontSize = 10.sp,
                                lineHeight = 14.sp
                            )
                        }
                    }
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                AurumHeader(
                    onMenuClick = {
                        coroutineScope.launch {
                            if (drawerState.isOpen) drawerState.close() else drawerState.open()
                        }
                    },
                    streak = streakDays,
                    coins = userCoins,
                    onCoinsClick = {
                        currentTab = ScreenTab.CHALLENGE
                    }
                )
            },
            bottomBar = {
                AurumBottomBar(
                    currentTab = currentTab,
                    onTabSelected = { currentTab = it }
                )
            },
            containerColor = ObsidianBackground
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when (currentTab) {
                    ScreenTab.SOLVE -> {
                        SolverScreen(
                            initialInput = solverInitialProblem,
                            onCameraClick = { currentTab = ScreenTab.SCAN }
                        )
                    }
                    ScreenTab.SCAN -> {
                        PhotoSolverScreen(
                            onNavigateToSolverWithProblem = { prob ->
                                solverInitialProblem = prob
                                currentTab = ScreenTab.SOLVE
                            }
                        )
                    }
                    ScreenTab.GRAPH -> {
                        GraphScreen()
                    }
                    ScreenTab.FORMULAS -> {
                        FormulasScreen(
                            onFormulaSelectedForSolver = { form ->
                                solverInitialProblem = form
                                currentTab = ScreenTab.SOLVE
                            }
                        )
                    }
                    ScreenTab.QUIZ -> {
                        QuizScreen(initialClass = quizTargetClass)
                    }
                    ScreenTab.CHALLENGE -> {
                        DailyChallengeScreen(
                            userScore = userCoins - 8400,
                            onScoreEarned = { earned -> userCoins += earned }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DrawerMenuItem(
    icon: String,
    title: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(if (selected) Color(0xFF1E1700) else ObsidianSurfaceVariant)
            .border(
                1.dp,
                if (selected) GoldSecondary else Color(0xFF232330),
                RoundedCornerShape(10.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 10.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = icon, fontSize = 16.sp)
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = title,
                color = if (selected) GoldPrimary else TextPrimary,
                fontSize = 12.sp,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium
            )
        }
    }
}

@Composable
fun AurumBottomBar(
    currentTab: ScreenTab,
    onTabSelected: (ScreenTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = ObsidianSurface,
        modifier = modifier
            .fillMaxWidth()
            .border(
                1.dp,
                GoldSecondary.copy(alpha = 0.4f),
                RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavItem(
                icon = "💡",
                label = "Solve",
                isSelected = currentTab == ScreenTab.SOLVE,
                onClick = { onTabSelected(ScreenTab.SOLVE) },
                testTag = "nav_solve"
            )

            BottomNavItem(
                icon = "🔥",
                label = "Daily",
                isSelected = currentTab == ScreenTab.CHALLENGE,
                onClick = { onTabSelected(ScreenTab.CHALLENGE) },
                testTag = "nav_daily"
            )

            // Prominent Gold Center FAB Button for Camera Scan
            Box(
                modifier = Modifier
                    .offset(y = (-10).dp)
                    .size(54.dp)
                    .clip(CircleShape)
                    .background(GoldGradient)
                    .border(3.dp, ObsidianBackground, CircleShape)
                    .clickable { onTabSelected(ScreenTab.SCAN) }
                    .testTag("nav_scan_fab"),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "📷",
                    fontSize = 24.sp
                )
            }

            BottomNavItem(
                icon = "📚",
                label = "Formulas",
                isSelected = currentTab == ScreenTab.FORMULAS,
                onClick = { onTabSelected(ScreenTab.FORMULAS) },
                testTag = "nav_formulas"
            )

            BottomNavItem(
                icon = "🧠",
                label = "Quiz",
                isSelected = currentTab == ScreenTab.QUIZ,
                onClick = { onTabSelected(ScreenTab.QUIZ) },
                testTag = "nav_quiz"
            )
        }
    }
}

@Composable
private fun BottomNavItem(
    icon: String,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    testTag: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onClick() }
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .testTag(testTag)
    ) {
        Text(
            text = icon,
            fontSize = 17.sp
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = label,
            color = if (isSelected) GoldPrimary else TextMuted,
            fontSize = 10.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}
