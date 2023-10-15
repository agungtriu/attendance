package com.example.phinconattendance.ui.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.phinconattendance.databinding.ActivityOnboardingBinding
import com.example.phinconattendance.ui.login.LoginActivity
import com.example.phinconattendance.ui.register.RegisterActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var adapter: ViewPagerAdapter
    private val videModel: OnboardingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        videModel.saveOnboardingStatus()
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = ViewPagerAdapter(this)
        binding.vpOnboardingSlide.adapter = adapter
        binding.indicatorOnboarding.setViewPager(binding.vpOnboardingSlide)

        listener()
    }

    private fun listener() {
        binding.tvOnboardingSkip.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnOnboardingLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnOnboardingSignup.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}