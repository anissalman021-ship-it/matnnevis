package com.example.media

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import java.io.File
import java.io.IOException

class AudioRecorderHelper(private val context: Context) {
    private var mediaRecorder: MediaRecorder? = null
    var currentFilePath: String? = null
        private set
    var isRecording: Boolean = false
        private set

    fun startRecording(): String? {
        val audioDir = File(context.filesDir, "audio")
        if (!audioDir.exists()) {
            audioDir.mkdirs()
        }
        val file = File(audioDir, "voice_${System.currentTimeMillis()}.m4a")
        currentFilePath = file.absolutePath

        mediaRecorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(context)
        } else {
            @Suppress("DEPRECATION")
            MediaRecorder()
        }.apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setAudioEncodingBitRate(128000)
            setAudioSamplingRate(44100)
            setOutputFile(file.absolutePath)
            try {
                prepare()
                start()
                isRecording = true
            } catch (e: IOException) {
                e.printStackTrace()
                isRecording = false
                release()
                return null
            } catch (e: IllegalStateException) {
                e.printStackTrace()
                isRecording = false
                release()
                return null
            }
        }
        return currentFilePath
    }

    fun stopRecording(): String? {
        if (!isRecording) return currentFilePath
        try {
            mediaRecorder?.apply {
                stop()
                release()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            mediaRecorder = null
            isRecording = false
        }
        return currentFilePath
    }
}
