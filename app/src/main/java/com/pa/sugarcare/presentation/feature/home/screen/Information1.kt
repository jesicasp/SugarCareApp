package com.pa.sugarcare.presentation.feature.home.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.pa.sugarcare.databinding.FragmentInformation1Binding

class Information1 : Fragment() {
    private var _binding: FragmentInformation1Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInformation1Binding.inflate(inflater, container, false)

        binding.apply {
            Glide.with(requireContext())
                .load("https://github.com/jesicasp/product-images/blob/main/screen3.png?raw=true")
                .into(ivInfo1)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}