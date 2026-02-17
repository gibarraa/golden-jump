package com.goldenjump.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColors = darkColorScheme(
    primary = McDYellow,
    onPrimary = McDBlack,
    secondary = McDRed,
    onSecondary = Color.White,
    background = McDBlack,
    onBackground = McDOffWhite,
    surface = Color(0xFF1F1D19),
    onSurface = McDOffWhite,
    surfaceVariant = Color(0xFF24221D),
    onSurfaceVariant = McDOffWhite
)

@Composable
fun GoldenJumpTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColors,
        typography = Typography,
        content = content
    )
}