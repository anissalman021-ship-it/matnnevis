package com.example.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Home : Screen("home")
    object Editor : Screen("editor/{noteId}") {
        fun createRoute(noteId: Long = 0L) = "editor/$noteId"
    }
    object FocusMode : Screen("focus/{noteId}") {
        fun createRoute(noteId: Long = 0L) = "focus/$noteId"
    }
    object ReadingMode : Screen("reading/{noteId}") {
        fun createRoute(noteId: Long = 0L) = "reading/$noteId"
    }
    object Favorites : Screen("favorites")
    object Archive : Screen("archive")
    object Trash : Screen("trash")
    object Categories : Screen("categories")
    object Calendar : Screen("calendar")
    object Settings : Screen("settings")
    object About : Screen("about")
    object PinLock : Screen("pin_lock")
}
