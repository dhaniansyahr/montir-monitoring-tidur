package com.meone.montir.view.music

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.meone.montir.databinding.ActivityDetailMusicBinding

class DetailMusicActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailMusicBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMusicBinding.inflate(layoutInflater)

        setContentView(binding.root)

    }
}