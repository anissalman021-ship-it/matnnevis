package com.example.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.core.config.AppConfig
import com.example.core.i18n.Language
import com.example.ui.theme.AppThemePreset
import com.example.ui.theme.TextSizeScale
import com.example.ui.theme.ThemeMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = AppConfig.PREFERENCES_NAME)

data class UserPreferences(
    val themePreset: AppThemePreset = AppThemePreset.METALLIC_BLACK,
    val themeMode: ThemeMode = ThemeMode.DARK,
    val textSizeScale: TextSizeScale = TextSizeScale.MEDIUM,
    val language: Language = Language.FA,
    val wordCounterEnabled: Boolean = true,
    val autoSaveEnabled: Boolean = true,
    val defaultNoteColor: String = "default",
    val appLockPin: String = "",
    val isAppLockEnabled: Boolean = false,
    val floatingModeEnabled: Boolean = false,
    val showDashboard: Boolean = true,
    val onboardingDone: Boolean = false,
    val defaultSort: String = "newest"
)

class AppPreferences(private val context: Context) {

    private object PreferencesKeys {
        val THEME_PRESET = stringPreferencesKey("theme_preset")
        val THEME_MODE = stringPreferencesKey("theme_mode")
        val TEXT_SIZE = stringPreferencesKey("text_size")
        val LANGUAGE = stringPreferencesKey("language")
        val WORD_COUNTER = booleanPreferencesKey("word_counter")
        val AUTO_SAVE = booleanPreferencesKey("auto_save")
        val DEFAULT_NOTE_COLOR = stringPreferencesKey("default_note_color")
        val APP_LOCK_PIN = stringPreferencesKey("app_lock_pin")
        val IS_APP_LOCK_ENABLED = booleanPreferencesKey("is_app_lock_enabled")
        val FLOATING_MODE_ENABLED = booleanPreferencesKey("floating_mode_enabled")
        val SHOW_DASHBOARD = booleanPreferencesKey("show_dashboard")
        val ONBOARDING_DONE = booleanPreferencesKey("onboarding_done")
        val DEFAULT_SORT = stringPreferencesKey("default_sort")
    }

    val userPreferencesFlow: Flow<UserPreferences> = context.dataStore.data.map { preferences ->
        val themePresetStr = preferences[PreferencesKeys.THEME_PRESET] ?: AppThemePreset.METALLIC_BLACK.id
        val themeModeStr = preferences[PreferencesKeys.THEME_MODE] ?: ThemeMode.DARK.id
        val textSizeStr = preferences[PreferencesKeys.TEXT_SIZE] ?: TextSizeScale.MEDIUM.id
        val languageStr = preferences[PreferencesKeys.LANGUAGE] ?: Language.FA.code

        UserPreferences(
            themePreset = AppThemePreset.fromId(themePresetStr),
            themeMode = ThemeMode.fromId(themeModeStr),
            textSizeScale = TextSizeScale.fromId(textSizeStr),
            language = Language.fromCode(languageStr),
            wordCounterEnabled = preferences[PreferencesKeys.WORD_COUNTER] ?: true,
            autoSaveEnabled = preferences[PreferencesKeys.AUTO_SAVE] ?: true,
            defaultNoteColor = preferences[PreferencesKeys.DEFAULT_NOTE_COLOR] ?: "default",
            appLockPin = preferences[PreferencesKeys.APP_LOCK_PIN] ?: "",
            isAppLockEnabled = preferences[PreferencesKeys.IS_APP_LOCK_ENABLED] ?: false,
            floatingModeEnabled = preferences[PreferencesKeys.FLOATING_MODE_ENABLED] ?: false,
            showDashboard = preferences[PreferencesKeys.SHOW_DASHBOARD] ?: true,
            onboardingDone = preferences[PreferencesKeys.ONBOARDING_DONE] ?: false,
            defaultSort = preferences[PreferencesKeys.DEFAULT_SORT] ?: "newest"
        )
    }

    suspend fun setThemePreset(preset: AppThemePreset) {
        context.dataStore.edit { it[PreferencesKeys.THEME_PRESET] = preset.id }
    }

    suspend fun setThemeMode(mode: ThemeMode) {
        context.dataStore.edit { it[PreferencesKeys.THEME_MODE] = mode.id }
    }

    suspend fun setTextSizeScale(scale: TextSizeScale) {
        context.dataStore.edit { it[PreferencesKeys.TEXT_SIZE] = scale.id }
    }

    suspend fun setLanguage(language: Language) {
        context.dataStore.edit { it[PreferencesKeys.LANGUAGE] = language.code }
    }

    suspend fun setWordCounterEnabled(enabled: Boolean) {
        context.dataStore.edit { it[PreferencesKeys.WORD_COUNTER] = enabled }
    }

    suspend fun setAutoSaveEnabled(enabled: Boolean) {
        context.dataStore.edit { it[PreferencesKeys.AUTO_SAVE] = enabled }
    }

    suspend fun setDefaultNoteColor(colorId: String) {
        context.dataStore.edit { it[PreferencesKeys.DEFAULT_NOTE_COLOR] = colorId }
    }

    suspend fun setAppLock(pin: String, enabled: Boolean) {
        context.dataStore.edit {
            it[PreferencesKeys.APP_LOCK_PIN] = pin
            it[PreferencesKeys.IS_APP_LOCK_ENABLED] = enabled
        }
    }

    suspend fun setFloatingModeEnabled(enabled: Boolean) {
        context.dataStore.edit { it[PreferencesKeys.FLOATING_MODE_ENABLED] = enabled }
    }

    suspend fun setShowDashboard(show: Boolean) {
        context.dataStore.edit { it[PreferencesKeys.SHOW_DASHBOARD] = show }
    }

    suspend fun setOnboardingDone(done: Boolean) {
        context.dataStore.edit { it[PreferencesKeys.ONBOARDING_DONE] = done }
    }

    suspend fun setDefaultSort(sort: String) {
        context.dataStore.edit { it[PreferencesKeys.DEFAULT_SORT] = sort }
    }
}
