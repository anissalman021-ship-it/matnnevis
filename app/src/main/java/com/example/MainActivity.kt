package com.example

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.compose.rememberNavController
import com.example.core.i18n.AppStrings
import com.example.core.i18n.LocalAppStrings
import com.example.receiver.ReminderReceiver
import com.example.ui.navigation.AppNavGraph
import com.example.ui.navigation.Screen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val notificationNoteId = intent?.getLongExtra(ReminderReceiver.EXTRA_NOTE_ID, -1L) ?: -1L

        setContent {
            val userPrefs by viewModel.userPreferences.collectAsState()
            val currentStrings = AppStrings.get(userPrefs.language)
            val layoutDir = if (userPrefs.language.isRtl) LayoutDirection.Rtl else LayoutDirection.Ltr

            // Update Android Locale & Configuration for System / Context access
            LaunchedEffect(userPrefs.language) {
                val locale = java.util.Locale(userPrefs.language.code)
                java.util.Locale.setDefault(locale)
                val config = resources.configuration
                config.setLocale(locale)
                config.setLayoutDirection(locale)
                @Suppress("DEPRECATION")
                resources.updateConfiguration(config, resources.displayMetrics)
            }

            // Global Toast messages listener with language awareness
            LaunchedEffect(userPrefs.language) {
                viewModel.messageEvent.collect { messageResolver ->
                    val text = messageResolver(currentStrings)
                    Toast.makeText(this@MainActivity, text, Toast.LENGTH_SHORT).show()
                }
            }

            CompositionLocalProvider(
                LocalLayoutDirection provides layoutDir,
                LocalAppStrings provides currentStrings
            ) {
                MyApplicationTheme(
                    themePreset = userPrefs.themePreset,
                    themeMode = userPrefs.themeMode,
                    textScale = userPrefs.textSizeScale.multiplier
                ) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        val navController = rememberNavController()

                        LaunchedEffect(notificationNoteId) {
                            if (notificationNoteId > 0) {
                                navController.navigate(Screen.Editor.createRoute(notificationNoteId))
                            }
                        }

                        key(userPrefs.language) {
                            AppNavGraph(
                                navController = navController,
                                viewModel = viewModel
                            )
                        }
                    }
                }
            }
        }
    }
}
