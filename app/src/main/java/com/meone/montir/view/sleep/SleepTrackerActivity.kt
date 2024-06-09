package com.meone.montir.view.sleep

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import com.meone.montir.databinding.ActivitySleepTrackerBinding
import com.meone.montir.view.music.MusicActivity

class SleepTrackerActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySleepTrackerBinding

    @SuppressLint("DefaultLocale")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySleepTrackerBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.apply {
            alarmBtn.setOnClickListener {
                val timePickerDialog = TimePickerDialog(
                    this@SleepTrackerActivity, // Replace 'YourActivity' with the actual name of your activity
                    android.R.style.Theme_Holo_Dialog_MinWidth,
                    TimePickerDialog.OnTimeSetListener { _: TimePicker, hourOfDay: Int, minute: Int ->
                        val time = String.format("%02d:%02d", hourOfDay, minute)
                        valueAlarm.text = time
                    }, 12, 0, false
                )
                timePickerDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                timePickerDialog.show()
            }

            bedtimeBtn.setOnClickListener {
                val timePickerDialog = TimePickerDialog(
                    this@SleepTrackerActivity, // Replace 'YourActivity' with the actual name of your activity
                    android.R.style.Theme_Holo_Dialog_MinWidth,
                    TimePickerDialog.OnTimeSetListener { _: TimePicker, hourOfDay: Int, minute: Int ->
                        val time = String.format("%02d:%02d", hourOfDay, minute)
                        valueBedTime.text = time
                    }, 12, 0, false
                )
                timePickerDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                timePickerDialog.show()
            }

            sleepTrackerBtn.setOnClickListener {
                startActivity(Intent(this@SleepTrackerActivity, StopTrackerActivity::class.java))
            }

            musicButton.setOnClickListener {
                startActivity(Intent(this@SleepTrackerActivity, MusicActivity::class.java))
            }
        }

    }
}