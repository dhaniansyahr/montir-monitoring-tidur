package com.meone.montir.view.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.meone.montir.R
import com.meone.montir.databinding.ActivityProfileBinding
import com.meone.montir.databinding.ActivityStatisticBinding
import com.meone.montir.ui.main.ProfileFragment
import com.meone.montir.view.statistic.SleepScoreActivity

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
        }
    }
}