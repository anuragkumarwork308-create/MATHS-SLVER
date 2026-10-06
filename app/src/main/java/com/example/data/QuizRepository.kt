package com.example.data

import com.example.model.QuizQuestion

object QuizRepository {

    val classQuizzes: Map<Int, List<QuizQuestion>> = mapOf(
        1 to listOf(
            QuizQuestion("c1_1", 1, "5 + 3 = ?", listOf("6", "8", "9"), "8", "Count on fingers: 5 + 3 = 8 🖐️"),
            QuizQuestion("c1_2", 1, "2 + 2 = ?", listOf("3", "4", "5"), "4", "Two plus two equals four."),
            QuizQuestion("c1_3", 1, "Which shape has 3 sides?", listOf("Circle", "Triangle", "Square"), "Triangle", "A triangle has three sides and three corners.")
        ),
        2 to listOf(
            QuizQuestion("c2_1", 2, "23 + 15 = ?", listOf("38", "36", "40"), "38", "Add units: 3+5=8, add tens: 2+1=3 → 38"),
            QuizQuestion("c2_2", 2, "10 × 2 = ?", listOf("20", "12", "10"), "20", "Multiplying by 2 doubles the number: 10 + 10 = 20."),
            QuizQuestion("c2_3", 2, "50 - 20 = ?", listOf("30", "20", "40"), "30", "5 tens minus 2 tens = 3 tens = 30.")
        ),
        3 to listOf(
            QuizQuestion("c3_1", 3, "12 × 4 = ?", listOf("48", "36", "44"), "48", "12 × 4 = 48 (Multiplication table of 12)."),
            QuizQuestion("c3_2", 3, "100 ÷ 5 = ?", listOf("10", "20", "25"), "20", "100 divided into 5 equal parts gives 20."),
            QuizQuestion("c3_3", 3, "What is 1/2 of 10?", listOf("5", "2", "10"), "5", "Half of 10 is 10 ÷ 2 = 5.")
        ),
        4 to listOf(
            QuizQuestion("c4_1", 4, "Which are factors of 12?", listOf("1, 2, 3, 4, 6, 12", "1, 2, 12 only", "12 only"), "1, 2, 3, 4, 6, 12", "All numbers that divide 12 without remainder."),
            QuizQuestion("c4_2", 4, "Perimeter of square with side 5cm?", listOf("20cm", "25cm", "10cm"), "20cm", "Perimeter of square = 4 × side = 4 × 5 = 20cm."),
            QuizQuestion("c4_3", 4, "144 ÷ 12 = ?", listOf("12", "14", "10"), "12", "12 × 12 = 144.")
        ),
        5 to listOf(
            QuizQuestion("c5_1", 5, "What is the LCM of 12 and 18?", listOf("36", "18", "12"), "36", "Multiples of 12: 12, 24, 36. Multiples of 18: 18, 36. LCM = 36."),
            QuizQuestion("c5_2", 5, "Area of rectangle with Length=5, Breadth=4?", listOf("20", "9", "10"), "20", "Area = Length × Breadth = 5 × 4 = 20."),
            QuizQuestion("c5_3", 5, "0.5 + 0.5 = ?", listOf("1.0", "0.10", "0.55"), "1.0", "Adding two halves gives one whole.")
        ),
        6 to listOf(
            QuizQuestion("c6_1", 6, "If 2x + 5 = 15, then x = ?", listOf("5", "10", "7"), "5", "2x = 15 - 5 = 10  →  x = 10/2 = 5."),
            QuizQuestion("c6_2", 6, "Integer calculation: -2 + 5 = ?", listOf("3", "-7", "7"), "3", "Adding 5 to -2 results in +3."),
            QuizQuestion("c6_3", 6, "Simplify ratio 15 : 25", listOf("3 : 5", "5 : 3", "1 : 2"), "3 : 5", "Divide both terms by common divisor 5.")
        ),
        7 to listOf(
            QuizQuestion("c7_1", 7, "If 3x - 7 = 8, then x = ?", listOf("5", "3", "15"), "5", "3x = 8 + 7 = 15  →  x = 15/3 = 5."),
            QuizQuestion("c7_2", 7, "Profit of 20% on ₹100 is ₹?", listOf("20", "120", "80"), "20", "20% of 100 = (20/100) × 100 = ₹20."),
            QuizQuestion("c7_3", 7, "Sum of all three angles in a triangle?", listOf("180°", "360°", "90°"), "180°", "Angle sum property of triangle states the sum is always 180°.")
        ),
        8 to listOf(
            QuizQuestion("c8_1", 8, "Expand (a + b)² = ?", listOf("a² + 2ab + b²", "a² + b²", "a² - 2ab + b²"), "a² + 2ab + b²", "Standard algebraic identity: (a+b)² = a² + 2ab + b²."),
            QuizQuestion("c8_2", 8, "What is the cube of 3?", listOf("27", "9", "6"), "27", "3³ = 3 × 3 × 3 = 27."),
            QuizQuestion("c8_3", 8, "Square root of 144 is?", listOf("12", "14", "16"), "12", "12 × 12 = 144.")
        ),
        9 to listOf(
            QuizQuestion("c9_1", 9, "Roots of 2x² - 5x + 3 = 0?", listOf("1 and 1.5", "2 and 3", "0 and 1"), "1 and 1.5", "D = (-5)² - 4(2)(3) = 1. x = (5 ± 1)/4 = 6/4 = 1.5, and 4/4 = 1."),
            QuizQuestion("c9_2", 9, "Heron's Formula is used to calculate area of:", listOf("Triangle", "Circle", "Square"), "Triangle", "Heron's formula √[s(s-a)(s-b)(s-c)] calculates area of any triangle from its sides."),
            QuizQuestion("c9_3", 9, "Degree of polynomial 4x³ + 2x² - 7?", listOf("3", "2", "4"), "3", "The highest power of variable x is 3.")
        ),
        10 to listOf(
            QuizQuestion("c10_1", 10, "What is sin(30°)?", listOf("1/2", "1", "0"), "1/2", "Standard trigonometric value sin(30°) = 0.5 = 1/2."),
            QuizQuestion("c10_2", 10, "Discriminant of quadratic equation ax²+bx+c=0 is:", listOf("b² - 4ac", "b² + 4ac", "2a"), "b² - 4ac", "D = b² - 4ac determines root nature."),
            QuizQuestion("c10_3", 10, "Maximum probability of an event can be:", listOf("1", "0", "100"), "1", "Probability is bounded between 0 (impossible) and 1 (certain).")
        ),
        11 to listOf(
            QuizQuestion("c11_1", 11, "Derivative of sin(x) with respect to x?", listOf("cos(x)", "-cos(x)", "tan(x)"), "cos(x)", "d/dx [sin(x)] = cos(x)."),
            QuizQuestion("c11_2", 11, "Value of limit: lim x→0 [sin(x)/x]?", listOf("1", "0", "Undefined"), "1", "Standard limit via L'Hôpital rule / Sandwich theorem is 1.")
        ),
        12 to listOf(
            QuizQuestion("c12_1", 12, "Integral ∫ (1/x) dx = ?", listOf("ln|x| + C", "x² + C", "-1/x² + C"), "ln|x| + C", "Standard antiderivative of 1/x is natural logarithm ln|x| + C."),
            QuizQuestion("c12_2", 12, "If det(A) = 0 for a square matrix A, then A is:", listOf("Singular", "Invertible", "Identity"), "Singular", "A matrix with zero determinant has no inverse and is called singular.")
        )
    )

