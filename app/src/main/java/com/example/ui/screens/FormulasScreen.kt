package com.example.ui.screens

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.FormulaRepository
import com.example.model.FormulaItem
import com.example.ui.components.GoldGradient
import com.example.ui.components.GoldenButton
import com.example.ui.components.GoldenCard
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.GoldSecondary
import com.example.ui.theme.GoldTertiary
import com.example.ui.theme.ObsidianBackground
import com.example.ui.theme.ObsidianSurfaceVariant
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.VerifiedGreen

@Composable
fun FormulasScreen(
    onFormulaSelectedForSolver: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }
    var expandedFormulaId by remember { mutableStateOf<String?>("alg_1") }

    val categories = listOf("All", "Algebra", "Geometry", "Trigonometry", "Calculus", "Coordinate Geometry", "Commercial Math")

    // Topic quick keyword tags
    val topicKeywords = listOf(
        "Quadratic",
        "Circle Area",
        "Pythagoras",
        "Derivative",
        "Integration",
        "AP & GP",
        "Trigonometry",
        "Simple Interest",
        "Sphere Volume",
        "Probability",
        "Class 10",
        "Class 12"
    )

    // Multi-factor keyword filtering
    val filteredList = remember(searchQuery, selectedCategory) {
        val query = searchQuery.trim().lowercase()
        FormulaRepository.allFormulas.filter { item ->
            val matchesCat = selectedCategory == "All" || item.category.equals(selectedCategory, ignoreCase = true)
            val matchesQuery = query.isBlank() ||
                item.name.lowercase().contains(query) ||
                item.formula.lowercase().contains(query) ||
                item.category.lowercase().contains(query) ||
                item.classRange.lowercase().contains(query) ||
                item.explanation.lowercase().contains(query) ||
                item.keywords.any { kw -> kw.lowercase().contains(query) }
            matchesCat && matchesQuery
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ObsidianBackground)
            .padding(14.dp)
    ) {
        // Top Banner Title
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "📚 FORMULA BANK",
                    color = GoldPrimary,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "500+ Formulas • Class 1 to 12 • Instant Search",
                    color = TextSecondary,
                    fontSize = 11.sp
                )
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF221A00))
                    .border(1.dp, GoldSecondary, RoundedCornerShape(12.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "${filteredList.size} Formulas",
                    color = GoldPrimary,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // ADVANCED TOPIC SEARCH BAR
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(ObsidianSurfaceVariant)
                .border(1.5.dp, GoldSecondary, RoundedCornerShape(14.dp))
                .padding(horizontal = 12.dp, vertical = 2.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = GoldPrimary,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                TextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = {
                        Text(
                            text = "Search quadratic, pythagoras, derivative, class 10...",
                            color = TextMuted,
                            fontSize = 12.sp
                        )
                    },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("formula_search_input"),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    textStyle = androidx.compose.ui.text.TextStyle(fontSize = 14.sp)
                )
                if (searchQuery.isNotBlank()) {
                    IconButton(
                        onClick = { searchQuery = "" },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear",
                            tint = GoldSecondary,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // TOPIC KEYWORD QUICK CHIPS
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            topicKeywords.forEach { topic ->
                val isActive = searchQuery.equals(topic, ignoreCase = true)
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isActive) GoldPrimary else Color(0xFF14141E))
                        .border(
                            1.dp,
                            if (isActive) GoldPrimary else Color(0xFF262634),
                            RoundedCornerShape(12.dp)
                        )
                        .clickable {
                            searchQuery = if (isActive) "" else topic
                        }
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                        .testTag("topic_chip_$topic")
                ) {
                    Text(
                        text = "#$topic",
                        color = if (isActive) ObsidianBackground else GoldTertiary,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Category Filter Chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            categories.forEach { cat ->
                val isSelected = selectedCategory == cat
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(if (isSelected) GoldGradient else SolidColor(ObsidianSurfaceVariant))
                        .border(
                            1.dp,
                            if (isSelected) GoldPrimary else Color(0xFF2B2B38),
                            RoundedCornerShape(20.dp)
                        )
                        .clickable { selectedCategory = cat }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                        .testTag("category_chip_$cat"),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = cat,
                        color = if (isSelected) ObsidianBackground else GoldSecondary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Results Status Bar
        if (searchQuery.isNotBlank() || selectedCategory != "All") {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Found ${filteredList.size} formulas for \"${if (searchQuery.isNotBlank()) searchQuery else selectedCategory}\"",
                    color = GoldTertiary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "Reset ✕",
                    color = GoldSecondary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        searchQuery = ""
                        selectedCategory = "All"
                    }
                )
            }
        }

        // Formula List or Empty State
        if (filteredList.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(RoundedCornerShape(16.dp))
                    .background(ObsidianSurfaceVariant)
                    .border(1.dp, Color(0xFF262634), RoundedCornerShape(16.dp))
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "🔍", fontSize = 36.sp)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "No formulas found for \"$searchQuery\"",
                        color = GoldPrimary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Try searching for quadratic, circle, sin, derivative, or ap",
                        color = TextMuted,
                        fontSize = 11.sp,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    GoldenButton(
                        text = "Clear Search",
                        isPrimary = true,
                        onClick = {
                            searchQuery = ""
                            selectedCategory = "All"
                        }
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(filteredList) { formula ->
                    val isExpanded = expandedFormulaId == formula.id
                    FormulaCardItem(
                        formula = formula,
                        isExpanded = isExpanded,
                        onToggle = {
                            expandedFormulaId = if (isExpanded) null else formula.id
                        },
                        onSendToSolver = {
                            onFormulaSelectedForSolver(formula.formula)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun FormulaCardItem(
    formula: FormulaItem,
    isExpanded: Boolean,
    onToggle: () -> Unit,
    onSendToSolver: () -> Unit
) {
    val variableInputs = remember(formula.id) {
        mutableStateMapOf<String, String>().apply {
            formula.variables.forEach { v ->
                put(v.symbol, v.defaultValue)
            }
        }
    }
    var calcResult by remember(formula.id) { mutableStateOf<String?>(null) }

    GoldenCard {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onToggle() },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = formula.name,
                            color = GoldTertiary,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color(0xFF1E1700))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = formula.classRange,
                                color = GoldPrimary,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    // Formula Display
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(ObsidianBackground)
                            .border(1.dp, Color(0xFF242430), RoundedCornerShape(8.dp))
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = formula.formula,
                            color = GoldPrimary,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.ExtraBold,
                            fontFamily = FontFamily.Monospace
                        )
                    }

                    // Topic Keywords Badges
                    if (formula.keywords.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            formula.keywords.take(4).forEach { kw ->
                                Text(
                                    text = "#$kw",
                                    color = TextMuted,
                                    fontSize = 9.sp
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isExpanded) "▲" else "▼",
                    color = GoldSecondary,
                    fontSize = 14.sp
                )
            }

            AnimatedVisibility(visible = isExpanded) {
                Column(modifier = Modifier.padding(top = 12.dp)) {
                    Text(
                        text = formula.explanation,
                        color = TextSecondary,
                        fontSize = 12.sp,
                        lineHeight = 16.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Example: ${formula.example}",
                        color = TextMuted,
                        fontSize = 11.sp
                    )

                    // Interactive Calculation Inputs
                    if (formula.variables.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Interactive Calculator (Enter values):",
                            color = GoldSecondary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(6.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            formula.variables.forEach { variable ->
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(text = variable.label, color = TextMuted, fontSize = 10.sp)
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(ObsidianBackground)
                                            .border(1.dp, GoldSecondary.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                                            .padding(horizontal = 8.dp, vertical = 4.dp)
                                    ) {
                                        TextField(
                                            value = variableInputs[variable.symbol] ?: variable.defaultValue,
                                            onValueChange = { variableInputs[variable.symbol] = it },
                                            colors = TextFieldDefaults.colors(
                                                focusedContainerColor = Color.Transparent,
                                                unfocusedContainerColor = Color.Transparent,
                                                focusedTextColor = GoldPrimary,
                                                unfocusedTextColor = GoldPrimary,
                                                focusedIndicatorColor = Color.Transparent,
                                                unfocusedIndicatorColor = Color.Transparent
                                            ),
                                            textStyle = androidx.compose.ui.text.TextStyle(fontSize = 13.sp, fontWeight = FontWeight.Bold)
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            GoldenButton(
                                text = "Calculate Live",
                                icon = "⚡",
                                isPrimary = true,
                                onClick = {
                                    val mapped = variableInputs.mapValues { it.value.toDoubleOrNull() ?: 0.0 }
                                    calcResult = FormulaRepository.calculateFormula(formula, mapped)
                                },
                                modifier = Modifier.weight(1f)
                            )
                        }

                        if (calcResult != null) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0xFF141914))
                                    .border(1.dp, VerifiedGreen.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                                    .padding(10.dp)
                            ) {
                                Text(
                                    text = calcResult ?: "",
                                    color = VerifiedGreen,
                                    fontSize = 12.sp,
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
