package com.example.core.i18n

enum class Language(val code: String, val displayName: String, val isRtl: Boolean) {
    FA("fa", "فارسی", true),
    EN("en", "English", false),
    AR("ar", "العربية", true),
    TR("tr", "Türkçe", false),
    ES("es", "Español", false),
    FR("fr", "Français", false),
    DE("de", "Deutsch", false),
    RU("ru", "Русский", false);

    companion object {
        fun fromCode(code: String): Language {
            return entries.firstOrNull { it.code.equals(code, ignoreCase = true) } ?: FA
        }
    }
}
