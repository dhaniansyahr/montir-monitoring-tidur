package com.meone.montir.helper.music

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.meone.montir.R
import com.meone.montir.view.music.DetailMusicActivity

class MusicService : Service() {

    private val binder = MusicBinder()
    private lateinit var mediaPlayer: MediaPlayer
    private var isPrepared = false
    private val CHANNEL_ID = "MusicServiceChannel"

    // Audio focus listener
    private val afChangeListener = AudioManager.OnAudioFocusChangeListener { focusChange ->
        when (focusChange) {
            AudioManager.AUDIOFOCUS_GAIN -> {
                // Resume playback
                if (!mediaPlayer.isPlaying && isPrepared) {
                    mediaPlayer.start()
                }
            }
            AudioManager.AUDIOFOCUS_LOSS -> {
                // Lost audio focus, stop playback
                if (mediaPlayer.isPlaying) {
                    mediaPlayer.pause()
                }
            }
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> {
                // Temporary loss of audio focus, pause playback
                if (mediaPlayer.isPlaying) {
                    mediaPlayer.pause()
                }
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        mediaPlayer = MediaPlayer()
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        mediaPlayer.setOnErrorListener { mp, what, extra ->
            Log.e("MusicService", "MediaPlayer error: what=$what, extra=$extra")
            false
        }
    }

    @SuppressLint("ForegroundServiceType")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val musicUrl = intent?.getStringExtra("MUSIC_URL")

        Log.d("MusicService", "Received music URL: $musicUrl")

        musicUrl?.let {
            Log.d("MusicService", "Received music URL: $it")
            try {
                mediaPlayer.reset()
                mediaPlayer.setDataSource(it)
                mediaPlayer.prepareAsync()
                mediaPlayer.setOnPreparedListener {
                    Log.d("MusicService", "MediaPlayer prepared, starting playback")
                    isPrepared = true

                    // Request audio focus
                    val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
                    val result = audioManager.requestAudioFocus(
                        afChangeListener,
                        AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN
                    )

                    if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                        mediaPlayer.start()
                        startForeground(1, createNotification())
                    } else {
                        Log.e("MusicService", "Failed to gain audio focus")
                        stopSelf()
                    }
                }

                mediaPlayer.setOnCompletionListener {
                    val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
                    audioManager.abandonAudioFocus(afChangeListener)
                    stopSelf()
                }

                // Error listener for MediaPlayer
                mediaPlayer.setOnErrorListener { mp, what, extra ->
                    Log.e("MusicService", "MediaPlayer error: what=$what, extra=$extra")
                    // Handle the error appropriately (e.g., notify the user, stop the service)
                    true // Return true to indicate that you've handled the error
                }

            } catch (e: Exception) {
                Log.e("MusicService", "Error setting data source", e)
                stopSelf()
            }
        }
        return START_STICKY
    }

        override fun onDestroy() {
            super.onDestroy()
            if (mediaPlayer.isPlaying) {
                mediaPlayer.stop()
            }
            mediaPlayer.release()

            // Release audio focus
            val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
            audioManager.abandonAudioFocus(afChangeListener)
        }

        fun pauseMusic() {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
            }
        }

        fun resumeMusic() {
            if (!mediaPlayer.isPlaying && isPrepared) {
                mediaPlayer.start()
            }
        }

        fun seekTo(position: Int) {
            if (isPrepared) {
                mediaPlayer.seekTo(position)
            }
        }

    fun getDuration(): Int {
        return if (isPrepared) {
            mediaPlayer.duration
        } else {
            0
        }
    }

    fun getCurrentPosition(): Int {
        return if (isPrepared) {
            mediaPlayer.currentPosition
        } else {
            0
            }
        }

        fun isPlaying(): Boolean {
            return mediaPlayer.isPlaying
        }

        private fun createNotificationChannel() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    CHANNEL_ID,
                    "Music Service Channel",
                    NotificationManager.IMPORTANCE_LOW
                )
                val manager = getSystemService(NotificationManager::class.java)
                manager.createNotificationChannel(channel)
            }
        }

        private fun createNotification(): Notification {
            val notificationIntent = Intent(this, DetailMusicActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(
                this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE
            )

            return NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Playing Music")
                .setContentText("Your music is playing")
                .setSmallIcon(R.drawable.music_note)
                .setContentIntent(pendingIntent)
                .build()
        }

        inner class MusicBinder : Binder() {
            fun getService(): MusicService = this@MusicService
        }
    }
