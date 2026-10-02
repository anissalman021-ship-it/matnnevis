package com.example.receiver

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.MainActivity
import com.example.MatnnevisApp
import com.example.R
import com.example.core.config.AppConfig
import com.example.core.i18n.AppStrings
import com.example.core.i18n.Language
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class ReminderReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val noteId = intent.getLongExtra(EXTRA_NOTE_ID, -1L)
        val noteTitle = intent.getStringExtra(EXTRA_NOTE_TITLE) ?: AppConfig.APP_NAME
        val noteContent = intent.getStringExtra(EXTRA_NOTE_CONTENT) ?: ""

        val mainIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(EXTRA_NOTE_ID, noteId)
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            noteId.toInt(),
            mainIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val strings: AppStrings.Strings = try {
            val prefs = runBlocking {
                MatnnevisApp.instance.preferences.userPreferencesFlow.first()
            }
            AppStrings.get(prefs.language)
        } catch (e: Exception) {
            AppStrings.get(Language.FA)
        }

        val notification = NotificationCompat.Builder(context, MatnnevisApp.CHANNEL_REMINDERS)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("${strings.reminder} ${strings.appName}: $noteTitle")
            .setContentText(noteContent.ifBlank { strings.reminder })
            .setStyle(NotificationCompat.BigTextStyle().bigText(noteContent))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        try {
            val notificationManager = NotificationManagerCompat.from(context)
            notificationManager.notify(noteId.toInt(), notification)
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    companion object {
        const val EXTRA_NOTE_ID = "extra_note_id"
        const val EXTRA_NOTE_TITLE = "extra_note_title"
        const val EXTRA_NOTE_CONTENT = "extra_note_content"
    }
}
