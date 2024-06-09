package com.meone.montir.view.auth

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.meone.montir.R
import com.meone.montir.databinding.ActivityRegisterBinding
import com.meone.montir.viewModel.ViewModelFactory
import com.meone.montir.viewModel.auth.RegisterViewModel

class RegisterActivity : AppCompatActivity() {

    private lateinit var autoCompleteTextView: AutoCompleteTextView
    private lateinit var adapterItems: ArrayAdapter<String>

    private val viewModel by viewModels<RegisterViewModel>{
        ViewModelFactory.getInstance(this)
    }
    var genderInput = ""

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setupView()

        val genderItem = arrayOf("Laki - Laki", "Perempuan")
        autoCompleteTextView = findViewById(R.id.genderSelect)
        adapterItems = ArrayAdapter(this, R.layout.list_gender, genderItem)

        autoCompleteTextView.setAdapter(adapterItems)

        autoCompleteTextView.setOnItemClickListener(AdapterView.OnItemClickListener { adapterView, view, i, l ->
            val selectedItem = adapterView.getItemAtPosition(i).toString()
            genderInput = selectedItem
        })

        binding.signUpButton.setOnClickListener {
            validation()
        }

//        viewModel.isLoading.observe(this) {
//            binding.progressBar3.visibility = if (it) View.VISIBLE else View.GONE
//        }

        viewModel.message.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        viewModel.response.observe(this) {
            if (it.status == "Sukses") {
                startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            }
        }

    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun validation(){
        binding.apply {
            val email = binding.emailEditTextLayout.editText?.text.toString()
            val password = binding.passwordEditTextLayout.editText?.text.toString()
            val age = binding.ageEditInputLayout.editText?.text.toString()
            val height = binding.heightEditInputLayout.editText?.text.toString()
            val weight = binding.weightEditInputLayout.editText?.text.toString()
            val city = binding.cityEditInputLayout.editText?.text.toString()
            val gender = genderInput

            if (email.isNotEmpty() && password.isNotEmpty() && age.isNotEmpty() && height.isNotEmpty() && weight.isNotEmpty() && city.isNotEmpty()) {
                register(username = "Testing", email, password, age, height, weight, city, gender)
            } else {
                Toast.makeText(
                    this@RegisterActivity,
                    "Please Filled All of Column",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

    private fun register(username: String, email: String, password: String, age: String, height: String, weight: String, city: String, gender: String) {
        val ageInt = age.toInt()
        val heightInt = height.toInt()
        val weightInt = weight.toInt()
        val genderInt = if (gender == "Laki - Laki") true else false
        viewModel.register(username, email, password, ageInt, city, genderInt, heightInt, weightInt)

    }
}