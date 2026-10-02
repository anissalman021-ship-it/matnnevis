package com.example.ui.theme

import androidx.compose.ui.graphics.Color

// Luxury Metallic Obsidian & Platinum Palette
val MetallicBlackBackground = Color(0xFF090A0D)
val MetallicBlackSurface = Color(0xFF13151A)
val MetallicBlackSurfaceVariant = Color(0xFF1E2129)
val MetallicBlackCard = Color(0xFF171A21)
val MetallicPlatinum = Color(0xFFE2E8F0)
val MetallicSilver = Color(0xFF94A3B8)
val MetallicChromeBorder = Color(0xFF2E3340)
val MetallicAccent = Color(0xFFF1F5F9)

// Note Card Color Palette with complementary light and luxury dark tints
data class NoteColorDef(
    val id: String,
    val nameFa: String,
    val nameEn: String,
    val lightBg: Color,
    val darkBg: Color,
    val accent: Color
)

object NoteColors {
    val Default = NoteColorDef(
        id = "default",
        nameFa = "پیش‌فرض",
        nameEn = "Default",
        lightBg = Color(0xFFFFFFFF),
        darkBg = Color(0xFF15181E),
        accent = Color(0xFF94A3B8)
    )
    val Red = NoteColorDef(
        id = "red",
        nameFa = "قرمز",
        nameEn = "Red",
        lightBg = Color(0xFFFEE2E2),
        darkBg = Color(0xFF2D1418),
        accent = Color(0xFFF87171)
    )
    val Orange = NoteColorDef(
        id = "orange",
        nameFa = "نارنجی",
        nameEn = "Orange",
        lightBg = Color(0xFFFFEDD5),
        darkBg = Color(0xFF331D10),
        accent = Color(0xFFFB923C)
    )
    val Yellow = NoteColorDef(
        id = "yellow",
        nameFa = "زرد",
        nameEn = "Yellow",
        lightBg = Color(0xFFFEF9C3),
        darkBg = Color(0xFF2E270E),
        accent = Color(0xFFFACC15)
    )
    val Green = NoteColorDef(
        id = "green",
        nameFa = "سبز",
        nameEn = "Green",
        lightBg = Color(0xFFDCFCE7),
        darkBg = Color(0xFF10281A),
        accent = Color(0xFF4ADE80)
    )
    val Blue = NoteColorDef(
        id = "blue",
        nameFa = "آبی",
        nameEn = "Blue",
        lightBg = Color(0xFFDBEAFE),
        darkBg = Color(0xFF122238),
        accent = Color(0xFF60A5FA)
    )
    val Purple = NoteColorDef(
        id = "purple",
        nameFa = "بنفش",
        nameEn = "Purple",
        lightBg = Color(0xFFF3E8FF),
        darkBg = Color(0xFF261536),
        accent = Color(0xFFC084FC)
    )
    val Pink = NoteColorDef(
        id = "pink",
        nameFa = "صورتی",
        nameEn = "Pink",
        lightBg = Color(0xFFFCE7F3),
        darkBg = Color(0xFF321323),
        accent = Color(0xFFF472B6)
    )
    val Teal = NoteColorDef(
        id = "teal",
        nameFa = "فیروزه‌ای",
        nameEn = "Teal",
        lightBg = Color(0xFFCCFBF1),
        darkBg = Color(0xFF0F2624),
        accent = Color(0xFF2DD4BF)
    )

    val all: List<NoteColorDef> = listOf(
        Default, Red, Orange, Yellow, Green, Blue, Purple, Pink, Teal
    )

    fun fromId(id: String): NoteColorDef {
        return all.firstOrNull { it.id.equals(id, ignoreCase = true) } ?: Default
    }
}

