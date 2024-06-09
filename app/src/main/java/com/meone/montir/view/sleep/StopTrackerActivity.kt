package com.meone.montir.view.sleep

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.meone.montir.databinding.ActivityStopTrackerBinding

class StopTrackerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStopTrackerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStopTrackerBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.apply {
            backBtn.setOnClickListener {
                startActivity(Intent(this@StopTrackerActivity, SleepTrackerActivity::class.java))
            }
        }
    }
}