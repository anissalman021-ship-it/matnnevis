package com.example.media

import android.media.MediaPlayer
import java.io.IOException

class AudioPlayerHelper {
    private var mediaPlayer: MediaPlayer? = null
    var isPlaying: Boolean = false
        private set

    fun play(filePath: String, onCompletion: () -> Unit) {
        stop()
        mediaPlayer = MediaPlayer().apply {
            try {
                setDataSource(filePath)
                prepare()
                start()
                this@AudioPlayerHelper.isPlaying = true
                setOnCompletionListener {
                    this@AudioPlayerHelper.isPlaying = false
                    onCompletion()
                }
            } catch (e: IOException) {
                e.printStackTrace()
                this@AudioPlayerHelper.isPlaying = false
                release()
            }
        }
    }

    fun stop() {
        try {
            mediaPlayer?.apply {
                if (isPlaying) {
                    stop()
                }
                release()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            mediaPlayer = null
            isPlaying = false
        }
    }
}
