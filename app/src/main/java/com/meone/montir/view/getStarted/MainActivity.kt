package com.meone.montir.view.getStarted

import android.app.ActivityManager
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.meone.montir.databinding.ActivityMainBinding
import com.meone.montir.view.sleep.SleepTrackerActivity
import com.meone.montir.viewModel.sleep.SleepTrackerViewModel
import com.meone.montir.viewModel.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<SleepTrackerViewModel> {
        ViewModelFactory.getInstance(this)
    }
    
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        Handler().postDelayed({
            if (!isGetStartedActivityRunning()){
                viewModel.getSession().observe(this) { user ->
                    if (!user.isLogin) {
                        startActivity(Intent(this, GetStarted::class.java))
                        finish()
                    } else {
                        startActivity(Intent(this@MainActivity, SleepTrackerActivity::class.java))
                        finish()
                    }
                }
            }
        }, 2000)
    }

    private fun isGetStartedActivityRunning(): Boolean {
        val activityManager = getSystemService(ACTIVITY_SERVICE) as? ActivityManager
        val taskInfo = activityManager?.getRunningTasks(1)?.get(0)
        val topActivity = taskInfo?.topActivity
        return topActivity?.className == GetStarted::class.java.name
    }
}