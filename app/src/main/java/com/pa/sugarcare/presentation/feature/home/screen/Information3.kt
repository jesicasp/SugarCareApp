package com.pa.sugarcare.presentation.feature.home.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pa.sugarcare.R
import com.pa.sugarcare.databinding.FragmentInformation2Binding

class Information3 : Fragment() {
    private var _binding: FragmentInformation2Binding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInformation2Binding.inflate(inflater, container, false)

        return binding.root
    }

}