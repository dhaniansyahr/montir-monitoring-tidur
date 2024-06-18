package com.meone.montir.view.statistic

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.meone.montir.R
import com.meone.montir.databinding.ActivitySleepScoreBinding
import com.meone.montir.view.music.MusicActivity
import com.meone.montir.view.sleep.SleepTrackerActivity
import com.meone.montir.view.profile.ProfileActivity

class SleepScoreActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySleepScoreBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySleepScoreBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.apply {
            musicButton.setOnClickListener {
                startActivity(Intent(this@SleepScoreActivity, MusicActivity::class.java))
            }


            statsButton.setOnClickListener{
                startActivity(Intent(this@SleepScoreActivity, StatisticActivity::class.java))
            }

            profileButton.setOnClickListener{
                startActivity(Intent(this@SleepScoreActivity, ProfileActivity::class.java))
            }

            sleepButton.setOnClickListener{
                startActivity(Intent(this@SleepScoreActivity, SleepTrackerActivity::class.java))
            }
        }
    }
}