package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

enum class ThemeMode(val id: String, val nameFa: String, val nameEn: String) {
    SYSTEM("system", "پیرو سیستم", "System Default"),
    LIGHT("light", "روز (روشن)", "Light Mode"),
    DARK("dark", "شب (تیره)", "Dark Mode");

    fun getLocalizedName(strings: com.example.core.i18n.AppStrings.Strings): String {
        return when (this) {
            SYSTEM -> strings.systemMode
            LIGHT -> strings.lightMode
            DARK -> strings.darkMode
        }
    }

    companion object {
        fun fromId(id: String): ThemeMode {
            return entries.firstOrNull { it.id.equals(id, ignoreCase = true) } ?: SYSTEM
        }
    }
}

@Composable
fun MyApplicationTheme(
    themePreset: AppThemePreset = AppThemePreset.METALLIC_BLACK,
    themeMode: ThemeMode = ThemeMode.SYSTEM,
    textScale: Float = 1.0f,
    content: @Composable () -> Unit
) {
    val isDark = when (themeMode) {
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
    }

    val colorScheme = if (isDark) {
        darkColorScheme(
            primary = themePreset.darkPrimary,
            onPrimary = if (themePreset == AppThemePreset.METALLIC_BLACK) Color(0xFF090A0D) else Color.White,
            primaryContainer = themePreset.darkPrimary.copy(alpha = 0.25f),
            onPrimaryContainer = Color(0xFFF8FAFC),
            secondary = themePreset.darkSecondary,
            onSecondary = Color(0xFF090A0D),
            secondaryContainer = themePreset.darkSecondary.copy(alpha = 0.2f),
            onSecondaryContainer = Color(0xFFE2E8F0),
            background = themePreset.darkBackground,
            onBackground = Color(0xFFF8FAFC),
            surface = themePreset.darkSurface,
            onSurface = Color(0xFFF1F5F9),
            surfaceVariant = themePreset.darkSurfaceVariant,
            onSurfaceVariant = Color(0xFFCBD5E1),
            outline = themePreset.darkOutline,
            outlineVariant = themePreset.darkOutline.copy(alpha = 0.6f),
            error = Color(0xFFF87171),
            onError = Color(0xFF090A0D)
        )
    } else {
        lightColorScheme(
            primary = themePreset.lightPrimary,
            onPrimary = Color.White,
            primaryContainer = themePreset.lightPrimary.copy(alpha = 0.12f),
            onPrimaryContainer = themePreset.lightPrimary,
            secondary = themePreset.lightSecondary,
            onSecondary = Color.White,
            secondaryContainer = themePreset.lightSecondary.copy(alpha = 0.12f),
            onSecondaryContainer = Color(0xFF1E293B),
            background = themePreset.lightBackground,
            onBackground = Color(0xFF0F172A),
            surface = themePreset.lightSurface,
            onSurface = Color(0xFF1E293B),
            surfaceVariant = themePreset.lightSurfaceVariant,
            onSurfaceVariant = Color(0xFF475569),
            outline = themePreset.lightOutline,
            outlineVariant = themePreset.lightOutline.copy(alpha = 0.5f),
            error = Color(0xFFDC2626),
            onError = Color.White
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = getAppTypography(textScale),
        content = content
    )
}
