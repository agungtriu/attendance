package com.example.phinconattendance.ui.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.phinconattendance.databinding.ActivitySplashScreenBinding
import com.example.phinconattendance.ui.home.HomeActivity
import com.example.phinconattendance.ui.login.LoginActivity
import com.example.phinconattendance.ui.onboarding.OnboardingActivity
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private val splashScreenViewModel: SplashScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val splashTime: Long = 2000
        Handler(Looper.getMainLooper()).postDelayed({
            splashScreenViewModel.getOnboarding().observe(this) {
                var intent: Intent
                if (it) {
                    splashScreenViewModel.isLogin().observe(this) { isLogin ->
                        if (isLogin) {
                            intent = Intent(this, HomeActivity::class.java)
                            startActivity(intent)
                        } else {
                            intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                        }
                    }
                } else {
                    intent = Intent(this, OnboardingActivity::class.java)
                    startActivity(intent)
                }
                finish()
            }
        }, splashTime)
    }
}