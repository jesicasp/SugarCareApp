package com.pa.sugarcare.presentation.feature.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.pa.sugarcare.databinding.FragmentHomeBinding
import com.pa.sugarcare.presentation.adapter.OnBoardingViewPagerAdapter
import com.pa.sugarcare.presentation.feature.home.screen.Information1
import com.pa.sugarcare.presentation.feature.home.screen.Information2
import com.pa.sugarcare.presentation.feature.home.screen.Information3


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var pagerAdapter: OnBoardingViewPagerAdapter
    private val listOnBoardingScreen = listOf(
        Information1(),
        Information2(),
        Information3()
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pagerAdapter = OnBoardingViewPagerAdapter(listOnBoardingScreen, this.childFragmentManager, lifecycle)

        binding.apply {
            viewPager.adapter = pagerAdapter
            dotsIndicator.attachTo(viewPager)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}