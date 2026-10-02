package com.example.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.ui.screens.archive.ArchiveScreen
import com.example.ui.screens.calendar.CalendarScreen
import com.example.ui.screens.category.CategoryScreen
import com.example.ui.screens.editor.FocusModeScreen
import com.example.ui.screens.editor.NoteEditorScreen
import com.example.ui.screens.editor.ReadingModeScreen
import com.example.ui.screens.favorites.FavoritesScreen
import com.example.ui.screens.home.HomeScreen
import com.example.ui.screens.security.PinLockScreen
import com.example.ui.screens.settings.AboutScreen
import com.example.ui.screens.settings.SettingsScreen
import com.example.ui.screens.splash.SplashScreen
import com.example.ui.screens.trash.TrashScreen
import com.example.ui.viewmodel.MainViewModel

@Composable
fun AppNavGraph(
    navController: NavHostController,
    viewModel: MainViewModel
) {
    val prefs by viewModel.userPreferences.collectAsState()
    val isAppUnlocked by viewModel.isAppUnlocked.collectAsState()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        // Splash
        composable(Screen.Splash.route) {
            SplashScreen(
                onSplashFinished = {
                    if (prefs.isAppLockEnabled && prefs.appLockPin.isNotBlank() && !isAppUnlocked) {
                        navController.navigate(Screen.PinLock.route) {
                            popUpTo(Screen.Splash.route) { inclusive = true }
                        }
                    } else {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Splash.route) { inclusive = true }
                        }
                    }
                }
            )
        }

        // PIN Lock
        composable(Screen.PinLock.route) {
            PinLockScreen(
                viewModel = viewModel,
                onUnlocked = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.PinLock.route) { inclusive = true }
                    }
                }
            )
        }

        // Home
        composable(Screen.Home.route) {
            HomeScreen(
                viewModel = viewModel,
                onNavigateToEditor = { noteId ->
                    navController.navigate(Screen.Editor.createRoute(noteId))
                },
                onNavigateToFavorites = { navController.navigate(Screen.Favorites.route) },
                onNavigateToArchive = { navController.navigate(Screen.Archive.route) },
                onNavigateToTrash = { navController.navigate(Screen.Trash.route) },
                onNavigateToCategories = { navController.navigate(Screen.Categories.route) },
                onNavigateToCalendar = { navController.navigate(Screen.Calendar.route) },
                onNavigateToSettings = { navController.navigate(Screen.Settings.route) }
            )
        }

        // Note Editor
        composable(
            route = Screen.Editor.route,
            arguments = listOf(navArgument("noteId") { type = NavType.LongType })
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getLong("noteId") ?: 0L
            NoteEditorScreen(
                noteId = noteId,
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToFocus = { id -> navController.navigate(Screen.FocusMode.createRoute(id)) },
                onNavigateToReading = { id -> navController.navigate(Screen.ReadingMode.createRoute(id)) }
            )
        }

        // Focus Mode
        composable(
            route = Screen.FocusMode.route,
            arguments = listOf(navArgument("noteId") { type = NavType.LongType })
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getLong("noteId") ?: 0L
            FocusModeScreen(
                noteId = noteId,
                viewModel = viewModel,
                onExitFocus = { navController.popBackStack() }
            )
        }

        // Reading Mode
        composable(
            route = Screen.ReadingMode.route,
            arguments = listOf(navArgument("noteId") { type = NavType.LongType })
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getLong("noteId") ?: 0L
            ReadingModeScreen(
                noteId = noteId,
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // Favorites
        composable(Screen.Favorites.route) {
            FavoritesScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToEditor = { noteId ->
                    navController.navigate(Screen.Editor.createRoute(noteId))
                }
            )
        }

        // Archive
        composable(Screen.Archive.route) {
            ArchiveScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToEditor = { noteId ->
                    navController.navigate(Screen.Editor.createRoute(noteId))
                }
            )
        }

        // Trash
        composable(Screen.Trash.route) {
            TrashScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // Categories
        composable(Screen.Categories.route) {
            CategoryScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // Calendar
        composable(Screen.Calendar.route) {
            CalendarScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToEditor = { noteId ->
                    navController.navigate(Screen.Editor.createRoute(noteId))
                }
            )
        }

        // Settings
        composable(Screen.Settings.route) {
            SettingsScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToAbout = { navController.navigate(Screen.About.route) }
            )
        }

        // About
        composable(Screen.About.route) {
            AboutScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
