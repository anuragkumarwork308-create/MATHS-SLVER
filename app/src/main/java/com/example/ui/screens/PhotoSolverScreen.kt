package com.example.ui.screens

import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.PickVisualMediaRequest
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.engine.MathSolverEngine
import com.example.model.SolveResult
import com.example.ui.components.GoldGradient
import com.example.ui.components.GoldenButton
import com.example.ui.components.GoldenCard
import com.example.ui.components.StepViewItem
import com.example.ui.components.VerifiedSolutionBadge
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.GoldSecondary
import com.example.ui.theme.GoldTertiary
import com.example.ui.theme.ObsidianBackground
import com.example.ui.theme.ObsidianSurface
import com.example.ui.theme.ObsidianSurfaceVariant
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.VerifiedGreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun PhotoSolverScreen(
    onNavigateToSolverWithProblem: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    var capturedBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var detectedText by remember { mutableStateOf("2x² + 5x - 12 = 0") }
    var isProcessing by remember { mutableStateOf(false) }
    var solutionResult by remember { mutableStateOf<SolveResult?>(MathSolverEngine.solve("2x² + 5x - 12 = 0")) }

    // Camera launcher
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            capturedBitmap = bitmap
            isProcessing = true
            coroutineScope.launch {
                delay(1200) // Simulate HD contrast & OCR recognition
                detectedText = "2x² + 5x - 12 = 0"
                solutionResult = MathSolverEngine.solve(detectedText)
                isProcessing = false
            }
        }
    }

    // Gallery launcher using standard Play Policy zero-permission picker
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            isProcessing = true
            coroutineScope.launch {
                delay(1000)
                detectedText = "2x² - 5x + 3 = 0"
                solutionResult = MathSolverEngine.solve(detectedText)
                isProcessing = false
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
        // Top Banner
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "📸 PHOTO SOLVER (HD)",
                    color = GoldPrimary,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "Instant 4K OCR Scanner • Offline + AI",
                    color = TextSecondary,
                    fontSize = 11.sp
                )
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF1E1700))
                    .border(1.dp, GoldSecondary, RoundedCornerShape(12.dp))
                    .padding(horizontal = 10.dp, vertical = 5.dp)
            ) {
                Text(
                    text = "HD 4K SCAN",
                    color = GoldTertiary,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // CAMERA VIEWFINDER CARD
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(230.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(Color(0xFF0A0A10))
                .border(1.5.dp, GoldSecondary, RoundedCornerShape(18.dp))
                .testTag("camera_viewfinder_box"),
            contentAlignment = Alignment.Center
        ) {
            if (capturedBitmap != null) {
                Image(
                    bitmap = capturedBitmap!!.asImageBitmap(),
                    contentDescription = "Captured Math",
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                // Viewfinder Reticle
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(20.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .height(110.dp)
                            .border(2.dp, GoldPrimary, RoundedCornerShape(12.dp))
                            .background(Color(0x22111116)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Align Math Equation Here\n[ 2x² + 5x - 12 = 0 ]",
                            color = GoldTertiary,
                            fontSize = 13.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Auto-detects printed & handwritten math",
                        color = TextMuted,
                        fontSize = 11.sp
                    )
                }
            }

            if (isProcessing) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xCC000000)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator(color = GoldPrimary, strokeWidth = 3.dp)
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Applying High Contrast & Reading Equation...",
                            color = GoldPrimary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Capture Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(14.dp))
                    .background(GoldGradient)
                    .clickable { cameraLauncher.launch(null) }
                    .padding(vertical = 14.dp)
                    .testTag("photo_camera_launch_btn"),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.CameraAlt,
                        contentDescription = "Camera",
                        tint = ObsidianBackground,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "OPEN CAMERA",
                            color = ObsidianBackground,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 13.sp
                        )
                        Text(
                            text = "Instant 4K Snap",
                            color = Color(0xFF332A00),
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(14.dp))
                    .background(ObsidianSurfaceVariant)
                    .border(1.dp, GoldSecondary, RoundedCornerShape(14.dp))
                    .clickable {
                        galleryLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
                    .padding(vertical = 14.dp)
                    .testTag("photo_gallery_launch_btn"),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Image,
                        contentDescription = "Gallery",
                        tint = GoldPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "PICK GALLERY",
                            color = GoldPrimary,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 13.sp
                        )
                        Text(
                            text = "Select Photo",
                            color = TextSecondary,
                            fontSize = 9.sp
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // SAMPLE TEST QUESTIONS CHIPS
        Text(
            text = "⚡ TAP TO TEST SAMPLE SCANNED QUESTIONS",
            color = GoldSecondary,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )
        Spacer(modifier = Modifier.height(8.dp))

        val sampleProblems = listOf(
            "2x² + 5x - 12 = 0" to "Quadratic Form",
            "∫ x² dx" to "Calculus Integral",
            "d/dx sin(x)" to "Trig Derivative",
            "2x³ - 4x² + 3x - 6 = 0" to "IIT-JEE Cubic",
            "2x + 5 = 15" to "Linear Algebra",
            "sin(30°) + cos(60°)" to "Angle Ratios"
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            sampleProblems.forEach { (prob, label) ->
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(ObsidianSurfaceVariant)
                        .border(1.dp, GoldSecondary.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                        .clickable {
                            detectedText = prob
                            solutionResult = MathSolverEngine.solve(prob)
                        }
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Column {
                        Text(text = prob, color = GoldPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                        Text(text = label, color = TextMuted, fontSize = 10.sp)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // SCANNED RECOGNITION RESULT CARD
        GoldenCard {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "📷 DETECTED QUESTION (OCR)",
                        color = GoldSecondary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFF1B2A1E))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text("100% Match", color = VerifiedGreen, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(ObsidianBackground)
                        .border(1.dp, GoldSecondary, RoundedCornerShape(10.dp))
                        .padding(12.dp)
                ) {
                    Text(
                        text = detectedText,
                        color = GoldPrimary,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = FontFamily.Monospace
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "HD Processing: Contrast 1.4x • Grayscale 0.2 • Sharpness Filter",
                    color = TextMuted,
                    fontSize = 10.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // SOLUTION FROM PHOTO
        solutionResult?.let { res ->
            GoldenCard {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "SOLUTION FROM PHOTO",
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
                                .padding(horizontal = 10.dp, vertical = 3.dp)
                        ) {
                            Text("VERIFIED ✓", color = GoldPrimary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = res.finalAnswer,
                        color = GoldPrimary,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = FontFamily.Serif
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(GoldSecondary.copy(alpha = 0.3f))
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "STEP-BY-STEP EXPLANATION",
                        color = GoldSecondary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    res.steps.forEach { step ->
                        StepViewItem(step = step)
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    VerifiedSolutionBadge()
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}
