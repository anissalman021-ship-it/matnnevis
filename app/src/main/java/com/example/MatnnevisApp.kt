package com.example

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.example.core.config.AppConfig
import com.example.data.local.AppDatabase
import com.example.data.local.AppPreferences
import com.example.data.repository.NoteRepository

class MatnnevisApp : Application() {

    lateinit var database: AppDatabase
        private set

    lateinit var repository: NoteRepository
        private set

    lateinit var preferences: AppPreferences
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this

        database = AppDatabase.getInstance(this)
        repository = NoteRepository(
            noteDao = database.noteDao(),
            categoryDao = database.categoryDao(),
            revisionDao = database.noteRevisionDao()
        )
        preferences = AppPreferences(this)

        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val reminderChannel = NotificationChannel(
                CHANNEL_REMINDERS,
                "یادآورهای متنویس",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "اعلان‌های یادآوری یادداشت‌های متنویس"
                enableVibration(true)
            }

            val overlayChannel = NotificationChannel(
                CHANNEL_OVERLAY,
                "حالت شناور متنویس",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "سرویس حباب شناور یادداشت سریع"
            }

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(reminderChannel)
            notificationManager?.createNotificationChannel(overlayChannel)
        }
    }

    companion object {
        const val CHANNEL_REMINDERS = "matnnevis_reminders_channel"
        const val CHANNEL_OVERLAY = "matnnevis_overlay_channel"

        lateinit var instance: MatnnevisApp
            private set
    }
}
