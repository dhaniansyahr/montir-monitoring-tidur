package com.meone.montir.view.statistic

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.meone.montir.R
import com.meone.montir.databinding.ActivitySleepScoreBinding
import com.meone.montir.view.music.MusicActivity
import com.meone.montir.view.sleep.SleepTrackerActivity
import com.meone.montir.view.profile.ProfileActivity
import com.meone.montir.viewModel.ViewModelFactory
import com.meone.montir.viewModel.music.DetailMusicViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class SleepScoreActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySleepScoreBinding
    private lateinit var mSleepScore: TextView
    private lateinit var mSleepDur: TextView
    private lateinit var mWake:TextView
    private val detailViewModel by viewModels<DetailMusicViewModel> {
        ViewModelFactory.getInstance(this)
    }
    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySleepScoreBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val getSleepScore = intent.getStringExtra("SLEEPSCORE")
        val getSleepDur = intent.getStringExtra("SLEEPDUR")
        val getWake = intent.getStringExtra("WAKE_UP")

        mSleepScore = findViewById(R.id.sleepscoreNumb)
        mSleepDur = findViewById(R.id.tvSleepDur)
        mWake = findViewById(R.id.tvWakeUp)

        if (!getSleepScore.isNullOrBlank() and !getSleepScore.isNullOrBlank() and !getWake.isNullOrBlank()){
            val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            // Parse the date string to LocalDateTime
            val localDateTime = LocalDateTime.parse(getWake.toString(), dateTimeFormatter)
            // Extract the hours and minutes
            val hours = localDateTime.hour
            val minutes = localDateTime.minute

            mSleepScore.text = getSleepScore.toString()
            mSleepDur.text = "${getSleepDur.toString()} Hours"
            mWake.text = String.format("%02d:%02d", hours, minutes)
        }

        detailViewModel.getMusic().observe(this) {
            if (it != null) {
                binding.apply {
                    tvMusicTitle.text = it.musicName
                }
            }
        }

        binding.apply {
            musicLayout.setOnClickListener{
                startActivity(Intent(this@SleepScoreActivity, MusicActivity::class.java))
            }
            backBtn.setOnClickListener{
                startActivity(Intent(this@SleepScoreActivity, StatisticActivity::class.java))
            }
        }
    }
}