enum class AppThemePreset(
    val id: String,
    val nameFa: String,
    val nameEn: String,
    val darkPrimary: Color,
    val darkSecondary: Color,
    val darkBackground: Color,
    val darkSurface: Color,
    val darkSurfaceVariant: Color,
    val darkOutline: Color,
    val lightPrimary: Color,
    val lightSecondary: Color,
    val lightBackground: Color,
    val lightSurface: Color,
    val lightSurfaceVariant: Color,
    val lightOutline: Color
) {
    METALLIC_BLACK(
        id = "metallic_black",
        nameFa = "مشکی متالیک لوکس (اصلی)",
        nameEn = "Metallic Glossy Luxury Black",
        darkPrimary = Color(0xFFE2E8F0),
        darkSecondary = Color(0xFF94A3B8),
        darkBackground = Color(0xFF090A0D),
        darkSurface = Color(0xFF13151A),
        darkSurfaceVariant = Color(0xFF1E2129),
        darkOutline = Color(0xFF2E3340),
        lightPrimary = Color(0xFF13151A),
        lightSecondary = Color(0xFF475569),
        lightBackground = Color(0xFFF4F5F7),
        lightSurface = Color(0xFFFFFFFF),
        lightSurfaceVariant = Color(0xFFE4E7EC),
        lightOutline = Color(0xFFCBD5E1)
    ),
    BLUE(
        id = "blue",
        nameFa = "آبی لاجوردی",
        nameEn = "Azure Blue",
        darkPrimary = Color(0xFF60A5FA),
        darkSecondary = Color(0xFF38BDF8),
        darkBackground = Color(0xFF0B111E),
        darkSurface = Color(0xFF121D30),
        darkSurfaceVariant = Color(0xFF1E2E4A),
        darkOutline = Color(0xFF2A4269),
        lightPrimary = Color(0xFF1D4ED8),
        lightSecondary = Color(0xFF0284C7),
        lightBackground = Color(0xFFF0F6FF),
        lightSurface = Color(0xFFFFFFFF),
        lightSurfaceVariant = Color(0xFFE0EDFE),
        lightOutline = Color(0xFFBAE6FD)
    ),
    GREEN(
        id = "green",
        nameFa = "سبز زمردی",
        nameEn = "Emerald Green",
        darkPrimary = Color(0xFF4ADE80),
        darkSecondary = Color(0xFF34D399),
        darkBackground = Color(0xFF0A1610),
        darkSurface = Color(0xFF12241C),
        darkSurfaceVariant = Color(0xFF1C362A),
        darkOutline = Color(0xFF2A503F),
        lightPrimary = Color(0xFF15803D),
        lightSecondary = Color(0xFF059669),
        lightBackground = Color(0xFFF0FDF4),
        lightSurface = Color(0xFFFFFFFF),
        lightSurfaceVariant = Color(0xFFDCFCE7),
        lightOutline = Color(0xFFBBF7D0)
    ),
    PURPLE(
        id = "purple",
        nameFa = "بنفش شاهانه",
        nameEn = "Royal Purple",
        darkPrimary = Color(0xFFC084FC),
        darkSecondary = Color(0xFFA855F7),
        darkBackground = Color(0xFF130B1C),
        darkSurface = Color(0xFF20132E),
        darkSurfaceVariant = Color(0xFF2E1C42),
        darkOutline = Color(0xFF442B61),
        lightPrimary = Color(0xFF7E22CE),
        lightSecondary = Color(0xFF9333EA),
        lightBackground = Color(0xFFFAF5FF),
        lightSurface = Color(0xFFFFFFFF),
        lightSurfaceVariant = Color(0xFFF3E8FF),
        lightOutline = Color(0xFFE9D5FF)
    ),
    PINK(
        id = "pink",
        nameFa = "صورتی گلبهی",
        nameEn = "Rose Pink",
        darkPrimary = Color(0xFFF472B6),
        darkSecondary = Color(0xFFFB7185),
        darkBackground = Color(0xFF1A0B14),
        darkSurface = Color(0xFF2A1322),
        darkSurfaceVariant = Color(0xFF3B1C30),
        darkOutline = Color(0xFF552A47),
        lightPrimary = Color(0xFFBE185D),
        lightSecondary = Color(0xFFE11D48),
        lightBackground = Color(0xFFFFF1F2),
        lightSurface = Color(0xFFFFFFFF),
        lightSurfaceVariant = Color(0xFFFCE7F3),
        lightOutline = Color(0xFFFECDD3)
    ),
    ORANGE(
        id = "orange",
        nameFa = "نارنجی کهربایی",
        nameEn = "Amber Orange",
        darkPrimary = Color(0xFFFB923C),
        darkSecondary = Color(0xFFFBBF24),
        darkBackground = Color(0xFF1A1108),
        darkSurface = Color(0xFF291B0E),
        darkSurfaceVariant = Color(0xFF3B2715),
        darkOutline = Color(0xFF57391F),
        lightPrimary = Color(0xFFC2410C),
        lightSecondary = Color(0xFFD97706),
        lightBackground = Color(0xFFFFF7ED),
        lightSurface = Color(0xFFFFFFFF),
        lightSurfaceVariant = Color(0xFFFFEDD5),
        lightOutline = Color(0xFFFED7AA)
    ),
    RED(
        id = "red",
        nameFa = "یاقوتی گرم",
        nameEn = "Ruby Red",
        darkPrimary = Color(0xFFF87171),
        darkSecondary = Color(0xFFFB7185),
        darkBackground = Color(0xFF1A0A0B),
        darkSurface = Color(0xFF2B1214),
        darkSurfaceVariant = Color(0xFF3D1B1E),
        darkOutline = Color(0xFF5A272B),
        lightPrimary = Color(0xFFB91C1C),
        lightSecondary = Color(0xFFDC2626),
        lightBackground = Color(0xFFFEF2F2),
        lightSurface = Color(0xFFFFFFFF),
        lightSurfaceVariant = Color(0xFFFEE2E2),
        lightOutline = Color(0xFFFECACA)
    ),
    TEAL(
        id = "teal",
        nameFa = "فیروزه‌ای پارسی",
        nameEn = "Persian Teal",
        darkPrimary = Color(0xFF2DD4BF),
        darkSecondary = Color(0xFF38BDF8),
        darkBackground = Color(0xFF091616),
        darkSurface = Color(0xFF112324),
        darkSurfaceVariant = Color(0xFF193436),
        darkOutline = Color(0xFF264C4F),
        lightPrimary = Color(0xFF0F766E),
        lightSecondary = Color(0xFF0891B2),
        lightBackground = Color(0xFFF0FDFA),
        lightSurface = Color(0xFFFFFFFF),
        lightSurfaceVariant = Color(0xFFCCFBF1),
        lightOutline = Color(0xFF99F6E4)
    ),
    GRAY(
        id = "gray",
        nameFa = "خاکستری گرافیتی",
        nameEn = "Graphite Gray",
        darkPrimary = Color(0xFFCBD5E1),
        darkSecondary = Color(0xFF94A3B8),
        darkBackground = Color(0xFF101216),
        darkSurface = Color(0xFF181B20),
        darkSurfaceVariant = Color(0xFF242830),
        darkOutline = Color(0xFF363C48),
        lightPrimary = Color(0xFF334155),
        lightSecondary = Color(0xFF475569),
        lightBackground = Color(0xFFF8FAFC),
        lightSurface = Color(0xFFFFFFFF),
        lightSurfaceVariant = Color(0xFFE2E8F0),
        lightOutline = Color(0xFFCBD5E1)
    ),
    MINIMAL(
        id = "minimal",
        nameFa = "مینیمال مونوسیاه",
        nameEn = "Minimal Mono",
        darkPrimary = Color(0xFFFFFFFF),
        darkSecondary = Color(0xFFA1A1AA),
        darkBackground = Color(0xFF000000),
        darkSurface = Color(0xFF121212),
        darkSurfaceVariant = Color(0xFF1E1E1E),
        darkOutline = Color(0xFF333333),
        lightPrimary = Color(0xFF000000),
        lightSecondary = Color(0xFF52525B),
        lightBackground = Color(0xFFFFFFFF),
        lightSurface = Color(0xFFF4F4F5),
        lightSurfaceVariant = Color(0xFFE4E4E7),
        lightOutline = Color(0xFFD4D4D8)
    );

    val primary: Color get() = darkPrimary

    fun getLocalizedName(strings: com.example.core.i18n.AppStrings.Strings): String {
        return when (this) {
            METALLIC_BLACK -> strings.themeMetallicBlack
            BLUE -> strings.themeBlue
            GREEN -> strings.themeGreen
            PURPLE -> strings.themePurple
            PINK -> strings.themePink
            ORANGE -> strings.themeOrange
            RED -> strings.themeRed
            TEAL -> strings.themeTeal
            GRAY -> strings.themeGray
            MINIMAL -> strings.themeMinimal
        }
    }

    companion object {
        fun fromId(id: String): AppThemePreset {
            return entries.firstOrNull { it.id.equals(id, ignoreCase = true) } ?: METALLIC_BLACK
        }
    }
}
