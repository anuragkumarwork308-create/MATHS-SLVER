package com.example

import com.example.engine.MathSolverEngine
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun testQuadraticSolver() {
        val res = MathSolverEngine.solve("2x² + 5x - 12 = 0")
        assertEquals("Quadratic Equation", res.category)
        assertTrue(res.finalAnswer.contains("3/2"))
        assertTrue(res.finalAnswer.contains("-4"))
        assertTrue(res.steps.size >= 4)
    }

    @Test
    fun testLinearSolver() {
        val res = MathSolverEngine.solve("2x + 5 = 15")
        assertEquals("Linear Equation", res.category)
        assertEquals("x = 5", res.finalAnswer)
    }

    @Test
    fun testDerivativeSolver() {
        val res = MathSolverEngine.solve("d/dx sin(x)")
        assertEquals("Calculus - Derivative", res.category)
        assertEquals("cos(x)", res.finalAnswer)
    }
}
