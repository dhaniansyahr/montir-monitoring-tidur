package com.meone.montir.view.statistic

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.meone.montir.R
import com.meone.montir.databinding.ActivitySleepScoreBinding

class SleepScoreActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySleepScoreBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySleepScoreBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}