package com.meone.montir.view.profile

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.meone.montir.databinding.ActivityDetailProfileBinding
import com.meone.montir.service.RequestUpdateUser
import com.meone.montir.view.component.LoadingDialog
import com.meone.montir.viewModel.ViewModelFactory
import com.meone.montir.viewModel.profile.ProfileViewModel

class DetailProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailProfileBinding

    private val viewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var dialog: LoadingDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProfileBinding.inflate(layoutInflater)

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
                    ageEditText.setText(it.data.age.toString()) // Make sure to convert age to a string
                    val gender: String = if (it.data.gender == 1) "Laki - Laki" else "Perempuan"
                    genderEditText.setText(gender)
                    BMIEditText.setText(it.data.bmi.toString())
                }
            }
        }

        binding.apply {
            editBtn.setOnClickListener {
                val username = nameEditText.text.toString()
                val age = ageEditText.text.toString().toInt()
                val gender = if (genderEditText.text.toString() == "Laki - Laki") true else false
                val bmi = BMIEditText.text.toString().toFloat()

                viewModel.updateUser(RequestUpdateUser(username, age, gender, bmi))
            }
        }

        viewModel.isLoadingUpdate.observe(this) {
            if (it) {
                dialog.show()
            } else {
                dialog.dismiss()
            }
        }

        viewModel.response.observe(this) {
            if ( it.message.toString() == "Update data sukses!") {
                startActivity(Intent(this@DetailProfileActivity, ProfileActivity::class.java))
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        }

    }
}