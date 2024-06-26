package com.meone.montir.view.profile

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.meone.montir.databinding.ActivityProfileBinding
import com.meone.montir.view.account.AccountActivity
import com.meone.montir.view.auth.LoginActivity
import com.meone.montir.view.component.LoadingDialog
import com.meone.montir.view.music.MusicActivity
import com.meone.montir.view.sleep.SleepTrackerActivity
import com.meone.montir.view.statistic.StatisticActivity
import com.meone.montir.viewModel.ViewModelFactory
import com.meone.montir.viewModel.auth.LoginViewModel
import com.meone.montir.viewModel.profile.ProfileViewModel

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    private val authViewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private val viewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var dialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)

        setContentView(binding.root)

        dialog = LoadingDialog(this)

        viewModel.isLoading.observe(this) {
            if (it) {
                dialog.show()
            } else {
                dialog.dismiss()
            }
        }

        viewModel.value.observe(this) {
            if (it != null) {
                binding.apply {
                    binding.textView2.text = it.data.username
                }
            }
        }

        binding.apply {
            logout.setOnClickListener {
                authViewModel.logout()
                finish()

                Toast.makeText(this@ProfileActivity, "Anda berhasil logout", Toast.LENGTH_SHORT)
                    .show()

                val intent = Intent(this@ProfileActivity, LoginActivity::class.java)
                startActivity(intent)
            }

            profilSetting.setOnClickListener {
                startActivity(Intent(this@ProfileActivity, DetailProfileActivity::class.java))
            }

            accountSetting.setOnClickListener {
                startActivity(Intent(this@ProfileActivity, AccountActivity::class.java))
            }

            musicButton.setOnClickListener {
                startActivity(Intent(this@ProfileActivity, MusicActivity::class.java))
            }

            statsButton.setOnClickListener{
                startActivity(Intent(this@ProfileActivity, StatisticActivity::class.java))
            }

            profileButton.setOnClickListener{
                startActivity(Intent(this@ProfileActivity, ProfileActivity::class.java))
            }

            sleepButton.setOnClickListener{
                startActivity(Intent(this@ProfileActivity, SleepTrackerActivity::class.java))
            }
        }

    }
}