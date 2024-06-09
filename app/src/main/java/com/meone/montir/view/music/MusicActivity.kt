package com.meone.montir.view.music

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.meone.montir.databinding.ActivityMusicBinding
import com.meone.montir.view.sleep.SleepTrackerActivity

class MusicActivity : AppCompatActivity() {

    private  lateinit var binding: ActivityMusicBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMusicBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.apply {
            sleepButton.setOnClickListener {
                startActivity(Intent(this@MusicActivity, SleepTrackerActivity::class.java))
            }

            statsButton.setOnClickListener {
//                startActivity(Intent(this@MusicActivity, StatsActivity::class.java))
            }

            musicButton.setOnClickListener {
                startActivity(Intent(this@MusicActivity, MusicActivity::class.java))
            }

            profileButton.setOnClickListener {
//                startActivity(Intent(this@MusicActivity, ProfileActivity::class.java))
            }
        }

    }
}