package com.meone.montir.view.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.meone.montir.R
import com.meone.montir.databinding.ActivityProfileBinding
import com.meone.montir.databinding.ActivityStatisticBinding
import com.meone.montir.ui.main.ProfileFragment
import com.meone.montir.view.statistic.SleepScoreActivity
import com.meone.montir.view.account.AccountActivity
import com.meone.montir.view.music.MusicActivity
import com.meone.montir.view.sleep.SleepTrackerActivity
import com.meone.montir.view.statistic.StatisticActivity

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.apply {
            profilSetting.setOnClickListener{
                startActivity(Intent(this@ProfileActivity, DetailProfileActivity::class.java))
            }
            accountSetting.setOnClickListener{
                startActivity(Intent(this@ProfileActivity, AccountActivity::class.java))
            }
            musicButton.setOnClickListener {
                startActivity(Intent(this@ProfileActivity, MusicActivity::class.java))
            }


            statsButton.setOnClickListener{
                startActivity(Intent(this@ProfileActivity, StatisticActivity::class.java))
            }

            profileButton.setOnClickListener{
                startActivity(Intent(this@ProfileActivity, ProfileActivity::class.java))
            }

            sleepButton.setOnClickListener{
                startActivity(Intent(this@ProfileActivity, SleepTrackerActivity::class.java))
            }
        }
    }
}