package com.example.ui.components

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
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
import java.util.Locale
import kotlin.math.abs
import kotlin.math.atan
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

enum class FunctionType {
    LINEAR,       // y = mx + c
    QUADRATIC,    // y = ax^2 + bx + c
    CUSTOM        // custom f(x)
}

enum class CanvasEngine {
    NATIVE_COMPOSE, // Android Hardware-Accelerated Canvas API
    HTML5_CANVAS    // Offline HTML5 Canvas 2D Context API
}

@Composable
fun GraphCalculator(modifier: Modifier = Modifier) {
    CanvasGraphCalculator(modifier)
}

@Composable
fun CanvasGraphCalculator(
    modifier: Modifier = Modifier
) {
    var selectedFuncType by remember { mutableStateOf(FunctionType.LINEAR) }
    var canvasEngine by remember { mutableStateOf(CanvasEngine.NATIVE_COMPOSE) }

    // Linear parameters: y = mx + c
    var slopeM by remember { mutableFloatStateOf(1.5f) }
    var interceptC by remember { mutableFloatStateOf(-2.0f) }

    // Quadratic parameters: y = ax^2 + bx + c
    var coeffA by remember { mutableFloatStateOf(1.0f) }
    var coeffB by remember { mutableFloatStateOf(-2.0f) }
    var coeffC by remember { mutableFloatStateOf(-3.0f) }

    // Custom function string
    var customFunc by remember { mutableStateOf("y = x²") }

    // Pan & Zoom
    var panOffsetX by remember { mutableFloatStateOf(0f) }
    var panOffsetY by remember { mutableFloatStateOf(0f) }
    var zoomScale by remember { mutableFloatStateOf(24f) }

    // Interactive Inspector Touch point (x, y)
    var touchedPoint by remember { mutableStateOf<Pair<Float, Float>?>(null) }
    var showTable by remember { mutableStateOf(false) }

    // Smooth Drawing Animation loop state
    val drawAnimProgress = remember { Animatable(0f) }
    var animTrigger by remember { mutableIntStateOf(0) }

    // Automatically trigger smooth drawing loop whenever function/coefficients change
    LaunchedEffect(selectedFuncType, slopeM, interceptC, coeffA, coeffB, coeffC, customFunc, animTrigger) {
        drawAnimProgress.snapTo(0f)
        drawAnimProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 950, easing = FastOutSlowInEasing)
        )
    }

    // Function evaluator
    fun evaluate(x: Float): Float {
        return when (selectedFuncType) {
            FunctionType.LINEAR -> slopeM * x + interceptC
            FunctionType.QUADRATIC -> coeffA * x * x + coeffB * x + coeffC
            FunctionType.CUSTOM -> {
                when {
                    customFunc.contains("x³") || customFunc.contains("x^3") -> x * x * x
                    customFunc.contains("sin") -> sin(x)
                    customFunc.contains("cos") -> cos(x)
                    customFunc.contains("1/x") -> if (x != 0f) 1f / x else 0f
                    else -> x * x // default y = x^2
                }
            }
        }
    }

    // Mathematical calculations for display
    val currentEqTitle = when (selectedFuncType) {
        FunctionType.LINEAR -> {
            val mStr = String.format(Locale.US, "%.1f", slopeM)
            val cStr = if (interceptC >= 0) "+ ${String.format(Locale.US, "%.1f", interceptC)}" else "- ${String.format(Locale.US, "%.1f", abs(interceptC))}"
            "y = ${mStr}x $cStr"
        }
        FunctionType.QUADRATIC -> {
            val aStr = if (coeffA == 1f) "" else if (coeffA == -1f) "-" else String.format(Locale.US, "%.1f", coeffA)
            val bStr = if (coeffB >= 0) "+ ${String.format(Locale.US, "%.1f", coeffB)}" else "- ${String.format(Locale.US, "%.1f", abs(coeffB))}"
            val cStr = if (coeffC >= 0) "+ ${String.format(Locale.US, "%.1f", coeffC)}" else "- ${String.format(Locale.US, "%.1f", abs(coeffC))}"
            "y = ${aStr}x² ${bStr}x $cStr"
        }
        FunctionType.CUSTOM -> customFunc
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(Color(0xFF0A0A10))
            .border(1.dp, GoldSecondary.copy(alpha = 0.6f), RoundedCornerShape(18.dp))
            .padding(14.dp)
            .testTag("canvas_graph_calculator")
    ) {
        // Calculator Title & Engine Selector
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "📈 GRAPH CALCULATOR",
                        color = GoldPrimary,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 0.5.sp
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFF221A00))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = if (drawAnimProgress.value < 1f) "DRAWING..." else "READY",
                            color = if (drawAnimProgress.value < 1f) GoldPrimary else VerifiedGreen,
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Text(
                    text = "Smooth Drawing Loop • 100% Offline Canvas",
                    color = TextMuted,
                    fontSize = 10.sp
                )
            }

            // Engine Switcher (Native Compose Canvas vs HTML5 Canvas API)
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(14.dp))
                    .background(ObsidianSurfaceVariant)
                    .border(1.dp, Color(0xFF2E2E3E), RoundedCornerShape(14.dp))
                    .padding(2.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (canvasEngine == CanvasEngine.NATIVE_COMPOSE) GoldPrimary else Color.Transparent)
                        .clickable { canvasEngine = CanvasEngine.NATIVE_COMPOSE }
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "Native Canvas",
                        color = if (canvasEngine == CanvasEngine.NATIVE_COMPOSE) ObsidianBackground else TextSecondary,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (canvasEngine == CanvasEngine.HTML5_CANVAS) GoldPrimary else Color.Transparent)
                        .clickable { canvasEngine = CanvasEngine.HTML5_CANVAS }
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "HTML5 Canvas",
                        color = if (canvasEngine == CanvasEngine.HTML5_CANVAS) ObsidianBackground else TextSecondary,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Function Type Selector Tabs
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf(
                FunctionType.LINEAR to "Linear (y = mx + c)",
                FunctionType.QUADRATIC to "Quadratic (y = ax² + bx + c)",
                FunctionType.CUSTOM to "Special / Custom Curves"
            ).forEach { (fType, label) ->
                val isSelected = selectedFuncType == fType
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .then(
                            if (isSelected) Modifier.background(GoldGradient)
                            else Modifier.background(ObsidianSurfaceVariant)
                        )
                        .border(
                            1.dp,
                            if (isSelected) GoldPrimary else Color(0xFF262634),
                            RoundedCornerShape(16.dp)
                        )
                        .clickable {
                            selectedFuncType = fType
                            touchedPoint = null
                            animTrigger++
                        }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                        .testTag("func_type_${fType.name}")
                ) {
                    Text(
                        text = label,
                        color = if (isSelected) ObsidianBackground else GoldPrimary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Current Active Equation Title Badge + Re-animate button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFF14141E))
                .border(1.dp, GoldSecondary.copy(alpha = 0.4f), RoundedCornerShape(10.dp))
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Plotting: $currentEqTitle",
                    color = GoldPrimary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = FontFamily.Monospace,
                    modifier = Modifier.weight(1f)
                )

                // Re-draw animation button
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(GoldGradient)
                        .clickable { animTrigger++ }
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                        .testTag("reanimate_curve_button")
                ) {
                    Text(
                        text = "⚡ Animate",
                        color = ObsidianBackground,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // THE GRAPH CANVAS (Either Native Compose Canvas or HTML5 Canvas)
        if (canvasEngine == CanvasEngine.NATIVE_COMPOSE) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color(0xFF050508))
                    .border(1.dp, Color(0xFF242436), RoundedCornerShape(14.dp))
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            change.consume()
                            panOffsetX += dragAmount.x
                            panOffsetY += dragAmount.y
                        }
                    }
                    .pointerInput(zoomScale, panOffsetX, panOffsetY, selectedFuncType, slopeM, interceptC, coeffA, coeffB, coeffC) {
                        detectTapGestures { offset ->
                            val centerX = size.width / 2f + panOffsetX
                            val centerY = size.height / 2f + panOffsetY
                            val mathX = (offset.x - centerX) / zoomScale
                            val mathY = evaluate(mathX)
                            touchedPoint = mathX to mathY
                        }
                    }
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val w = size.width
                    val h = size.height
                    val originX = w / 2f + panOffsetX
                    val originY = h / 2f + panOffsetY

                    // 1. Draw Grid Lines & Numbers
                    val step = zoomScale
                    var xPos = originX % step
                    while (xPos < w) {
                        val isAxis = abs(xPos - originX) < 1.5f
                        drawLine(
                            color = if (isAxis) Color(0xFF4A4A60) else Color(0xFF13131F),
                            start = Offset(xPos, 0f),
                            end = Offset(xPos, h),
                            strokeWidth = if (isAxis) 1.5f else 1f
                        )
                        xPos += step
                    }

                    var yPos = originY % step
                    while (yPos < h) {
                        val isAxis = abs(yPos - originY) < 1.5f
                        drawLine(
                            color = if (isAxis) Color(0xFF4A4A60) else Color(0xFF13131F),
                            start = Offset(0f, yPos),
                            end = Offset(w, yPos),
                            strokeWidth = if (isAxis) 1.5f else 1f
                        )
                        yPos += step
                    }

                    // 2. Draw Main Axes (X & Y)
                    drawLine(
                        color = Color(0xFF6B6B8A),
                        start = Offset(0f, originY),
                        end = Offset(w, originY),
                        strokeWidth = 2f
                    )
                    drawLine(
                        color = Color(0xFF6B6B8A),
                        start = Offset(originX, 0f),
                        end = Offset(originX, h),
                        strokeWidth = 2f
                    )

                    // 3. Draw Axis Labels via native canvas
                    val paint = android.graphics.Paint().apply {
                        color = android.graphics.Color.parseColor("#777788")
                        textSize = 22f
                        isAntiAlias = true
                    }
                    val goldPaint = android.graphics.Paint().apply {
                        color = android.graphics.Color.parseColor("#FFD700")
                        textSize = 24f
                        isFakeBoldText = true
                        isAntiAlias = true
                    }

                    for (tick in -15..15) {
                        if (tick == 0) continue
                        val px = originX + tick * zoomScale
                        val py = originY - tick * zoomScale
                        if (px in 10f..(w - 20f)) {
                            drawContext.canvas.nativeCanvas.drawText("$tick", px - 8f, originY + 24f, paint)
                        }
                        if (py in 10f..(h - 20f)) {
                            drawContext.canvas.nativeCanvas.drawText("$tick", originX + 8f, py + 6f, paint)
                        }
                    }
                    drawContext.canvas.nativeCanvas.drawText("0", originX + 6f, originY + 24f, paint)
                    drawContext.canvas.nativeCanvas.drawText("X", w - 24f, originY - 8f, goldPaint)
                    drawContext.canvas.nativeCanvas.drawText("Y", originX + 8f, 28f, goldPaint)

                    // 4. ANIMATED DRAWING LOOP FOR CURVE
                    val animProgress = drawAnimProgress.value
                    val maxPx = w * animProgress

                    val curvePath = Path()
                    var first = true
                    val sampleStep = 2 // Sample every 2 pixels for smooth curve
                    var px = 0f
                    var tipPoint: Offset? = null

                    while (px <= maxPx) {
                        val mathX = (px - originX) / zoomScale
                        val mathY = evaluate(mathX)
                        val py = originY - mathY * zoomScale

                        if (py in -120f..(h + 120f)) {
                            if (first) {
                                curvePath.moveTo(px, py)
                                first = false
                            } else {
                                curvePath.lineTo(px, py)
                            }
                            tipPoint = Offset(px, py)
                        } else {
                            first = true
                        }
                        px += sampleStep
                    }

                    drawPath(
                        path = curvePath,
                        color = GoldPrimary,
                        style = Stroke(width = 3.5f)
                    )

                    // Glowing Pen / Tracer Cursor at the leading edge of the animation loop
                    if (animProgress < 1f && tipPoint != null) {
                        drawCircle(
                            color = Color(0x66FFD700),
                            radius = 10f,
                            center = tipPoint
                        )
                        drawCircle(
                            color = Color(0xFFFFD700),
                            radius = 5f,
                            center = tipPoint
                        )
                        drawCircle(
                            color = Color.White,
                            radius = 2.5f,
                            center = tipPoint
                        )
                    }

                    // 5. Draw Special Mathematical Points (Fade in at the end of drawing loop)
                    val pointsAlpha = ((animProgress - 0.85f) / 0.15f).coerceIn(0f, 1f)
                    if (pointsAlpha > 0f) {
                        when (selectedFuncType) {
                            FunctionType.LINEAR -> {
                                // y-intercept at (0, c)
                                val yIntX = originX
                                val yIntY = originY - interceptC * zoomScale
                                drawCircle(
                                    color = VerifiedGreen.copy(alpha = pointsAlpha),
                                    radius = 5.5f * pointsAlpha,
                                    center = Offset(yIntX, yIntY)
                                )

                                // x-intercept at (-c/m, 0) if m != 0
                                if (slopeM != 0f) {
                                    val xInt = -interceptC / slopeM
                                    val xIntX = originX + xInt * zoomScale
                                    val xIntY = originY
                                    drawCircle(
                                        color = GoldTertiary.copy(alpha = pointsAlpha),
                                        radius = 5.5f * pointsAlpha,
                                        center = Offset(xIntX, xIntY)
                                    )
                                }
                            }
                            FunctionType.QUADRATIC -> {
                                // Vertex at (-b/2a, c - b^2/4a)
                                if (coeffA != 0f) {
                                    val vx = -coeffB / (2 * coeffA)
                                    val vy = evaluate(vx)
                                    val vPx = originX + vx * zoomScale
                                    val vPy = originY - vy * zoomScale
                                    drawCircle(
                                        color = Color(0xFF60A5FA).copy(alpha = pointsAlpha),
                                        radius = 6.5f * pointsAlpha,
                                        center = Offset(vPx, vPy)
                                    )
                                }
                                // Roots if D >= 0
                                val disc = coeffB * coeffB - 4 * coeffA * coeffC
                                if (disc >= 0 && coeffA != 0f) {
                                    val r1 = (-coeffB + sqrt(disc)) / (2 * coeffA)
                                    val r2 = (-coeffB - sqrt(disc)) / (2 * coeffA)
                                    drawCircle(
                                        color = VerifiedGreen.copy(alpha = pointsAlpha),
                                        radius = 5.5f * pointsAlpha,
                                        center = Offset(originX + r1 * zoomScale, originY)
                                    )
                                    drawCircle(
                                        color = VerifiedGreen.copy(alpha = pointsAlpha),
                                        radius = 5.5f * pointsAlpha,
                                        center = Offset(originX + r2 * zoomScale, originY)
                                    )
                                }
                            }
                            else -> {}
                        }
                    }

                    // 6. Draw Touched Inspector Point & Crosshair
                    touchedPoint?.let { (tx, ty) ->
                        val touchPx = originX + tx * zoomScale
                        val touchPy = originY - ty * zoomScale

                        // Crosshair lines
                        drawLine(
                            color = Color(0x66FFD700),
                            start = Offset(touchPx, 0f),
                            end = Offset(touchPx, h),
                            strokeWidth = 1f
                        )
                        drawLine(
                            color = Color(0x66FFD700),
                            start = Offset(0f, touchPy),
                            end = Offset(w, touchPy),
                            strokeWidth = 1f
                        )
                        // Glowing Inspector Dot
                        drawCircle(color = GoldPrimary, radius = 7f, center = Offset(touchPx, touchPy))
                        drawCircle(color = ObsidianBackground, radius = 3f, center = Offset(touchPx, touchPy))
                    }
                }
            }
        } else {
            // HTML5 CANVAS 2D API WITH DRAWING LOOP (Offline In-App Engine via WebView)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color(0xFF000000))
                    .border(1.dp, GoldSecondary, RoundedCornerShape(14.dp))
            ) {
                val htmlScript = generateHtml5CanvasCode(
                    funcType = selectedFuncType,
                    m = slopeM,
                    c = interceptC,
                    a = coeffA,
                    b = coeffB,
                    cQuad = coeffC,
                    custom = customFunc,
                    scale = zoomScale,
                    trigger = animTrigger
                )

                AndroidView(
                    factory = { ctx ->
                        WebView(ctx).apply {
                            webViewClient = WebViewClient()
                            settings.javaScriptEnabled = true
                            settings.domStorageEnabled = true
                            loadDataWithBaseURL(null, htmlScript, "text/html", "UTF-8", null)
                        }
                    },
                    update = { webView ->
                        webView.loadDataWithBaseURL(null, htmlScript, "text/html", "UTF-8", null)
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // PAN, ZOOM & RESET CONTROLS
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Zoom",
                    color = TextMuted,
                    fontSize = 11.sp
                )
                Spacer(modifier = Modifier.width(6.dp))
                Slider(
                    value = zoomScale,
                    onValueChange = { zoomScale = it },
                    valueRange = 12f..48f,
                    modifier = Modifier.width(120.dp),
                    colors = SliderDefaults.colors(
                        thumbColor = GoldPrimary,
                        activeTrackColor = GoldSecondary,
                        inactiveTrackColor = Color(0xFF222230)
                    )
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(ObsidianSurfaceVariant)
                        .border(1.dp, Color(0xFF333344), RoundedCornerShape(8.dp))
                        .clickable {
                            panOffsetX = 0f
                            panOffsetY = 0f
                            zoomScale = 24f
                            touchedPoint = null
                            animTrigger++
                        }
                        .padding(horizontal = 8.dp, vertical = 5.dp)
                ) {
                    Text("⟲ Center", color = GoldPrimary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (showTable) GoldPrimary else ObsidianSurfaceVariant)
                        .clickable { showTable = !showTable }
                        .padding(horizontal = 8.dp, vertical = 5.dp)
                ) {
                    Text(
                        text = if (showTable) "Hide Table" else "Values Table",
                        color = if (showTable) ObsidianBackground else GoldSecondary,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // INTERACTIVE COEFFICIENT SLIDERS
        when (selectedFuncType) {
            FunctionType.LINEAR -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF111118))
                        .border(1.dp, Color(0xFF222232), RoundedCornerShape(12.dp))
                        .padding(12.dp)
                ) {
                    Text(
                        text = "📐 LINEAR PARAMETERS: y = mx + c",
                        color = GoldSecondary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Slope m slider
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Slope (m): ${String.format(Locale.US, "%.1f", slopeM)}",
                            color = TextPrimary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = if (slopeM > 0) "Ascending ↗" else if (slopeM < 0) "Descending ↘" else "Horizontal ―",
                            color = GoldTertiary,
                            fontSize = 10.sp
                        )
                    }
                    Slider(
                        value = slopeM,
                        onValueChange = { slopeM = it; touchedPoint = null },
                        valueRange = -5f..5f,
                        steps = 20,
                        colors = SliderDefaults.colors(thumbColor = GoldPrimary, activeTrackColor = GoldSecondary)
                    )

                    // Intercept c slider
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Y-Intercept (c): ${String.format(Locale.US, "%.1f", interceptC)}",
                            color = TextPrimary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "Point: (0, ${String.format(Locale.US, "%.1f", interceptC)})",
                            color = VerifiedGreen,
                            fontSize = 10.sp
                        )
                    }
                    Slider(
                        value = interceptC,
                        onValueChange = { interceptC = it; touchedPoint = null },
                        valueRange = -10f..10f,
                        steps = 20,
                        colors = SliderDefaults.colors(thumbColor = GoldPrimary, activeTrackColor = GoldSecondary)
                    )

                    // Mathematical summary
                    Spacer(modifier = Modifier.height(4.dp))
                    val thetaDeg = Math.toDegrees(atan(slopeM.toDouble()))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Angle: ${String.format(Locale.US, "%.1f°", thetaDeg)}",
                            color = TextMuted,
                            fontSize = 10.sp
                        )
                        if (slopeM != 0f) {
                            val rootX = -interceptC / slopeM
                            Text(
                                text = "Root (x-int): ${String.format(Locale.US, "%.2f", rootX)}",
                                color = GoldPrimary,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            FunctionType.QUADRATIC -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF111118))
                        .border(1.dp, Color(0xFF222232), RoundedCornerShape(12.dp))
                        .padding(12.dp)
                ) {
                    Text(
                        text = "⚡ QUADRATIC PARAMETERS: y = ax² + bx + c",
                        color = GoldSecondary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Parameter a
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Coeff (a): ${String.format(Locale.US, "%.1f", coeffA)}", color = TextPrimary, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        Text(if (coeffA > 0) "Opens Upwards ∪" else if (coeffA < 0) "Opens Downwards ∩" else "Degenerate Line", color = GoldTertiary, fontSize = 10.sp)
                    }
                    Slider(
                        value = coeffA,
                        onValueChange = { coeffA = if (it == 0f) 0.1f else it; touchedPoint = null },
                        valueRange = -3f..3f,
                        steps = 12,
                        colors = SliderDefaults.colors(thumbColor = GoldPrimary, activeTrackColor = GoldSecondary)
                    )

                    // Parameter b
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Coeff (b): ${String.format(Locale.US, "%.1f", coeffB)}", color = TextPrimary, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        Text("Axis of symmetry: x = ${String.format(Locale.US, "%.2f", -coeffB / (2 * coeffA))}", color = TextMuted, fontSize = 10.sp)
                    }
                    Slider(
                        value = coeffB,
                        onValueChange = { coeffB = it; touchedPoint = null },
                        valueRange = -6f..6f,
                        steps = 12,
                        colors = SliderDefaults.colors(thumbColor = GoldPrimary, activeTrackColor = GoldSecondary)
                    )

                    // Parameter c
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Constant (c): ${String.format(Locale.US, "%.1f", coeffC)}", color = TextPrimary, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        Text("Y-intercept: (0, ${String.format(Locale.US, "%.1f", coeffC)})", color = VerifiedGreen, fontSize = 10.sp)
                    }
                    Slider(
                        value = coeffC,
                        onValueChange = { coeffC = it; touchedPoint = null },
                        valueRange = -10f..10f,
                        steps = 20,
                        colors = SliderDefaults.colors(thumbColor = GoldPrimary, activeTrackColor = GoldSecondary)
                    )

                    // Quadratic Analysis
                    val d = coeffB * coeffB - 4 * coeffA * coeffC
                    val vx = -coeffB / (2 * coeffA)
                    val vy = evaluate(vx)
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Vertex: (${String.format(Locale.US, "%.2f", vx)}, ${String.format(Locale.US, "%.2f", vy)})", color = Color(0xFF60A5FA), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        Text(
                            text = "Discriminant D: ${String.format(Locale.US, "%.1f", d)} ${if (d > 0) "(2 Roots)" else if (d == 0f) "(1 Root)" else "(Imaginary)"}",
                            color = if (d >= 0) VerifiedGreen else AccentRed,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            FunctionType.CUSTOM -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF111118))
                        .border(1.dp, Color(0xFF222232), RoundedCornerShape(12.dp))
                        .padding(12.dp)
                ) {
                    Text(
                        text = "⚡ PRESET SPECIAL CURVES",
                        color = GoldSecondary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    val presets = listOf(
                        "y = x²" to "Standard Parabola",
                        "y = x³" to "Cubic Inflection",
                        "y = sin(x)" to "Sine Wave",
                        "y = cos(x)" to "Cosine Wave",
                        "y = 1/x" to "Hyperbola"
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        presets.forEach { (func, desc) ->
                            val isSelected = customFunc == func
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(if (isSelected) Color(0xFF221A00) else ObsidianSurfaceVariant)
                                    .border(1.dp, if (isSelected) GoldPrimary else Color(0xFF2E2E3E), RoundedCornerShape(10.dp))
                                    .clickable {
                                        customFunc = func
                                        touchedPoint = null
                                        animTrigger++
                                    }
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Column {
                                    Text(text = func, color = GoldPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                                    Text(text = desc, color = TextMuted, fontSize = 9.sp)
                                }
                            }
                        }
                    }
                }
            }
        }

        // VALUES TABLE (Expandable)
        AnimatedVisibility(visible = showTable) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF0F0F16))
                    .border(1.dp, Color(0xFF222232), RoundedCornerShape(12.dp))
                    .padding(12.dp)
            ) {
                Text(
                    text = "📋 TABLE OF VALUES: (x, y)",
                    color = GoldSecondary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("x", color = GoldPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                    for (xVal in -4..4) {
                        Text(
                            text = "$xVal",
                            color = TextSecondary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("y", color = GoldPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                    for (xVal in -4..4) {
                        val yVal = evaluate(xVal.toFloat())
                        Text(
                            text = String.format(Locale.US, "%.1f", yVal),
                            color = if (yVal == 0f) VerifiedGreen else GoldTertiary,
                            fontSize = 10.sp,
                            fontFamily = FontFamily.Monospace,
                            modifier = Modifier.weight(1f),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

/**
 * Generates standalone offline HTML5 Canvas 2D API code with animated drawing loop.
 * Uses requestAnimationFrame to progressively trace the curve.
 */
private fun generateHtml5CanvasCode(
    funcType: FunctionType,
    m: Float,
    c: Float,
    a: Float,
    b: Float,
    cQuad: Float,
    custom: String,
    scale: Float,
    trigger: Int
): String {
    val jsFunc = when (funcType) {
        FunctionType.LINEAR -> "return $m * x + $c;"
        FunctionType.QUADRATIC -> "return $a * x * x + $b * x + $cQuad;"
        FunctionType.CUSTOM -> {
            when {
                custom.contains("x³") || custom.contains("x^3") -> "return x * x * x;"
                custom.contains("sin") -> "return Math.sin(x);"
                custom.contains("cos") -> "return Math.cos(x);"
                custom.contains("1/x") -> "return x !== 0 ? 1 / x : 0;"
                else -> "return x * x;"
            }
        }
    }

    return """
        <!DOCTYPE html>
        <html>
        <head>
            <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
            <style>
                * { margin:0; padding:0; box-sizing:border-box; }
                body { background: #07070C; overflow: hidden; display: flex; justify-content: center; align-items: center; height: 100vh; }
                canvas { width: 100%; height: 100%; display: block; }
            </style>
        </head>
        <body>
            <canvas id="graphCanvas"></canvas>
            <script>
                const canvas = document.getElementById('graphCanvas');
                const ctx = canvas.getContext('2d');
                let progress = 0;
                let animFrame = null;

                function resize() {
                    canvas.width = window.innerWidth * window.devicePixelRatio;
                    canvas.height = window.innerHeight * window.devicePixelRatio;
                    startAnimation();
                }

                function f(x) {
                    $jsFunc
                }

                function drawFrame(prog) {
                    const w = canvas.width;
                    const h = canvas.height;
                    const scale = $scale * window.devicePixelRatio;
                    const originX = w / 2;
                    const originY = h / 2;

                    ctx.clearRect(0, 0, w, h);

                    // Grid
                    ctx.strokeStyle = '#151522';
                    ctx.lineWidth = 1;
                    for (let x = originX % scale; x < w; x += scale) {
                        ctx.beginPath(); ctx.moveTo(x, 0); ctx.lineTo(x, h); ctx.stroke();
                    }
                    for (let y = originY % scale; y < h; y += scale) {
                        ctx.beginPath(); ctx.moveTo(0, y); ctx.lineTo(w, y); ctx.stroke();
                    }

                    // Axes
                    ctx.strokeStyle = '#4A4A62';
                    ctx.lineWidth = 2 * window.devicePixelRatio;
                    ctx.beginPath(); ctx.moveTo(0, originY); ctx.lineTo(w, originY); ctx.stroke();
                    ctx.beginPath(); ctx.moveTo(originX, 0); ctx.lineTo(originX, h); ctx.stroke();

                    // Numbers
                    ctx.fillStyle = '#777788';
                    ctx.font = (10 * window.devicePixelRatio) + 'px monospace';
                    for (let tick = -10; tick <= 10; tick++) {
                        if (tick === 0) continue;
                        const px = originX + tick * scale;
                        const py = originY - tick * scale;
                        if (px > 0 && px < w) ctx.fillText(tick, px - 6, originY + 16);
                        if (py > 0 && py < h) ctx.fillText(tick, originX + 6, py + 4);
                    }

                    // Progressive Curve Draw Loop
                    ctx.strokeStyle = '#FFD700';
                    ctx.lineWidth = 3.5 * window.devicePixelRatio;
                    ctx.beginPath();

                    const maxPx = w * prog;
                    let started = false;
                    let lastX = 0, lastY = 0;

                    for (let px = 0; px <= maxPx; px += 2) {
                        const mathX = (px - originX) / scale;
                        const mathY = f(mathX);
                        const py = originY - mathY * scale;

                        if (py >= -100 && py <= h + 100) {
                            if (!started) {
                                ctx.moveTo(px, py);
                                started = true;
                            } else {
                                ctx.lineTo(px, py);
                            }
                            lastX = px;
                            lastY = py;
                        } else {
                            started = false;
                        }
                    }
                    ctx.stroke();

                    // Glowing drawing tip cursor while animating
                    if (prog < 1 && started) {
                        ctx.fillStyle = 'rgba(255, 215, 0, 0.4)';
                        ctx.beginPath();
                        ctx.arc(lastX, lastY, 8 * window.devicePixelRatio, 0, Math.PI * 2);
                        ctx.fill();

                        ctx.fillStyle = '#FFD700';
                        ctx.beginPath();
                        ctx.arc(lastX, lastY, 4 * window.devicePixelRatio, 0, Math.PI * 2);
                        ctx.fill();
                    }

                    // Origin Dot
                    ctx.fillStyle = '#D4AF37';
                    ctx.beginPath();
                    ctx.arc(originX, originY, 4 * window.devicePixelRatio, 0, Math.PI * 2);
                    ctx.fill();
                }

                function startAnimation() {
                    if (animFrame) cancelAnimationFrame(animFrame);
                    progress = 0;
                    function loop() {
                        progress += 0.025; // 40 frames smooth drawing loop
                        if (progress > 1) progress = 1;
                        drawFrame(progress);
                        if (progress < 1) {
                            animFrame = requestAnimationFrame(loop);
                        }
                    }
                    animFrame = requestAnimationFrame(loop);
                }

                window.addEventListener('resize', resize);
                resize();
            </script>
        </body>
        </html>
    """.trimIndent()
}
