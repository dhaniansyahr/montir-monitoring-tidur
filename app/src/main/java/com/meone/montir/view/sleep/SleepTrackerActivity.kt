package com.meone.montir.view.sleep

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.widget.TimePicker
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.meone.montir.databinding.ActivitySleepTrackerBinding
import com.meone.montir.helper.alarmNotification.NotificationAlarmScheduler
import com.meone.montir.view.auth.LoginActivity
import com.meone.montir.view.component.LoadingDialog
import com.meone.montir.view.music.MusicActivity
import com.meone.montir.view.profile.ProfileActivity
import com.meone.montir.view.statistic.StatisticActivity
import com.meone.montir.viewModel.ViewModelFactory
import java.util.Calendar

class SleepTrackerActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySleepTrackerBinding

    private lateinit var calendar: Calendar
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent

    private val viewModel by viewModels<SleepTrackerViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private val notificationAlarmScheduler by lazy {
        NotificationAlarmScheduler(this)
    }

    private lateinit var dialog: LoadingDialog

    @SuppressLint("DefaultLocale")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySleepTrackerBinding.inflate(layoutInflater)

        setContentView(binding.root)

        createNotificationChannel()

        dialog = LoadingDialog(this)

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }

        binding.apply {
            alarmBtn.setOnClickListener {
                val timePickerDialog = TimePickerDialog(
                    this@SleepTrackerActivity, // Replace 'YourActivity' with the actual name of your activity
                    android.R.style.Theme_Holo_Dialog_MinWidth,
                    TimePickerDialog.OnTimeSetListener { _: TimePicker, hourOfDay: Int, minute: Int ->
                        val time = String.format("%02d:%02d", hourOfDay, minute)
                        valueAlarm.text = time

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
                dialog.show()

                val handler = Handler()
                val runnable = Runnable {
                    dialog.cancel()
                }
                handler.postDelayed(runnable, 3000)
//                val reminderItem = ReminderItem(
//                    time = Calendar.getInstance().apply {
//                        set(Calendar.HOUR_OF_DAY, 19)
//                        set(Calendar.MINUTE, 0)
//                    }.timeInMillis,
//                    id = 1,
//                )
//                notificationAlarmScheduler.schedule(reminderItem)
//
//                Toast.makeText(this@SleepTrackerActivity, "Alarm Berhasil Dibuat", Toast.LENGTH_SHORT).show()
//                startActivity(Intent(this@SleepTrackerActivity, StopTrackerActivity::class.java))
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