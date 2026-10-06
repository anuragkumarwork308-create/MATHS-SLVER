package com.example.model

data class SolutionStep(
    val stepNumber: Int,
    val title: String,
    val mathExpression: String,
    val explanation: String,
    val isFinal: Boolean = false,
    val note: String? = null
)

data class SolveResult(
    val problem: String,
    val category: String,
    val finalAnswer: String,
    val steps: List<SolutionStep>,
    val verified: Boolean = true,
    val rootsOrValues: List<String> = emptyList(),
    val graphEquation: String? = null,
    val graphPoints: List<Pair<Float, Float>> = emptyList()
)

data class FormulaItem(
    val id: String,
    val name: String,
    val formula: String,
    val category: String,
    val classRange: String, // e.g. "Class 10", "Class 6-8", "Class 11-12"
    val explanation: String,
    val example: String,
    val variables: List<FormulaVariable> = emptyList(),
    val keywords: List<String> = emptyList()
)

data class FormulaVariable(
    val symbol: String,
    val label: String,
    val defaultValue: String
)

data class QuizQuestion(
    val id: String,
    val classLevel: Int,
    val question: String,
    val options: List<String>,
    val answer: String,
    val explanation: String
)

data class RecentHistoryItem(
    val id: String = System.currentTimeMillis().toString(),
    val problem: String,
    val answer: String,
    val category: String,
    val timestamp: String
)
