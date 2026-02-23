package com.robjonesdev.todoprogger.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

enum class AppTheme {
    Green, Red, Blue, Purple
}

// Green Theme Colors
val GreenPrimary = Color(0xFF4C662B)
val GreenPrimaryDark = Color(0xFFB1D18A)

// Red Theme Colors
val RedPrimary = Color(0xFFBA1A1A)
val RedPrimaryDark = Color(0xFFFFB4AB)

// Blue Theme Colors
val BluePrimary = Color(0xFF0061A4)
val BluePrimaryDark = Color(0xFF9ECAFF)

// Purple Theme Colors
val PurplePrimary = Color(0xFF6750A4)
val PurplePrimaryDark = Color(0xFFD0BCFF)

private fun getLightColorScheme(theme: AppTheme) = when (theme) {
    AppTheme.Green -> lightColorScheme(primary = GreenPrimary)
    AppTheme.Red -> lightColorScheme(primary = RedPrimary)
    AppTheme.Blue -> lightColorScheme(primary = BluePrimary)
    AppTheme.Purple -> lightColorScheme(primary = PurplePrimary)
}

private fun getDarkColorScheme(theme: AppTheme) = when (theme) {
    AppTheme.Green -> darkColorScheme(primary = GreenPrimaryDark)
    AppTheme.Red -> darkColorScheme(primary = RedPrimaryDark)
    AppTheme.Blue -> darkColorScheme(primary = BluePrimaryDark)
    AppTheme.Purple -> darkColorScheme(primary = PurplePrimaryDark)
}

@Composable
fun TodoProggerTheme(
    appTheme: AppTheme = AppTheme.Green,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) getDarkColorScheme(appTheme) else getLightColorScheme(appTheme)

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
