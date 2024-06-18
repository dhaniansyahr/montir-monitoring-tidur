package com.meone.montir.view.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.meone.montir.data.pref.UserModel
import com.meone.montir.databinding.ActivityLoginBinding
import com.meone.montir.view.component.LoadingDialog
import com.meone.montir.view.sleep.SleepTrackerActivity
import com.meone.montir.viewModel.ViewModelFactory
import com.meone.montir.viewModel.auth.LoginViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var dialog: LoadingDialog
  
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        dialog = LoadingDialog(this)

        binding.apply {
            signUpBtn.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            }

            signInButton.setOnClickListener {
                validation()
//                startActivity(Intent(this@LoginActivity, SleepTrackerActivity::class.java))
            }
        }

        viewModel.value.observe(this) {
            viewModel.saveSession(UserModel(it.userId, "Testing", it.token, it.data[0].gender, it.data[0].city, it.data[0].weight, it.data[0].height, it.data[0].age, it.data[0].bmi))
            AlertDialog.Builder(this).apply {
                setTitle("Yeah!")
                setMessage("Anda berhasil login!")
                setPositiveButton("Lanjut") { _, _ ->
                    val intent = Intent(context, SleepTrackerActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                }
                create()
                show()
            }
        }

        viewModel.isLoading.observe(this) {
            if (it) {
                dialog.show()
            } else {
                dialog.dismiss()
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