package com.robjonesdev.todoprogger.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

enum class AppTheme {
    Green, Red, Blue, Purple
}

// --- GREEN THEME ---
val GreenPrimary = Color(0xFF4C662B)
val GreenOnPrimary = Color(0xFFFFFFFF)
val GreenPrimaryContainer = Color(0xFFCDEDA3)
val GreenOnPrimaryContainer = Color(0xFF102000)
val GreenSecondary = Color(0xFF586249)
val GreenSecondaryContainer = Color(0xFFDCE7C8)
val GreenTertiary = Color(0xFF386567)
val GreenTertiaryContainer = Color(0xFFBCEBEB)
val GreenBackground = Color(0xFFF7FBF1)
val GreenSurface = Color(0xFFF7FBF1)
val GreenSurfaceVariant = Color(0xFFE1E4D5)
val GreenOnSurfaceVariant = Color(0xFF44483D)

val GreenPrimaryDark = Color(0xFFB1D18A)
val GreenOnPrimaryDark = Color(0xFF1F3701)
val GreenPrimaryContainerDark = Color(0xFF354E16)
val GreenOnPrimaryContainerDark = Color(0xFFCDEDA3)
val GreenSecondaryDark = Color(0xFFBFCBAD)
val GreenSecondaryContainerDark = Color(0xFF404A33)
val GreenBackgroundDark = Color(0xFF11140E)
val GreenSurfaceDark = Color(0xFF11140E)
val GreenSurfaceVariantDark = Color(0xFF44483D)
val GreenOnSurfaceVariantDark = Color(0xFFC4C8BA)

// --- RED THEME ---
val RedPrimary = Color(0xFFBA1A1A)
val RedOnPrimary = Color(0xFFFFFFFF)
val RedPrimaryContainer = Color(0xFFFFDAD6)
val RedOnPrimaryContainer = Color(0xFF410002)
val RedSecondary = Color(0xFF775652)
val RedSecondaryContainer = Color(0xFFFFDAD5)
val RedTertiary = Color(0xFF715B2E)
val RedTertiaryContainer = Color(0xFFFDE0A6)
val RedBackground = Color(0xFFFFF8F7)
val RedSurface = Color(0xFFFFF8F7)
val RedSurfaceVariant = Color(0xFFF5DDDA)
val RedOnSurfaceVariant = Color(0xFF534341)

val RedPrimaryDark = Color(0xFFFFB4AB)
val RedOnPrimaryDark = Color(0xFF690005)
val RedPrimaryContainerDark = Color(0xFF93000A)
val RedOnPrimaryContainerDark = Color(0xFFFFDAD6)
val RedSecondaryDark = Color(0xFFE7BDB7)
val RedSecondaryContainerDark = Color(0xFF5D403B)
val RedBackgroundDark = Color(0xFF1A1110)
  val RedSurfaceDark = Color(0xFF1A1110)
val RedSurfaceVariantDark = Color(0xFF534341)
val RedOnSurfaceVariantDark = Color(0xFFD8C2BF)

// --- BLUE THEME ---
val BluePrimary = Color(0xFF0061A4)
val BlueOnPrimary = Color(0xFFFFFFFF)
val BluePrimaryContainer = Color(0xFFD1E4FF)
val BlueOnPrimaryContainer = Color(0xFF001D36)
val BlueSecondary = Color(0xFF535F70)
val BlueSecondaryContainer = Color(0xFFD7E3F7)
val BlueTertiary = Color(0xFF6B5778)
val BlueTertiaryContainer = Color(0xFFF2DAFF)
val BlueBackground = Color(0xFFF8F9FF)
val BlueSurface = Color(0xFFF8F9FF)
val BlueSurfaceVariant = Color(0xFFDFE2EB)
val BlueOnSurfaceVariant = Color(0xFF43474E)

val BluePrimaryDark = Color(0xFF9ECAFF)
val BlueOnPrimaryDark = Color(0xFF003258)
val BluePrimaryContainerDark = Color(0xFF00497D)
val BlueOnPrimaryContainerDark = Color(0xFFD1E4FF)
val BlueSecondaryDark = Color(0xFFBBC7DB)
val BlueSecondaryContainerDark = Color(0xFF3B4858)
val BlueBackgroundDark = Color(0xFF111318)
val BlueSurfaceDark = Color(0xFF111318)
val BlueSurfaceVariantDark = Color(0xFF43474E)
val BlueOnSurfaceVariantDark = Color(0xFFC3C7CF)

// --- PURPLE THEME ---
val PurplePrimary = Color(0xFF6750A4)
val PurpleOnPrimary = Color(0xFFFFFFFF)
val PurplePrimaryContainer = Color(0xFFE9DDFF)
val PurpleOnPrimaryContainer = Color(0xFF21005D)
val PurpleSecondary = Color(0xFF625B71)
val PurpleSecondaryContainer = Color(0xFFE8DEF8)
val PurpleTertiary = Color(0xFF7E5260)
val PurpleTertiaryContainer = Color(0xFFFFD9E3)
val PurpleBackground = Color(0xFFFFFBFF)
val PurpleSurface = Color(0xFFFFFBFF)
val PurpleSurfaceVariant = Color(0xFFE7E0EB)
val PurpleOnSurfaceVariant = Color(0xFF49454F)

