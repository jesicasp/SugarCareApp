package com.pa.sugarcare.presentation.feature.onboarding.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pa.sugarcare.databinding.FragmentScreen1Binding
import com.pa.sugarcare.presentation.feature.onboarding.OnBoardingActivity

class Screen1 : Fragment() {
    private var _binding: FragmentScreen1Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScreen1Binding.inflate(inflater, container, false)

        val viewPager = (requireActivity() as OnBoardingActivity).getCurrentPager()
        binding.btnNext.setOnClickListener {
            viewPager.currentItem = 1
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}