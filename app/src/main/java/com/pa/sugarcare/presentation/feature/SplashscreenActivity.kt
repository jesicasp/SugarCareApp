package com.pa.sugarcare.presentation.feature

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.pa.sugarcare.databinding.ActivitySplashscreenBinding
import com.pa.sugarcare.presentation.feature.onboarding.OnBoardingActivity

@SuppressLint("CustomSplashScreen")
class SplashscreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashscreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivitySplashscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        splashScreen.setKeepOnScreenCondition { false }

        binding.root.postDelayed({
            startActivity(Intent(this, OnBoardingActivity::class.java))
            finish()
        }, 1500)
    }
}