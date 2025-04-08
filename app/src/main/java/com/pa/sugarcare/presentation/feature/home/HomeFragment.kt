package com.pa.sugarcare.presentation.feature.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.pa.sugarcare.databinding.FragmentHomeBinding
import com.pa.sugarcare.presentation.feature.onboarding.OnBoardingViewPagerAdapter
import com.pa.sugarcare.presentation.feature.home.screen.Information1
import com.pa.sugarcare.presentation.feature.home.screen.Information2
import com.pa.sugarcare.presentation.feature.home.screen.Information3
import com.pa.sugarcare.presentation.feature.sugargrade.ProductResultActivity


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var pagerAdapter: OnBoardingViewPagerAdapter
    private val listOnBoardingScreen = listOf(
        Information1(),
        Information2(),
        Information3()
    )
    private var currentImageUri: Uri? = null

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
        pagerAdapter = OnBoardingViewPagerAdapter(listOnBoardingScreen, childFragmentManager, viewLifecycleOwner.lifecycle)

        binding.apply {
            viewPager.adapter = pagerAdapter
            dotsIndicator.attachTo(viewPager)
        }

        binding.cvMenuOpenGallery.setOnClickListener {
            startGallery()
        }

        setupSearchBar()

    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            val productGrade = getProductGrade()
            Log.d("Photo Picker", "Uri + $currentImageUri")
            Log.d("Sugar Grade", "grade : + $productGrade")
            val intent = Intent(requireContext(), ProductResultActivity::class.java)
            intent.putExtra(ProductResultActivity.IMAGE_URI, currentImageUri.toString())
            intent.putExtra(ProductResultActivity.PRODUCT_GRADE, productGrade)
            startActivity(intent)
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun getProductGrade(): String {
        return "red"
    }

    private fun setupSearchBar(){
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.setText(searchView.text)
                    searchView.hide()
                    false
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}