package com.robjonesdev.todoprogger.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val PrimaryGreen = Color(0xFF4C662B)
val OnPrimaryGreen = Color(0xFFFFFFFF)
val PrimaryContainerGreen = Color(0xFFCDEDA3)
val OnPrimaryContainerGreen = Color(0xFF102000)

val SecondaryBeige = Color(0xFF586249)
val OnSecondaryBeige = Color(0xFFFFFFFF)

val PureWhite = Color(0xFFFFFFFF)
val DarkerBeige = Color(0xFFE1E4D5)
val OnBackgroundDark = Color(0xFF1A1C16)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryGreen,
    onPrimary = OnPrimaryGreen,
    primaryContainer = PrimaryContainerGreen,
    onPrimaryContainer = OnPrimaryContainerGreen,
    secondary = SecondaryBeige,
    onSecondary = OnSecondaryBeige,
    background = PureWhite, // Main floor is now pure white
    onBackground = OnBackgroundDark,
    surface = DarkerBeige,   // Surfaces (cards) are now a darker beige
    onSurface = OnBackgroundDark,
    surfaceVariant = Color(0xFFD1D4C5), // Even darker for secondary surface elements
    onSurfaceVariant = Color(0xFF44483D)
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFB1D18A),
    onPrimary = Color(0xFF1F3701),
    primaryContainer = Color(0xFF354E16),
    onPrimaryContainer = Color(0xFFCDEDA3),
    secondary = Color(0xFFBFCBAD),
    onSecondary = Color(0xFF2A331E),
    background = Color(0xFF12140E),
    onBackground = Color(0xFFE2E3D8),
    surface = Color(0xFF1E201A),
    onSurface = Color(0xFFE2E3D8),
    surfaceVariant = Color(0xFF44483D),
    onSurfaceVariant = Color(0xFFC5C8BA)
)

@Composable
fun TodoProggerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
