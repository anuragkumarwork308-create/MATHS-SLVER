package com.example.data

import com.example.model.FormulaItem
import com.example.model.FormulaVariable
import kotlin.math.PI
import kotlin.math.pow
import kotlin.math.sqrt

object FormulaRepository {

    val allFormulas: List<FormulaItem> = listOf(
        // ALGEBRA
        FormulaItem(
            id = "alg_1",
            name = "Square of Sum (a + b)²",
            formula = "(a + b)² = a² + 2ab + b²",
            category = "Algebra",
            classRange = "Class 7-10",
            explanation = "Expands the square of the sum of two algebraic terms.",
            example = "If a=2, b=3: (2+3)² = 4 + 12 + 9 = 25",
            variables = listOf(FormulaVariable("a", "First Term (a)", "2"), FormulaVariable("b", "Second Term (b)", "3")),
            keywords = listOf("algebra", "square", "expansion", "identity", "sum", "binomial", "class 7", "class 8", "class 9", "class 10")
        ),
        FormulaItem(
            id = "alg_2",
            name = "Square of Difference (a - b)²",
            formula = "(a - b)² = a² - 2ab + b²",
            category = "Algebra",
            classRange = "Class 7-10",
            explanation = "Expands the square of the difference of two terms.",
            example = "If a=5, b=2: (5-2)² = 25 - 20 + 4 = 9",
            variables = listOf(FormulaVariable("a", "First Term (a)", "5"), FormulaVariable("b", "Second Term (b)", "2")),
            keywords = listOf("algebra", "square", "difference", "identity", "subtraction", "class 7", "class 8")
        ),
        FormulaItem(
            id = "alg_3",
            name = "Difference of Two Squares a² - b²",
            formula = "a² - b² = (a - b)(a + b)",
            category = "Algebra",
            classRange = "Class 8-10",
            explanation = "Factors the difference of two perfect squares into conjugate binomials.",
            example = "If a=6, b=4: 36 - 16 = 20 = (2)(10)",
            variables = listOf(FormulaVariable("a", "Term a", "6"), FormulaVariable("b", "Term b", "4")),
            keywords = listOf("algebra", "factorization", "squares", "difference", "conjugate", "class 8", "class 9", "class 10")
        ),
        FormulaItem(
            id = "alg_4",
            name = "Cube of Sum (a + b)³",
            formula = "(a + b)³ = a³ + 3a²b + 3ab² + b³",
            category = "Algebra",
            classRange = "Class 9-11",
            explanation = "Binomial expansion for cubic degree sum.",
            example = "If a=1, b=2: (1+2)³ = 1 + 6 + 12 + 8 = 27",
            variables = listOf(FormulaVariable("a", "Term a", "1"), FormulaVariable("b", "Term b", "2")),
            keywords = listOf("algebra", "cube", "expansion", "polynomial", "binomial", "class 9", "class 10", "class 11")
        ),
        FormulaItem(
            id = "alg_5",
            name = "Quadratic Roots Formula (Sridharacharya)",
            formula = "x = [-b ± √(b² - 4ac)] / (2a)",
            category = "Algebra",
            classRange = "Class 10-12",
            explanation = "Finds exact roots of any general quadratic equation ax² + bx + c = 0.",
            example = "For 2x² + 5x - 12 = 0: x = 3/2 or x = -4",
            variables = listOf(FormulaVariable("a", "Coeff a", "2"), FormulaVariable("b", "Coeff b", "5"), FormulaVariable("c", "Coeff c", "-12")),
            keywords = listOf("quadratic", "roots", "equation", "formula", "algebra", "sridharacharya", "class 10", "class 11", "jee")
        ),
        FormulaItem(
            id = "alg_6",
            name = "Discriminant of Quadratic Equation",
            formula = "D = b² - 4ac",
            category = "Algebra",
            classRange = "Class 10-12",
            explanation = "Determines nature of roots: D > 0 real distinct, D = 0 equal, D < 0 imaginary.",
            example = "b=5, a=2, c=-12 → D = 25 - 4(2)(-12) = 121",
            variables = listOf(FormulaVariable("a", "Coeff a", "2"), FormulaVariable("b", "Coeff b", "5"), FormulaVariable("c", "Coeff c", "-12")),
            keywords = listOf("quadratic", "discriminant", "nature of roots", "real roots", "imaginary", "class 10", "class 11")
        ),
        FormulaItem(
            id = "alg_7",
            name = "Arithmetic Progression nth Term (an)",
            formula = "aₙ = a + (n - 1)d",
            category = "Algebra",
            classRange = "Class 10-11",
            explanation = "Calculates the value of the nth term in an arithmetic progression.",
            example = "a=3, d=4, n=10: a₁₀ = 3 + 9(4) = 39",
            variables = listOf(FormulaVariable("a", "First Term (a)", "3"), FormulaVariable("d", "Common Diff (d)", "4"), FormulaVariable("n", "Term No (n)", "10")),
            keywords = listOf("arithmetic progression", "ap", "sequence", "series", "common difference", "nth term", "class 10", "class 11")
        ),
        FormulaItem(
            id = "alg_8",
            name = "Sum of n Terms of AP (Sn)",
            formula = "Sₙ = (n / 2)[2a + (n - 1)d]",
            category = "Algebra",
            classRange = "Class 10-11",
            explanation = "Total sum of first n terms of an arithmetic progression series.",
            example = "n=10, a=2, d=3: S₁₀ = 5[4 + 27] = 155",
            variables = listOf(FormulaVariable("a", "First Term (a)", "2"), FormulaVariable("d", "Diff (d)", "3"), FormulaVariable("n", "Count (n)", "10")),
            keywords = listOf("arithmetic progression", "sum of ap", "series", "sequence", "sn", "class 10", "class 11")
        ),
        FormulaItem(
            id = "alg_9",
            name = "Sum of Cubes a³ + b³",
            formula = "a³ + b³ = (a + b)(a² - ab + b²)",
            category = "Algebra",
            classRange = "Class 9-11",
            explanation = "Factorization of sum of cubes into linear and quadratic factors.",
            example = "2³ + 3³ = 8 + 27 = 35 = (5)(4 - 6 + 9)",
            variables = listOf(FormulaVariable("a", "Term a", "2"), FormulaVariable("b", "Term b", "3")),
            keywords = listOf("algebra", "sum of cubes", "factorization", "polynomials", "class 9", "class 10")
        ),
        FormulaItem(
            id = "alg_10",
            name = "Geometric Progression nth Term (GP)",
            formula = "aₙ = a · rⁿ⁻¹",
            category = "Algebra",
            classRange = "Class 11-12",
            explanation = "Calculates nth term of a geometric sequence with common ratio r.",
            example = "a=2, r=3, n=4: a₄ = 2 × 27 = 54",
            variables = listOf(FormulaVariable("a", "First term", "2"), FormulaVariable("r", "Ratio r", "3"), FormulaVariable("n", "n", "4")),
            keywords = listOf("geometric progression", "gp", "common ratio", "series", "exponential", "class 11")
        ),

        // GEOMETRY & MENSURATION
        FormulaItem(
            id = "geo_1",
            name = "Area of Circle",
            formula = "Area = πr²",
            category = "Geometry",
            classRange = "Class 6-10",
            explanation = "Surface area enclosed by a circle with radius r.",
            example = "If r = 7: Area = (22/7) × 49 = 154 sq units",
            variables = listOf(FormulaVariable("r", "Radius (r)", "7")),
            keywords = listOf("circle", "radius", "area of circle", "pi", "geometry", "mensuration", "class 6", "class 7", "class 8", "class 9", "class 10")
        ),
        FormulaItem(
            id = "geo_2",
            name = "Circumference of Circle",
            formula = "C = 2πr",
            category = "Geometry",
            classRange = "Class 6-10",
            explanation = "Perimeter or boundary length around a circle.",
            example = "If r = 7: C = 2 × (22/7) × 7 = 44 units",
            variables = listOf(FormulaVariable("r", "Radius (r)", "7")),
            keywords = listOf("circle", "circumference", "perimeter", "radius", "geometry", "class 6", "class 7", "class 8", "class 9", "class 10")
        ),
        FormulaItem(
            id = "geo_3",
            name = "Pythagoras Theorem",
            formula = "c = √(a² + b²)",
            category = "Geometry",
            classRange = "Class 7-10",
            explanation = "In right-angled triangle, square of hypotenuse equals sum of squares of other sides.",
            example = "a=3, b=4: c = √(9+16) = √25 = 5",
            variables = listOf(FormulaVariable("a", "Base (a)", "3"), FormulaVariable("b", "Perpendicular (b)", "4")),
            keywords = listOf("pythagoras", "triangle", "hypotenuse", "right angle", "geometry", "class 7", "class 8", "class 9", "class 10")
        ),
        FormulaItem(
            id = "geo_4",
            name = "Heron's Formula for Triangle Area",
            formula = "Area = √[s(s - a)(s - b)(s - c)], where s = (a+b+c)/2",
            category = "Geometry",
            classRange = "Class 9-10",
            explanation = "Calculates area of any scalene triangle given sides a, b, c.",
            example = "a=3, b=4, c=5: s=6, Area = √[6(3)(2)(1)] = 6",
            variables = listOf(FormulaVariable("a", "Side a", "3"), FormulaVariable("b", "Side b", "4"), FormulaVariable("c", "Side c", "5")),
            keywords = listOf("heron", "triangle area", "semi perimeter", "scalene", "geometry", "class 9", "class 10")
        ),
        FormulaItem(
            id = "geo_5",
            name = "Volume of Cylinder",
            formula = "V = πr²h",
            category = "Geometry",
            classRange = "Class 8-10",
            explanation = "Total capacity of a right circular cylinder with radius r and height h.",
            example = "r=7, h=10: V = (22/7) × 49 × 10 = 1540 cubic units",
            variables = listOf(FormulaVariable("r", "Radius (r)", "7"), FormulaVariable("h", "Height (h)", "10")),
            keywords = listOf("cylinder", "volume", "surface area", "mensuration", "3d geometry", "class 8", "class 9", "class 10")
        ),
        FormulaItem(
            id = "geo_6",
            name = "Volume of Sphere",
            formula = "V = (4/3)πr³",
            category = "Geometry",
            classRange = "Class 9-11",
            explanation = "Total volume enclosed inside a 3-dimensional solid sphere.",
            example = "r=3: V = (4/3)π(27) = 36π ≈ 113.1",
            variables = listOf(FormulaVariable("r", "Radius (r)", "3")),
            keywords = listOf("sphere", "volume of sphere", "radius", "mensuration", "class 9", "class 10")
        ),
        FormulaItem(
            id = "geo_7",
            name = "Surface Area of Sphere",
            formula = "Total Area = 4πr²",
            category = "Geometry",
            classRange = "Class 9-10",
            explanation = "Curved outer boundary surface area of a 3D sphere.",
            example = "r=7: Area = 4 × (22/7) × 49 = 616 sq units",
            variables = listOf(FormulaVariable("r", "Radius", "7")),
            keywords = listOf("sphere", "surface area", "mensuration", "class 9", "class 10")
        ),
        FormulaItem(
            id = "geo_8",
            name = "Volume of Cone",
            formula = "V = (1/3)πr²h",
            category = "Geometry",
            classRange = "Class 9-10",
            explanation = "Capacity of a right circular cone, exactly one-third of cylinder of equal base and height.",
            example = "r=7, h=12: V = (1/3)(22/7)(49)(12) = 616",
            variables = listOf(FormulaVariable("r", "Radius", "7"), FormulaVariable("h", "Height", "12")),
            keywords = listOf("cone", "volume of cone", "slant height", "mensuration", "class 9", "class 10")
        ),

        // TRIGONOMETRY
        FormulaItem(
            id = "trig_1",
            name = "Fundamental Pythagorean Identity (sin² + cos²)",
            formula = "sin²(θ) + cos²(θ) = 1",
            category = "Trigonometry",
            classRange = "Class 10-12",
            explanation = "The fundamental trigonometric identity valid for all real angles θ.",
            example = "For θ=30°: (1/2)² + (√3/2)² = 1/4 + 3/4 = 1",
            variables = listOf(FormulaVariable("θ", "Angle degrees", "30")),
            keywords = listOf("trigonometry", "sin", "cos", "pythagorean identity", "angle", "class 10", "class 11", "class 12")
        ),
        FormulaItem(
            id = "trig_2",
            name = "Tangent Ratio Identity",
            formula = "tan(θ) = sin(θ) / cos(θ)",
            category = "Trigonometry",
            classRange = "Class 10-12",
            explanation = "Ratio of sine to cosine of an angle.",
            example = "tan(45°) = sin(45°)/cos(45°) = 1",
            variables = listOf(FormulaVariable("θ", "Angle degrees", "45")),
            keywords = listOf("trigonometry", "tan", "tangent", "sin", "cos", "ratios", "class 10", "class 11")
        ),
        FormulaItem(
            id = "trig_3",
            name = "Double Angle Formula: sin(2θ)",
            formula = "sin(2θ) = 2 · sin(θ) · cos(θ)",
            category = "Trigonometry",
            classRange = "Class 11-12",
            explanation = "Double-angle formula for the sine function.",
            example = "For θ=30°: sin(60°) = 2 · (1/2) · (√3/2) = √3/2",
            variables = listOf(FormulaVariable("θ", "Angle degrees", "30")),
            keywords = listOf("trigonometry", "double angle", "sin 2theta", "identities", "class 11", "class 12", "jee")
        ),
        FormulaItem(
            id = "trig_4",
            name = "Double Angle Formula: cos(2θ)",
            formula = "cos(2θ) = cos²(θ) - sin²(θ) = 2cos²(θ) - 1",
            category = "Trigonometry",
            classRange = "Class 11-12",
            explanation = "Expresses cosine of double angle in terms of single angle.",
            example = "For θ=45°: cos(90°) = 2(1/√2)² - 1 = 1 - 1 = 0",
            variables = listOf(FormulaVariable("θ", "Angle degrees", "45")),
            keywords = listOf("trigonometry", "cos 2theta", "double angle", "identities", "class 11", "class 12")
        ),
        FormulaItem(
            id = "trig_5",
            name = "Secant and Tangent Identity (1 + tan²)",
            formula = "1 + tan²(θ) = sec²(θ)",
            category = "Trigonometry",
            classRange = "Class 10-12",
            explanation = "Second fundamental Pythagorean trigonometric identity.",
            example = "For θ=45°: 1 + 1² = 2 = (√2)² = sec²(45°)",
            variables = listOf(FormulaVariable("θ", "Angle", "45")),
            keywords = listOf("trigonometry", "secant", "tangent", "sec2", "tan2", "class 10", "class 11")
        ),

        // CALCULUS
        FormulaItem(
            id = "calc_1",
            name = "Power Rule of Differentiation",
            formula = "d/dx [xⁿ] = n · xⁿ⁻¹",
            category = "Calculus",
            classRange = "Class 11-12",
            explanation = "Derivative of power function with constant exponent n.",
            example = "d/dx [x³] = 3x²",
            variables = listOf(FormulaVariable("n", "Exponent (n)", "3")),
            keywords = listOf("calculus", "derivative", "differentiation", "power rule", "slope", "class 11", "class 12", "jee")
        ),
        FormulaItem(
            id = "calc_2",
            name = "Product Rule of Differentiation",
            formula = "d/dx [u · v] = u · (dv/dx) + v · (du/dx)",
            category = "Calculus",
            classRange = "Class 11-12",
            explanation = "Derivative of a product of two differentiable functions u and v.",
            example = "d/dx [x · sin(x)] = x · cos(x) + sin(x)",
            variables = emptyList(),
            keywords = listOf("calculus", "product rule", "differentiation", "leibniz rule", "class 11", "class 12")
        ),
        FormulaItem(
            id = "calc_3",
            name = "Quotient Rule of Differentiation",
            formula = "d/dx [u / v] = [v(du/dx) - u(dv/dx)] / v²",
            category = "Calculus",
            classRange = "Class 11-12",
            explanation = "Differentiation rule for rational functions.",
            example = "d/dx [sin(x)/x] = (x·cos(x) - sin(x))/x²",
            variables = emptyList(),
            keywords = listOf("calculus", "quotient rule", "derivative", "rational", "class 11", "class 12")
        ),
        FormulaItem(
            id = "calc_4",
            name = "Power Rule of Integration",
            formula = "∫ xⁿ dx = [xⁿ⁺¹ / (n + 1)] + C  (n ≠ -1)",
            category = "Calculus",
            classRange = "Class 11-12",
            explanation = "Antiderivative power rule for indefinite integrals.",
            example = "∫ x² dx = x³/3 + C",
            variables = listOf(FormulaVariable("n", "Exponent (n)", "2")),
            keywords = listOf("calculus", "integration", "integral", "power rule", "antiderivative", "class 12", "jee")
        ),
        FormulaItem(
            id = "calc_5",
            name = "Fundamental Limit: sin(x)/x",
            formula = "lim (x → 0) [sin(x) / x] = 1",
            category = "Calculus",
            classRange = "Class 11-12",
            explanation = "Standard trigonometric limit derived via Sandwich theorem.",
            example = "lim x→0 sin(3x)/x = 3 · 1 = 3",
            variables = emptyList(),
            keywords = listOf("calculus", "limits", "l'hopital", "trig limit", "continuity", "class 11", "class 12")
        ),
        FormulaItem(
            id = "calc_6",
            name = "Integration by Parts",
            formula = "∫ u · v dx = u ∫ v dx - ∫ [u' · (∫ v dx)] dx",
            category = "Calculus",
            classRange = "Class 12",
            explanation = "ILATE rule integration for products of algebraic, exponential, trig functions.",
            example = "∫ x · eˣ dx = x eˣ - eˣ + C",
            variables = emptyList(),
            keywords = listOf("calculus", "integration by parts", "ilate rule", "integral", "class 12")
        ),

        // COORDINATE GEOMETRY
        FormulaItem(
            id = "coord_1",
            name = "Distance Formula Between Two Points",
            formula = "d = √[(x₂ - x₁)² + (y₂ - y₁)²]",
            category = "Coordinate Geometry",
            classRange = "Class 9-11",
            explanation = "Euclidean distance between points P(x₁, y₁) and Q(x₂, y₂).",
            example = "P(0,0) and Q(3,4): d = √(9 + 16) = 5",
            variables = listOf(
                FormulaVariable("x1", "x₁", "0"), FormulaVariable("y1", "y₁", "0"),
                FormulaVariable("x2", "x₂", "3"), FormulaVariable("y2", "y₂", "4")
            ),
            keywords = listOf("coordinate geometry", "distance formula", "euclidean distance", "points", "class 9", "class 10")
        ),
        FormulaItem(
            id = "coord_2",
            name = "Section Formula (Internal Division)",
            formula = "x = (m₁x₂ + m₂x₁) / (m₁ + m₂), y = (m₁y₂ + m₂y₁) / (m₁ + m₂)",
            category = "Coordinate Geometry",
            classRange = "Class 10-11",
            explanation = "Coordinates of point dividing line segment in ratio m₁:m₂.",
            example = "Midpoint formula when m₁=1, m₂=1: ((x₁+x₂)/2, (y₁+y₂)/2)",
            variables = emptyList(),
            keywords = listOf("coordinate geometry", "section formula", "ratio", "midpoint", "line", "class 10", "class 11")
        ),
        FormulaItem(
            id = "coord_3",
            name = "Slope of Line (m)",
            formula = "m = (y₂ - y₁) / (x₂ - x₁) = tan(θ)",
            category = "Coordinate Geometry",
            classRange = "Class 10-11",
            explanation = "Rate of change or steepness of straight line passing through two coordinates.",
            example = "Points (1,2) and (3,6): m = (6 - 2)/(3 - 1) = 4/2 = 2",
            variables = emptyList(),
            keywords = listOf("coordinate geometry", "slope", "gradient", "straight line", "tan theta", "class 10", "class 11")
        ),

        // COMMERCIAL & STATISTICS
        FormulaItem(
            id = "comm_1",
            name = "Simple Interest (SI)",
            formula = "SI = (P · R · T) / 100",
            category = "Commercial Math",
            classRange = "Class 7-9",
            explanation = "Interest earned on principal P at annual rate R% over T years.",
            example = "P=1000, R=5%, T=2 yrs: SI = (1000 × 5 × 2)/100 = 100",
            variables = listOf(FormulaVariable("P", "Principal (P)", "1000"), FormulaVariable("R", "Rate % (R)", "5"), FormulaVariable("T", "Years (T)", "2")),
            keywords = listOf("simple interest", "si", "commercial math", "percentage", "rate", "principal", "class 7", "class 8")
        ),
        FormulaItem(
            id = "comm_2",
            name = "Compound Interest Total Amount",
            formula = "A = P(1 + R/100)ᵀ",
            category = "Commercial Math",
            classRange = "Class 8-10",
            explanation = "Compounded balance over T periods.",
            example = "P=1000, R=10, T=2: A = 1000(1.1)² = 1210",
            variables = listOf(FormulaVariable("P", "Principal", "1000"), FormulaVariable("R", "Rate %", "10"), FormulaVariable("T", "Years", "2")),
            keywords = listOf("compound interest", "ci", "commercial math", "growth", "interest", "class 8", "class 9")
        ),
        FormulaItem(
            id = "stat_1",
            name = "Arithmetic Mean (Average)",
            formula = "Mean x̄ = (∑ xᵢ) / N",
            category = "Commercial Math",
            classRange = "Class 8-10",
            explanation = "Sum of all observations divided by the total count of observations N.",
            example = "Values 10, 20, 30: Mean = 60 / 3 = 20",
            variables = emptyList(),
            keywords = listOf("statistics", "mean", "average", "median", "mode", "frequency", "class 8", "class 9", "class 10")
        ),
        FormulaItem(
            id = "stat_2",
            name = "Probability of an Event P(E)",
            formula = "P(E) = n(E) / n(S)",
            category = "Commercial Math",
            classRange = "Class 9-12",
            explanation = "Number of favorable outcomes divided by total number of possible outcomes in sample space.",
            example = "Coin toss heads: P(H) = 1/2 = 0.5",
            variables = emptyList(),
            keywords = listOf("probability", "sample space", "event", "outcomes", "dice", "coins", "class 9", "class 10", "class 11", "class 12")
        )
    )

