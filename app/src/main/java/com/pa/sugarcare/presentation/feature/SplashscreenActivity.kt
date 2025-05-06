package com.pa.sugarcare.presentation.feature

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.pa.sugarcare.databinding.ActivitySplashscreenBinding
import com.pa.sugarcare.presentation.feature.onboarding.OnBoardingActivity
import com.pa.sugarcare.presentation.feature.onboarding.vm.OnBoardViewModel
import com.pa.sugarcare.presentation.feature.signin.SignInActivity
import com.pa.sugarcare.repository.di.StateInjection
import com.pa.sugarcare.utility.TokenStorage

@SuppressLint("CustomSplashScreen")
class SplashscreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashscreenBinding
    private val onBoardViewModel: OnBoardViewModel by viewModels {
        StateInjection.onBoardInjection(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        TokenStorage.init(applicationContext)
        checkState()

        binding = ActivitySplashscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        splashScreen.setKeepOnScreenCondition { false }

    }

    private fun checkState() {
        onBoardViewModel.getOnBoardState().observe(this) { onBoardState ->
            if (onBoardState.isNullOrBlank()) {
                Log.d(TAG, "Belum onboarding")
                // Belum onboarding
                startActivity(Intent(this, OnBoardingActivity::class.java))
            } else {
                val token = TokenStorage.getToken()
                Log.d(TAG, "Acces Token : $token")
                if (token.isNullOrEmpty()) {
                    // Sudah onboarding tapi belum login
                    Log.d(TAG, "Sudah onboarding")

                    startActivity(Intent(this, SignInActivity::class.java))
                } else {
                    // Sudah login dan onboarding
                    Log.d(TAG, "Sudah login & onboarding")
                    startActivity(Intent(this, MainActivity::class.java))
                }
            }
            finish()
        }
    }

    companion object{
        private const val TAG = "LogSplashScreenActivity"

    }
}