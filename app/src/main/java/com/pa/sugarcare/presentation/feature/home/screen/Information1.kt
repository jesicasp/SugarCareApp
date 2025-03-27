package com.pa.sugarcare.presentation.feature.home.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pa.sugarcare.databinding.FragmentInformation1Binding

class Information1 : Fragment() {
    private var _binding: FragmentInformation1Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInformation1Binding.inflate(inflater, container, false)

        return binding.root
    }
}