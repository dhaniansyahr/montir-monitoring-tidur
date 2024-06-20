package com.meone.montir.view.music

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.meone.montir.adapter.MusicAdapter
import com.meone.montir.databinding.ActivityMusicBinding
import com.meone.montir.response.DataItemMusic
import com.meone.montir.view.component.LoadingDialog
import com.meone.montir.view.profile.ProfileActivity
import com.meone.montir.view.sleep.SleepTrackerActivity
import com.meone.montir.view.statistic.StatisticActivity
import com.meone.montir.viewModel.ViewModelFactory
import com.meone.montir.viewModel.music.MusicViewModel

class MusicActivity : AppCompatActivity() {

    private  lateinit var binding: ActivityMusicBinding

    private val viewModel by viewModels<MusicViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var dialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMusicBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.rvMusic.layoutManager = LinearLayoutManager(this)
        binding.rvMusic.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager(this).orientation))

        dialog = LoadingDialog(this)

        binding.apply {
            sleepButton.setOnClickListener {
                startActivity(Intent(this@MusicActivity, SleepTrackerActivity::class.java))
            }

            statsButton.setOnClickListener {
                startActivity(Intent(this@MusicActivity, StatisticActivity::class.java))
            }

            musicButton.setOnClickListener {
                startActivity(Intent(this@MusicActivity, MusicActivity::class.java))
            }

            profileButton.setOnClickListener {
                startActivity(Intent(this@MusicActivity, ProfileActivity::class.java))
            }
        }

        viewModel.isLoading.observe(this) {
            if (it) {
                dialog.show()
            } else {
                dialog.dismiss()
            }
        }

        viewModel.musicList.observe(this) {
            setMusicData(it)
        }

    }

    private fun setMusicData(music: List<DataItemMusic>) {
        val adapter = MusicAdapter {
            val intent = Intent(this, DetailMusicActivity::class.java)
            intent.putExtra("MUSIC_NAME", it.musicName)
            intent.putExtra("MUSIC_DURATION", it.duration)
            intent.putExtra("MUSIC_URL", it.urlMusic)
            startActivity(intent)
        }

        adapter.submitList(music)
        binding.rvMusic.adapter = adapter
    }
}