    fun getClassMetadata(level: Int): Triple<String, String, String> {
        return when (level) {
            1 -> Triple("Class 1", "🧸", "Counting, Basic Add/Sub, Shapes")
            2 -> Triple("Class 2", "🎈", "Tables 2-10, 2-Digit Math, Clock")
            3 -> Triple("Class 3", "📚", "Multiplication, Division, Fractions")
            4 -> Triple("Class 4", "✏️", "Factors, Perimeter, Large Numbers")
            5 -> Triple("Class 5", "🎯", "Decimals, LCM, HCF, Geometry")
            6 -> Triple("Class 6", "📐", "Algebra Basics, Integers, Ratios")
            7 -> Triple("Class 7", "🧮", "Simple Equations, Triangles, Profit")
            8 -> Triple("Class 8", "📊", "Linear Eq, Squares, Cubes, Identities")
            9 -> Triple("Class 9", "🔥", "Polynomials, Heron's, Coordinate")
            10 -> Triple("Class 10", "👑", "Quadratic, Trigonometry, Circles")
            11 -> Triple("Class 11", "⚡", "Derivatives, Limits, Conics")
            12 -> Triple("Class 12", "🏆", "Integrals, Matrices, Vectors")
            else -> Triple("Class $level", "📚", "Curriculum Math")
        }
    }
}
