package com.example.phinconattendance.ui.forgotpassword

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.phinconattendance.R
import com.example.phinconattendance.databinding.ActivityForgotPasswordBinding
import com.example.phinconattendance.helper.Utils.Companion.makeLinks
import com.example.phinconattendance.ui.login.LoginActivity
import com.example.phinconattendance.vo.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgotPasswordBinding
    private val viewModel: ForgotPasswordViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listener()
    }

    private fun listener() {
        binding.tvForgotpasswordAlreadyhaveaccount.makeLinks(
            Pair(getString(R.string.all_login), View.OnClickListener {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            })
        )

        binding.btnForgotpassword.setOnClickListener {
            val email = binding.etForgotpasswordEmail.text.toString()
            if (email.isEmpty()) {
                Toast.makeText(this, getString(R.string.all_email_cannot_empty), Toast.LENGTH_SHORT)
                    .show()
            } else {
                viewModel.forgotPassword(email).observe(this) {
                    when (it.status) {
                        Status.LOADING -> showProgressBar()
                        Status.SUCCESS -> {
                            hideProgressBar()
                            Toast.makeText(
                                this,
                                getString(R.string.forgotpassword_emailsendto) + email,
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        Status.ERROR -> {
                            hideProgressBar()
                            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun showProgressBar() {
        binding.pbForgotpassword.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.pbForgotpassword.visibility = View.GONE
    }
}