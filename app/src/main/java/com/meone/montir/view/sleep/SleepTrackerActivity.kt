package com.meone.montir.view.sleep

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ArrayAdapter
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.meone.montir.R
import com.meone.montir.databinding.ActivitySleepTrackerBinding
import com.meone.montir.helper.alarmNotification.NotificationAlarmScheduler
import com.meone.montir.helper.alarmNotification.ReminderItem
import com.meone.montir.view.auth.LoginActivity
import com.meone.montir.view.component.StressDialog
import com.meone.montir.view.music.MusicActivity
import com.meone.montir.view.profile.ProfileActivity
import com.meone.montir.view.statistic.StatisticActivity
import com.meone.montir.viewModel.ViewModelFactory
import com.meone.montir.viewModel.sleep.SleepTrackerViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class SleepTrackerActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySleepTrackerBinding

    private lateinit var calendar: Calendar
    private lateinit var stressDialog: StressDialog
    private lateinit var adapterItems: ArrayAdapter<String>

    private val viewModel by viewModels<SleepTrackerViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private val notificationAlarmScheduler by lazy {
        NotificationAlarmScheduler(this)
    }

    private var alarmTime: String = ""
    private var sleepTime: String = ""
    private var durationInput: Long = 0

    @SuppressLint("DefaultLocale")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySleepTrackerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createNotificationChannel()

        var stressItem = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
        adapterItems = ArrayAdapter(this, R.layout.list_gender, stressItem)

        stressDialog = StressDialog(this, adapterItems, durationInput)

//        viewModel.getSession().observe(this) { user ->
//            if (!user.isLogin) {
//                startActivity(Intent(this, LoginActivity::class.java))
//                finish()
//            }
//        }

        viewModel.getAlarmTime().observe(this) {
            binding.valueAlarm.text = it
        }

        viewModel.getSleepTime().observe(this) {
            binding.valueBedTime.text = it
        }

        binding.apply {
            alarmBtn.setOnClickListener {
                val timePickerDialog = TimePickerDialog(
                    this@SleepTrackerActivity,
                    android.R.style.Theme_Holo_Dialog_MinWidth,
                    TimePickerDialog.OnTimeSetListener { _: TimePicker, hourOfDay: Int, minute: Int ->
                        val time = String.format("%02d:%02d", hourOfDay, minute)
                        valueAlarm.text = time
                        alarmTime = time

                        calendar = Calendar.getInstance()
                        calendar[Calendar.HOUR_OF_DAY] = hourOfDay
                        calendar[Calendar.MINUTE] = minute
                        calendar[Calendar.SECOND] = 0
                        calendar[Calendar.MILLISECOND] = 0
                    }, 12, 0, false
                )
                timePickerDialog.setTitle("Set your Alarm")
                timePickerDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                timePickerDialog.show()
            }

            bedtimeBtn.setOnClickListener {
                val timePickerDialog = TimePickerDialog(
                    this@SleepTrackerActivity,
                    android.R.style.Theme_Holo_Dialog_MinWidth,
                    TimePickerDialog.OnTimeSetListener { _: TimePicker, hourOfDay: Int, minute: Int ->
                        val time = String.format("%02d:%02d", hourOfDay, minute)
                        valueBedTime.text = time
                        sleepTime = time
                    }, 12, 0, false
                )
                timePickerDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                timePickerDialog.show()
            }

            sleepTrackerBtn.setOnClickListener {
                // Check if alarmTime or sleepTime is empty
                if (alarmTime.isEmpty() || sleepTime.isEmpty()) {
                    Toast.makeText(this@SleepTrackerActivity, "Please set both Alarm Time and Bedtime", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                stressDialog.show()

                val duration = calculateDuration(sleepTime, alarmTime)
                durationInput = duration
                startCountDownTimer(duration)

                val reminderItem = ReminderItem(
                    time = Calendar.getInstance().apply {
                        set(Calendar.HOUR_OF_DAY, 19)
                        set(Calendar.MINUTE, 0)
                    }.timeInMillis,
                    id = 1,
                )
                notificationAlarmScheduler.schedule(reminderItem)

                Toast.makeText(this@SleepTrackerActivity, "Alarm Berhasil Disetting", Toast.LENGTH_SHORT).show()
            }

            musicButton.setOnClickListener {
                startActivity(Intent(this@SleepTrackerActivity, MusicActivity::class.java))
            }


            statsButton.setOnClickListener{
                startActivity(Intent(this@SleepTrackerActivity, StatisticActivity::class.java))
            }

            profileButton.setOnClickListener{
                startActivity(Intent(this@SleepTrackerActivity, ProfileActivity::class.java))
            }
        }
    }

    private fun timeStringToMillis(time: String): Long {
        val format = SimpleDateFormat("HH:mm", Locale.getDefault())
        val date = format.parse(time)
        val calendar = Calendar.getInstance().apply {
            this.time = date!!
        }
        return calendar.get(Calendar.HOUR_OF_DAY) * 3600000L + calendar.get(Calendar.MINUTE) * 60000L
    }

    private fun calculateDuration(sleepTime: String, alarmTime: String): Long {
        val sleepMillis = timeStringToMillis(sleepTime)
        val alarmMillis = timeStringToMillis(alarmTime)

        return if (alarmMillis > sleepMillis) {
            alarmMillis - sleepMillis
        } else {
            // If alarm time is on the next day
            alarmMillis + 24 * 3600000L - sleepMillis
        }
    }

    private fun startCountDownTimer(duration: Long) {
        val max = 86400 // Max progress should be in seconds
        binding.progressBar.max = max.toInt()

        val countDownTimer = object : CountDownTimer(duration, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val elapsedSeconds = (millisUntilFinished / 1000).toInt()
                val hours = elapsedSeconds / 3600
                val minutes = (elapsedSeconds % 3600) / 60
                val seconds = elapsedSeconds % 60

                runOnUiThread {
                    binding.progressBar.progress = elapsedSeconds
                    binding.valueSleepTime.text = String.format("%02d:%02d:%02d", hours, minutes, seconds)
                }
            }

            override fun onFinish() {
                runOnUiThread {
                    binding.valueSleepTime.text = "00:00:00"
                }
            }
        }
        countDownTimer.start()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "MONTIR: Monitoring Tidur Apps"
            val description = "Perbaiki dan Perbaharui Kualitas Tidur mu!"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("montir", name, importance)
            channel.description = description
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
}
