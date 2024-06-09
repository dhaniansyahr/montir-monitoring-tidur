package com.meone.montir.view.getStarted

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.meone.montir.databinding.ActivityGetStartedBinding
import com.meone.montir.view.auth.LoginActivity

class GetStarted : AppCompatActivity() {

    private lateinit var binding: ActivityGetStartedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGetStartedBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.getStartedButton.setOnClickListener {
            startActivity(Intent(this@GetStarted, LoginActivity::class.java))
        }
    }
}