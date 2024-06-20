package com.meone.montir.view.music

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.util.Log
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.meone.montir.R
import com.meone.montir.databinding.ActivityDetailMusicBinding
import com.meone.montir.helper.music.MusicService

class DetailMusicActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailMusicBinding
    private var musicService: MusicService? = null
    private var isBound = false
    private var isPlaying = false
    private lateinit var musicUrl: String
    private var durationInMillis = 0L
    private val handler = Handler()
    private var countDownRunnable: Runnable? = null

    private val TAG = "DetailMusicActivity"

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.d(TAG, "onServiceConnected")
            val binder = service as MusicService.MusicBinder
            musicService = binder.getService()
            isBound = true
            updateUI() // Update UI when service is connected
            binding.apply {
                if (musicService!!.isPlaying()) {
                    playPauseBtn.setImageResource(R.drawable.pause)
                    countDownRunnable?.let { handler.post(it) }
                    handler.post(updateSeekBarRunnable)
                } else {
                    playPauseBtn.setImageResource(R.drawable.play_arrow)
                    countDownRunnable?.let { handler.removeCallbacks(it) }
                    handler.removeCallbacks(updateSeekBarRunnable)
                }
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d(TAG, "onServiceDisconnected")
            musicService = null
            isBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMusicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get data from intent
        val musicName = intent.getStringExtra("MUSIC_NAME").toString()
        val musicDurationString = intent.getStringExtra("MUSIC_DURATION").toString()
        musicUrl = intent.getStringExtra("MUSIC_URL").toString()

        if (musicName.isEmpty() || musicDurationString.isEmpty() || musicUrl.isEmpty()) {
            Log.e(TAG, "onCreate: Data not found")
            return
        }

        // Convert duration string (HH:MM:SS) to milliseconds
        durationInMillis = convertDurationToMillis(musicDurationString)

        // Initialize UI components
        binding.apply {
            tvMusicTitle.text = musicName
            tvMusicDuration.text = formatMillisToTime(durationInMillis)
            sbDurationMusic.max = durationInMillis.toInt()

            // SeekBar listener
            sbDurationMusic.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    if (fromUser) {
                        musicService?.seekTo(progress)
                        updateCountdownTimer(durationInMillis - progress.toLong())
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    countDownRunnable?.let { handler.removeCallbacks(it) }
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    countDownRunnable?.let { handler.post(it) }
                }
            })

            // Play/Pause button click listener
            playPauseBtn.setOnClickListener {
                if (isBound) {
                    Log.d(TAG, "onCreate: isBound = $isBound")
                    if (isPlaying) {
                        musicService?.pauseMusic()
                    } else {
                        musicService?.resumeMusic()
                    }
                    isPlaying = !isPlaying
                    updateUI()
                } else {
                    Log.d(TAG, "onCreate Start Music Service: isBound = $isBound")
                    startMusicService()
                }
            }
        }
    }

    private val updateSeekBarRunnable = object : Runnable {
        override fun run() {
            if (isPlaying && musicService?.isPlaying() == true) {
                binding.sbDurationMusic.progress = musicService?.getCurrentPosition() ?: 0
                handler.postDelayed(this, 1000L) // Update every second
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (isBound) {
            updateUI()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isBound) {
            unbindService(serviceConnection)
            isBound = false
        }
        countDownRunnable?.let { handler.removeCallbacks(it) }
        handler.removeCallbacks(updateSeekBarRunnable)
    }

//    KEsalahan Pada Saat Music Service tetap Null HArus Cari Cara Buat Dapetin Music Service Tidak Null
    private fun startMusicService() {
        val serviceIntent = Intent(this, MusicService::class.java).apply {
            putExtra("MUSIC_URL", musicUrl)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d(TAG, "Music Service : $musicService")
            ContextCompat.startForegroundService(this, serviceIntent)
        } else {
            Log.d(TAG, "startMusicService: Build.VERSION.SDK_INT < Build.VERSION_CODES.O")
            startService(serviceIntent)
        }
    }

    private fun updateUI() {
        Log.d(TAG, "updateUI: isBound = $isBound")
        if (isBound && musicService != null) {
            binding.apply {
                if (musicService!!.isPlaying()) {
                    playPauseBtn.setImageResource(R.drawable.pause)
                    countDownRunnable?.let { handler.post(it) }
                    handler.post(updateSeekBarRunnable)
                } else {
                    playPauseBtn.setImageResource(R.drawable.play_arrow)
                    countDownRunnable?.let { handler.removeCallbacks(it) }
                    handler.removeCallbacks(updateSeekBarRunnable)
                }
            }
        } else {
            Log.e(TAG, "updateUI: MusicService or binding not initialized")
        }
    }

    private fun updateCountdownTimer(remainingMillis: Long) {
        countDownRunnable = object : Runnable {
            override fun run() {
                if (isPlaying && musicService?.isPlaying() == true) {
                    val currentPosition = durationInMillis - remainingMillis
                    binding.sbDurationMusic.progress = currentPosition.toInt()
                    binding.tvMusicDuration.text = formatMillisToTime(currentPosition)
                    if (remainingMillis > 0) {
                        handler.postDelayed(this, 1000)
                    }
                }
            }
        }
        handler.post(countDownRunnable as Runnable)
    }

    private fun convertDurationToMillis(duration: String): Long {
        val parts = duration.split(":")
        if (parts.size == 3) {
            val hours = parts[0].toLongOrNull() ?: 0
            val minutes = parts[1].toLongOrNull() ?: 0
            val seconds = parts[2].toLongOrNull() ?: 0
            return hours * 3600000 + minutes * 60000 + seconds * 1000
        }
        return 0
    }

    private fun formatMillisToTime(millis: Long): String {
        val hours = millis / (1000 * 60 * 60)
        val minutes = millis % (1000 * 60 * 60) / (1000 * 60)
        val seconds = millis % (1000 * 60) / 1000
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }
}
