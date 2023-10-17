package com.example.phinconattendance.ui.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.phinconattendance.R
import com.example.phinconattendance.databinding.ActivityRegisterBinding
import com.example.phinconattendance.helper.Utils.Companion.makeLinks
import com.example.phinconattendance.ui.login.LoginActivity
import com.example.phinconattendance.vo.Status
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        listener()
    }

    private fun listener() {
        binding.tvRegisterAlreadyhaveaccount.makeLinks(
            Pair(getString(R.string.all_login), View.OnClickListener {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            })
        )

        binding.btnRegister.setOnClickListener {
            val email = binding.etRegisterEmail.text.toString()
            val fullName = binding.etRegisterFullname.text.toString()
            val password = binding.etRegisterPassword.text.toString()
            val repeatPassword = binding.etRegisterRepeatpassword.text.toString()

            if (email.isEmpty()) {
                Toast.makeText(this, getString(R.string.all_email_cannot_empty), Toast.LENGTH_SHORT)
                    .show()
            } else if (fullName.isEmpty()) {
                Toast.makeText(
                    this,
                    getString(R.string.all_fullname_cannot_empty), Toast.LENGTH_SHORT
                ).show()
            } else if (password.isEmpty()) {
                Toast.makeText(
                    this,
                    getString(R.string.all_password_cannot_empty), Toast.LENGTH_SHORT
                ).show()
            } else if (repeatPassword.isEmpty()) {
                Toast.makeText(
                    this,
                    getString(R.string.all_repeat_password_cannot_empty), Toast.LENGTH_SHORT
                ).show()
            } else if (password.length < 6) {
                Toast.makeText(this, getString(R.string.all_password_too_short), Toast.LENGTH_SHORT)
                    .show()
            } else if (password != repeatPassword) {
                Toast.makeText(
                    this,
                    getString(R.string.all_password_and_repeat_password_not_match),
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else {
                viewModel.registerUser(email, fullName, password).observe(this) {
                    when (it.status) {
                        Status.LOADING -> showProgressBar()
                        Status.SUCCESS -> {
                            hideProgressBar()
                            Toast.makeText(
                                this,
                                getString(R.string.register_successful),
                                Toast.LENGTH_SHORT
                            ).show()
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        }

                        Status.ERROR -> {
                            hideProgressBar()
                            Toast.makeText(
                                this,
                                getString(R.string.register_failed),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }

    private fun showProgressBar() {
        binding.pbRegister.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.pbRegister.visibility = View.GONE
    }
}