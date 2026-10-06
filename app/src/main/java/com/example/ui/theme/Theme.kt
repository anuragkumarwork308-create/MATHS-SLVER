package com.example.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val AurumDarkColorScheme = darkColorScheme(
    primary = GoldPrimary,
    onPrimary = ObsidianBackground,
    primaryContainer = GoldContainer,
    onPrimaryContainer = OnGoldContainer,
    secondary = GoldSecondary,
    onSecondary = ObsidianBackground,
    secondaryContainer = ObsidianSurfaceVariant,
    onSecondaryContainer = GoldTertiary,
    tertiary = GoldTertiary,
    onTertiary = ObsidianBackground,
    background = ObsidianBackground,
    onBackground = TextPrimary,
    surface = ObsidianSurface,
    onSurface = TextPrimary,
    surfaceVariant = ObsidianSurfaceVariant,
    onSurfaceVariant = TextSecondary,
    outline = GoldSecondary,
    outlineVariant = CardBorderSubtle,
    error = AccentRed
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true, // Default to luxurious dark mode for Aurum Solver
    dynamicColor: Boolean = false, // Keep consistent royal gold branding
    content: @Composable () -> Unit
) {
    val colorScheme = AurumDarkColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                window.statusBarColor = ObsidianBackground.toArgb()
                window.navigationBarColor = ObsidianBackground.toArgb()
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
                WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = false
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
