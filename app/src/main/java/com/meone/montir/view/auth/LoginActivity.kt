package com.meone.montir.view.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.meone.montir.databinding.ActivityLoginBinding
import com.meone.montir.view.sleep.SleepTrackerActivity
import com.meone.montir.viewModel.ViewModelFactory
import com.meone.montir.viewModel.auth.LoginViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.apply {
            signUpBtn.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            }

            signInButton.setOnClickListener {
                validation()
            }
        }

        viewModel.apply {
            isLoading.observe(this@LoginActivity) {

            }
            message.observe(this@LoginActivity) {
                if (it == "Login berhasil!!") {
                    startActivity(Intent(this@LoginActivity, SleepTrackerActivity::class.java))
                }
                Toast.makeText(this@LoginActivity, it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validation() {
        val email = binding.emailEditTextLayout.editText?.text.toString()
        val password = binding.passwordEditTextLayout.editText?.text.toString()

        when {
            email.isEmpty() && password.isNotEmpty() -> Toast.makeText(this, "Please Email must be filled", Toast.LENGTH_SHORT).show()
            email.isNotEmpty() && password.isEmpty() -> Toast.makeText(this, "Please Password must be filled", Toast.LENGTH_SHORT).show()
            else -> login(email, password)
        }
    }

    private fun login(email: String, password: String) {
        viewModel.apply {
            login(email, password)
        }
    }

}