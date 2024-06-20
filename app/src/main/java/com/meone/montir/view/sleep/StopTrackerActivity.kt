package com.meone.montir.view.sleep

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.meone.montir.databinding.ActivityStopTrackerBinding
import com.meone.montir.view.component.LoadingDialog
import com.meone.montir.view.music.MusicActivity
import com.meone.montir.view.statistic.StatisticActivity
import com.meone.montir.viewModel.ViewModelFactory
import com.meone.montir.viewModel.music.DetailMusicViewModel
import com.meone.montir.viewModel.sleep.StopTrackerViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class StopTrackerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStopTrackerBinding

    private val detailViewModel by viewModels<DetailMusicViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private val viewModel by viewModels<StopTrackerViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var dialog: LoadingDialog

    private var currentTime: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStopTrackerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Correct retrieval of the extras
        val sleepDuration = intent.getLongExtra("duration", 0L)
        val stressInput = intent.getIntExtra("stress", 0)

        val formattedDuration = formatDurationInHours(sleepDuration)

        dialog = LoadingDialog(this)

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

            textView9.text = "$formattedDuration Hours"

            button4.setOnClickListener {
                currentTime = getCurrentTime()
                viewModel.createDailyData(stressInput, formattedDuration.toFloat())
            }

            musicLayout.setOnClickListener {
                startActivity(Intent(this@StopTrackerActivity, MusicActivity::class.java))
            }
        }

        viewModel.isLoading.observe(this) {
            if (it) {
                dialog.show()
            } else {
                dialog.dismiss()
            }
        }

        viewModel.response.observe(this) {
            if (it != null) {
                val intent = Intent(this@StopTrackerActivity, StatisticActivity::class.java)
                intent.apply {
                    putExtra("QUALITY_SCORE", it.data.qualityScore.toString())
                    putExtra("SLEEP_DURATION", it.data.sleepDuration.toString())
                    putExtra("BMI", it.data.bmi.toString())
                    putExtra("WAKE_UP_TIME", currentTime)
                }
                startActivity(intent)
            }
        }
    }

    private fun formatDurationInHours(durationInMillis: Long): String {
        val hours = durationInMillis / 3600000.0
        return String.format(Locale.US, "%.1f", hours)
    }

    private fun getCurrentTime(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return sdf.format(Date())
    }
}
