package com.example.media

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Build
import androidx.core.content.ContextCompat
import java.io.File
import java.io.IOException

class AudioRecorderHelper(private val context: Context) {

    companion object {
        private const val REQUEST_RECORD_AUDIO = 7001
    }

    private var mediaRecorder: MediaRecorder? = null

    var currentFilePath: String? = null
        private set

    var isRecording: Boolean = false
        private set

    fun startRecording(): String? {
        if (!hasRecordAudioPermission()) {
            requestRecordAudioPermission()
            return null
        }

        return try {
            val audioDir = File(context.filesDir, "audio")

            if (!audioDir.exists()) {
                audioDir.mkdirs()
            }

            val file = File(
                audioDir,
                "voice_${System.currentTimeMillis()}.m4a"
            )

            currentFilePath = file.absolutePath

            val recorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                MediaRecorder(context)
            } else {
                @Suppress("DEPRECATION")
                MediaRecorder()
            }

            recorder.apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setAudioEncodingBitRate(128000)
                setAudioSamplingRate(44100)
                setOutputFile(file.absolutePath)

                prepare()
                start()
            }

            mediaRecorder = recorder
            isRecording = true

            currentFilePath

        } catch (e: SecurityException) {
            e.printStackTrace()
            mediaRecorder?.release()
            mediaRecorder = null
            isRecording = false
            null

        } catch (e: IOException) {
            e.printStackTrace()
            mediaRecorder?.release()
            mediaRecorder = null
            isRecording = false
            null

        } catch (e: IllegalStateException) {
            e.printStackTrace()
            mediaRecorder?.release()
            mediaRecorder = null
            isRecording = false
            null

        } catch (e: Exception) {
            e.printStackTrace()
            mediaRecorder?.release()
            mediaRecorder = null
            isRecording = false
            null
        }
    }

    fun stopRecording(): String? {
        if (!isRecording) {
            return currentFilePath
        }

        val savedPath = currentFilePath

        try {
            mediaRecorder?.stop()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                mediaRecorder?.release()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            mediaRecorder = null
            isRecording = false
        }

        return savedPath
    }

    private fun hasRecordAudioPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestRecordAudioPermission() {
        val activity = context as? Activity ?: return

        activity.requestPermissions(
            arrayOf(Manifest.permission.RECORD_AUDIO),
            REQUEST_RECORD_AUDIO
        )
    }
}
