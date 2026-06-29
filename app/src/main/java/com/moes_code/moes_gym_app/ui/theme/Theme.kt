package com.moes_code.moes_gym_app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = Colors.moe_blue,
    onPrimary = Colors.moe_white,
    primaryContainer = Colors.moe_blue_dim,
    onPrimaryContainer = Colors.moe_blue_bright,
    secondary = Colors.moe_yellow,
    onSecondary = Colors.moe_black,
    secondaryContainer = Colors.moe_yellow_dim,
    onSecondaryContainer = Colors.moe_yellow,
    tertiary = Colors.moe_green,
    onTertiary = Colors.moe_white,
    tertiaryContainer = Colors.moe_green_dim,
    onTertiaryContainer = Colors.moe_green,
    error = Colors.moe_red,
    onError = Colors.moe_white,
    errorContainer = Colors.moe_red_dim,
    onErrorContainer = Colors.moe_red,
    background = Colors.moe_black,
    onBackground = Colors.moe_white,
    surface = Colors.surface,
    onSurface = Colors.onSurface,
    surfaceVariant = Colors.surfaceVariant,
    onSurfaceVariant = Colors.onSurfaceVariant,
    surfaceContainer = Colors.surfaceContainer,
    surfaceContainerLowest = Colors.moe_black,
    surfaceContainerLow = Colors.surface,
    surfaceContainerHigh = Colors.surfaceContainerHigh,
    surfaceContainerHighest = Colors.surfaceElevated,
    outline = Colors.outline,
    outlineVariant = Colors.outlineVariant,
    inverseSurface = Colors.moe_white,
    inverseOnSurface = Colors.moe_black,
    inversePrimary = Colors.moe_blue_dim,
    scrim = Colors.moe_black
)

@Composable
fun GYMTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = AppTypography,
        shapes = AppShapes,
        content = content
    )
}