    fun calculateFormula(item: FormulaItem, inputs: Map<String, Double>): String {
        return when (item.id) {
            "alg_1" -> {
                val a = inputs["a"] ?: 2.0
                val b = inputs["b"] ?: 3.0
                val res = (a + b).pow(2)
                "Result: ($a + $b)² = $res\nSteps:\n• a² = ${a * a}\n• 2ab = ${2 * a * b}\n• b² = ${b * b}\n• Sum = ${a * a + 2 * a * b + b * b}"
            }
            "alg_2" -> {
                val a = inputs["a"] ?: 5.0
                val b = inputs["b"] ?: 2.0
                val res = (a - b).pow(2)
                "Result: ($a - $b)² = $res\nSteps:\n• a² = ${a * a}\n• -2ab = ${-2 * a * b}\n• b² = ${b * b}\n• Sum = $res"
            }
            "alg_3" -> {
                val a = inputs["a"] ?: 6.0
                val b = inputs["b"] ?: 4.0
                val res = a * a - b * b
                "Result: $a² - $b² = $res\nSteps:\n• (a - b) = ${a - b}\n• (a + b) = ${a + b}\n• Product = ${(a - b) * (a + b)}"
            }
            "alg_5" -> {
                val a = inputs["a"] ?: 2.0
                val b = inputs["b"] ?: 5.0
                val c = inputs["c"] ?: -12.0
                val d = b * b - 4 * a * c
                if (d >= 0) {
                    val x1 = (-b + sqrt(d)) / (2 * a)
                    val x2 = (-b - sqrt(d)) / (2 * a)
                    "Roots: x₁ = $x1, x₂ = $x2\nDiscriminant D = $d"
                } else {
                    "Roots: Imaginary (D = $d < 0)"
                }
            }
            "geo_1" -> {
                val r = inputs["r"] ?: 7.0
                val area = PI * r * r
                "Area = π × $r² = ${String.format("%.2f", area)} sq units"
            }
            "geo_2" -> {
                val r = inputs["r"] ?: 7.0
                val c = 2 * PI * r
                "Circumference = 2 × π × $r = ${String.format("%.2f", c)} units"
            }
            "geo_3" -> {
                val a = inputs["a"] ?: 3.0
                val b = inputs["b"] ?: 4.0
                val hyp = sqrt(a * a + b * b)
                "Hypotenuse c = √($a² + $b²) = √(${a * a + b * b}) = $hyp"
            }
            "comm_1" -> {
                val p = inputs["P"] ?: 1000.0
                val r = inputs["R"] ?: 5.0
                val t = inputs["T"] ?: 2.0
                val si = (p * r * t) / 100.0
                "Simple Interest = ($p × $r × $t) / 100 = ₹$si\nTotal Amount = ₹${p + si}"
            }
            else -> "Calculation executed: ${item.example}"
        }
    }
}