val PurplePrimaryDark = Color(0xFFD0BCFF)
val PurpleOnPrimaryDark = Color(0xFF381E72)
val PurplePrimaryContainerDark = Color(0xFF4F378B)
val PurpleOnPrimaryContainerDark = Color(0xFFE9DDFF)
val PurpleSecondaryDark = Color(0xFFCCC2DC)
val PurpleSecondaryContainerDark = Color(0xFF4A4458)
val PurpleBackgroundDark = Color(0xFF1C1B1F)
val PurpleSurfaceDark = Color(0xFF1C1B1F)
val PurpleSurfaceVariantDark = Color(0xFF49454F)
val PurpleOnSurfaceVariantDark = Color(0xFFCAC4D0)

private fun getLightColorScheme(theme: AppTheme) = when (theme) {
    AppTheme.Green -> lightColorScheme(
        primary = GreenPrimary, onPrimary = GreenOnPrimary, primaryContainer = GreenPrimaryContainer, onPrimaryContainer = GreenOnPrimaryContainer,
        secondary = GreenSecondary, secondaryContainer = GreenSecondaryContainer, tertiary = GreenTertiary, tertiaryContainer = GreenTertiaryContainer,
        background = GreenBackground, surface = GreenSurface, surfaceVariant = GreenSurfaceVariant, onSurfaceVariant = GreenOnSurfaceVariant
    )
    AppTheme.Red -> lightColorScheme(
        primary = RedPrimary, onPrimary = RedOnPrimary, primaryContainer = RedPrimaryContainer, onPrimaryContainer = RedOnPrimaryContainer,
        secondary = RedSecondary, secondaryContainer = RedSecondaryContainer, tertiary = RedTertiary, tertiaryContainer = RedTertiaryContainer,
        background = RedBackground, surface = RedSurface, surfaceVariant = RedSurfaceVariant, onSurfaceVariant = RedOnSurfaceVariant
    )
    AppTheme.Blue -> lightColorScheme(
        primary = BluePrimary, onPrimary = BlueOnPrimary, primaryContainer = BluePrimaryContainer, onPrimaryContainer = BlueOnPrimaryContainer,
        secondary = BlueSecondary, secondaryContainer = BlueSecondaryContainer, tertiary = BlueTertiary, tertiaryContainer = BlueTertiaryContainer,
        background = BlueBackground, surface = BlueSurface, surfaceVariant = BlueSurfaceVariant, onSurfaceVariant = BlueOnSurfaceVariant
    )
    AppTheme.Purple -> lightColorScheme(
        primary = PurplePrimary, onPrimary = PurpleOnPrimary, primaryContainer = PurplePrimaryContainer, onPrimaryContainer = PurpleOnPrimaryContainer,
        secondary = PurpleSecondary, secondaryContainer = PurpleSecondaryContainer, tertiary = PurpleTertiary, tertiaryContainer = PurpleTertiaryContainer,
        background = PurpleBackground, surface = PurpleSurface, surfaceVariant = PurpleSurfaceVariant, onSurfaceVariant = PurpleOnSurfaceVariant
    )
}

private fun getDarkColorScheme(theme: AppTheme) = when (theme) {
    AppTheme.Green -> darkColorScheme(
        primary = GreenPrimaryDark, onPrimary = GreenOnPrimaryDark, primaryContainer = GreenPrimaryContainerDark, onPrimaryContainer = GreenOnPrimaryContainerDark,
        secondary = GreenSecondaryDark, secondaryContainer = GreenSecondaryContainerDark, background = GreenBackgroundDark, surface = GreenSurfaceDark, 
        surfaceVariant = GreenSurfaceVariantDark, onSurfaceVariant = GreenOnSurfaceVariantDark
    )
    AppTheme.Red -> darkColorScheme(
        primary = RedPrimaryDark, onPrimary = RedOnPrimaryDark, primaryContainer = RedPrimaryContainerDark, onPrimaryContainer = RedOnPrimaryContainerDark,
        secondary = RedSecondaryDark, secondaryContainer = RedSecondaryContainerDark, background = RedBackgroundDark, surface = RedSurfaceDark, 
        surfaceVariant = RedSurfaceVariantDark, onSurfaceVariant = RedOnSurfaceVariantDark
    )
    AppTheme.Blue -> darkColorScheme(
        primary = BluePrimaryDark, onPrimary = BlueOnPrimaryDark, primaryContainer = BluePrimaryContainerDark, onPrimaryContainer = BlueOnPrimaryContainerDark,
        secondary = BlueSecondaryDark, secondaryContainer = BlueSecondaryContainerDark, background = BlueBackgroundDark, surface = BlueSurfaceDark, 
        surfaceVariant = BlueSurfaceVariantDark, onSurfaceVariant = BlueOnSurfaceVariantDark
    )
    AppTheme.Purple -> darkColorScheme(
        primary = PurplePrimaryDark, onPrimary = PurpleOnPrimaryDark, primaryContainer = PurplePrimaryContainerDark, onPrimaryContainer = PurpleOnPrimaryContainerDark,
        secondary = PurpleSecondaryDark, secondaryContainer = PurpleSecondaryContainerDark, background = PurpleBackgroundDark, surface = PurpleSurfaceDark, 
        surfaceVariant = PurpleSurfaceVariantDark, onSurfaceVariant = PurpleOnSurfaceVariantDark
    )
}

@Composable
fun TodoProggerTheme(
    appTheme: AppTheme = AppTheme.Green,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) getDarkColorScheme(appTheme) else getLightColorScheme(appTheme)
    MaterialTheme(colorScheme = colorScheme, content = content)
}
