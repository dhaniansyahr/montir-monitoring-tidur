package com.meone.montir.view.account

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.meone.montir.databinding.ActivityAccountBinding
import com.meone.montir.service.RequestUpdatePassword
import com.meone.montir.view.component.LoadingDialog
import com.meone.montir.view.music.MusicActivity
import com.meone.montir.view.profile.ProfileActivity
import com.meone.montir.view.sleep.SleepTrackerActivity
import com.meone.montir.view.statistic.StatisticActivity
import com.meone.montir.viewModel.ViewModelFactory
import com.meone.montir.viewModel.profile.ProfileViewModel

class AccountActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAccountBinding

    private val viewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var dialog: LoadingDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountBinding.inflate(layoutInflater)

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
                    nameEditText.setText(it.data.username)
                }
            }
        }

        binding.apply {
            musicButton.setOnClickListener {
                startActivity(Intent(this@AccountActivity, MusicActivity::class.java))
            }

            statsButton.setOnClickListener{
                startActivity(Intent(this@AccountActivity, StatisticActivity::class.java))
            }

            profileButton.setOnClickListener{
                startActivity(Intent(this@AccountActivity, ProfileActivity::class.java))
            }

            sleepButton.setOnClickListener{
                startActivity(Intent(this@AccountActivity, SleepTrackerActivity::class.java))
            }

            editPassBtn.setOnClickListener {
                val username = nameEditText.text.toString()
                val password = passEditText.text.toString()

                viewModel.updateAccount(RequestUpdatePassword(username, password))
            }
        }

        viewModel.isLoadingUpdatePassword.observe(this) {
            if (it) {
                dialog.show()
            } else {
                dialog.dismiss()
            }
        }

        viewModel.responseUpdatePassword.observe(this) {
            if ( it.message.toString() == "Update data sukses!") {
                startActivity(Intent(this@AccountActivity, ProfileActivity::class.java))
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}