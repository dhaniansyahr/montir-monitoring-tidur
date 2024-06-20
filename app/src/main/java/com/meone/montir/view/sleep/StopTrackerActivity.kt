package com.meone.montir.view.sleep

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.meone.montir.databinding.ActivityStopTrackerBinding
import com.meone.montir.viewModel.ViewModelFactory
import com.meone.montir.viewModel.music.DetailMusicViewModel

class StopTrackerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStopTrackerBinding

    private val detailViewModel by viewModels<DetailMusicViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStopTrackerBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val sleepDuration = intent.getStringExtra("duration")
        val stressInput = intent.getStringExtra("stress")

        detailViewModel.getMusic().observe(this) {
            if (it != null) {
                binding.apply {
                    tvMusicTitle.text = it.musicName
                    tvMusicTime.text = it.musicDuration
                }
            }
        }

        binding.apply {
            backBtn.setOnClickListener {
                startActivity(Intent(this@StopTrackerActivity, SleepTrackerActivity::class.java))
            }

            textView9.text = "$sleepDuration Hours"
        }
    }
}