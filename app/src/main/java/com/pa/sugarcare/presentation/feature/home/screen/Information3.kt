package com.pa.sugarcare.presentation.feature.home.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.pa.sugarcare.R
import com.pa.sugarcare.databinding.FragmentInformation2Binding
import com.pa.sugarcare.databinding.FragmentInformation3Binding

class Information3 : Fragment() {
    private var _binding: FragmentInformation3Binding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInformation3Binding.inflate(inflater, container, false)

        binding.apply {
            Glide.with(requireContext())
                .load("https://github.com/jesicasp/product-images/blob/main/screen1.png?raw=true")
                .into(ivInfo3)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}