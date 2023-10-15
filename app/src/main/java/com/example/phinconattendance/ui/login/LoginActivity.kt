package com.example.phinconattendance.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.phinconattendance.R
import com.example.phinconattendance.databinding.ActivityLoginBinding
import com.example.phinconattendance.helper.Utils.Companion.makeLinks
import com.example.phinconattendance.ui.forgotpassword.ForgotPasswordActivity
import com.example.phinconattendance.ui.home.HomeActivity
import com.example.phinconattendance.ui.register.RegisterActivity
import com.example.phinconattendance.vo.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listener()
    }

    private fun listener() {
        binding.btnLogin.setOnClickListener {
            val email = binding.etLoginEmail.text.toString()
            val password = binding.etLoginPassword.text.toString()

            if (email.isEmpty()) {
                Toast.makeText(this, getString(R.string.all_email_cannot_empty), Toast.LENGTH_SHORT)
                    .show()
            } else if (password.isEmpty()) {
                Toast.makeText(
                    this,
                    getString(R.string.all_password_cannot_empty),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                loginViewModel.login(email, password).observe(this){
                    when (it.status) {
                        Status.LOADING -> showProgressBar()
                        Status.SUCCESS -> {
                            hideProgressBar()
                            val intent = Intent(this, HomeActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                            finish()
                        }
                        Status.ERROR -> {
                            hideProgressBar()
                            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        binding.tvLoginDonthaveaccount.makeLinks(
            Pair(getString(R.string.all_register), View.OnClickListener {
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            })
        )

        binding.tvLoginForgotpassword.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }
    }
    private fun showProgressBar() {
        binding.pbLogin.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.pbLogin.visibility = View.GONE
    }
}