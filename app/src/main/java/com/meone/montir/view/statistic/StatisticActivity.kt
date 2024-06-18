package com.meone.montir.view.statistic

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.meone.montir.R
import com.meone.montir.databinding.ActivitySleepTrackerBinding
import com.meone.montir.databinding.ActivityStatisticBinding
import com.meone.montir.view.music.MusicActivity
import com.meone.montir.view.sleep.SleepTrackerActivity
import com.meone.montir.view.profile.ProfileActivity

class StatisticActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStatisticBinding
    @SuppressLint("DefaultLocale")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatisticBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.apply {
            sleepscoreBtn.setOnClickListener {
                startActivity(Intent(this@StatisticActivity, SleepScoreActivity::class.java))
            }
            musicButton.setOnClickListener {
                startActivity(Intent(this@StatisticActivity, MusicActivity::class.java))
            }


            statsButton.setOnClickListener{
                startActivity(Intent(this@StatisticActivity, StatisticActivity::class.java))
            }

            profileButton.setOnClickListener{
                startActivity(Intent(this@StatisticActivity, ProfileActivity::class.java))
            }

            sleepButton.setOnClickListener{
                startActivity(Intent(this@StatisticActivity, SleepTrackerActivity::class.java))
            }
        }
    }
}