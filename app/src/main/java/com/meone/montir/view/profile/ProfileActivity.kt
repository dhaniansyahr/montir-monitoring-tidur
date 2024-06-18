package com.meone.montir.view.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.meone.montir.R
import com.meone.montir.databinding.ActivityProfileBinding
import com.meone.montir.databinding.ActivityStatisticBinding
import com.meone.montir.ui.main.ProfileFragment

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}