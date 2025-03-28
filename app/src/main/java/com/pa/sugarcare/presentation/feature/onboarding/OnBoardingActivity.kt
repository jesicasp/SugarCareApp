package com.pa.sugarcare.presentation.feature.onboarding

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.pa.sugarcare.R
import com.pa.sugarcare.databinding.ActivityOnBoardingBinding
import com.pa.sugarcare.presentation.feature.onboarding.screen.Screen1
import com.pa.sugarcare.presentation.feature.onboarding.screen.Screen2

class OnBoardingActivity : AppCompatActivity() {
    private var _binding: ActivityOnBoardingBinding? = null
    private val binding get() = _binding!!
    private lateinit var pagerAdapter: OnBoardingViewPagerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val listOnBoardingScreen = listOf(
            Screen1(),
            Screen2()
        )

        pagerAdapter = OnBoardingViewPagerAdapter(listOnBoardingScreen, this.supportFragmentManager, lifecycle)

        binding.apply {
            viewPager.adapter = pagerAdapter
            dotsIndicator.attachTo(viewPager)
        }
    }

    fun getCurrentPager() : ViewPager2 {
        return binding.viewPager
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}