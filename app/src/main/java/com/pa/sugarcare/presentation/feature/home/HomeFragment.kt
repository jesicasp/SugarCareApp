package com.pa.sugarcare.presentation.feature.home

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.pa.sugarcare.databinding.FragmentHomeBinding
import com.pa.sugarcare.presentation.feature.home.screen.Information1
import com.pa.sugarcare.presentation.feature.home.screen.Information2
import com.pa.sugarcare.presentation.feature.home.screen.Information3
import com.pa.sugarcare.presentation.feature.onboarding.OnBoardingViewPagerAdapter
import com.pa.sugarcare.presentation.feature.report.ReportActivity
import com.pa.sugarcare.presentation.feature.searchproduct.SearchProductActivity
import com.pa.sugarcare.utility.ImageClassifierHelper


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
        pagerAdapter = OnBoardingViewPagerAdapter(
            listOnBoardingScreen,
            childFragmentManager,
            viewLifecycleOwner.lifecycle
        )

        binding.apply {
            viewPager.adapter = pagerAdapter
            dotsIndicator.attachTo(viewPager)
        }

        binding.cvMenuOpenGallery.setOnClickListener {
            startGallery()
        }

        binding.cvMenuReport.setOnClickListener {
            goToReport()
        }

        setupSearchBar()

    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun goToReport() {
        val intent = Intent(requireContext(), ReportActivity::class.java)
        startActivity(intent)
    }
//
//    private val launcherGallery = registerForActivityResult(
//        ActivityResultContracts.PickVisualMedia()
//    ) { uri: Uri? ->
//        if (uri != null) {
//            currentImageUri = uri
//
//            imageClassifierHelper = com.pa.sugarcare.utility.ImageClassifierHelper(
//                context = requireContext(),
//                classifierListener = object : com.pa.sugarcare.utility.ImageClassifierHelper.ClassifierListener {
//                    override fun onError(error: String) {
//                        viewLifecycleOwner.lifecycleScope.launch {
//                            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
//                        }
//                    }
//
//                    override fun onResults(results: List<Classifications>?, inferenceTime: Long) {
//                        viewLifecycleOwner.lifecycleScope.launch {
//                            results?.let { it ->
//                                if (it.isNotEmpty() && it[0].categories.isNotEmpty()) {
//                                    val sortedCategories =
//                                        it[0].categories.sortedByDescending { it?.score }
//                                    val displayResult =
//                                        sortedCategories.joinToString("\n") {
//                                            "${it.label} " + NumberFormat.getPercentInstance()
//                                                .format(it.score).trim()
//                                        }
////                                    binding.tvResult.text = displayResult
////                                    binding.tvInferenceTime.text = "$inferenceTime ms"
//                                } else {
////                                    binding.tvResult.text = ""
////                                    binding.tvInferenceTime.text = ""
//                                }
//                            }
//                        }
//                    }
//                }
//            )
//            try {
//                imageClassifierHelper.classifyImage(currentImageUri!!)
//            } catch (e: Exception) {
//                Log.e("Classifier", "Error classifying image", e)
//                Toast.makeText(requireContext(), "Failed to classify image", Toast.LENGTH_SHORT)
//                    .show()
//            }
//
//
////            val productGrade = getProductGrade()
//            Log.d("Photo Picker", "Uri + $currentImageUri")
////            Log.d("Sugar Grade", "grade : + $productGrade")
////            val intent = Intent(requireContext(), ProductResultActivity::class.java)
////            intent.putExtra(ProductResultActivity.IMAGE_URI, currentImageUri.toString())
////            intent.putExtra(ProductResultActivity.PRODUCT_GRADE, productGrade)
////            startActivity(intent)
//        } else {
//            Log.d("Photo Picker", "No media selected")
//        }
//    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            Log.d("IMAGEURI", "$currentImageUri")

            // Load image dari URI ke Bitmap
            val bitmap = loadBitmapFromUri(uri) ?: run {
                Toast.makeText(requireContext(), "Gagal memuat gambar", Toast.LENGTH_SHORT).show()
                return@registerForActivityResult
            }

            // Inisialisasi ImageClassifierHelper
            val imageClassifierHelper = ImageClassifierHelper(requireContext())

            // Jalankan klasifikasi
            val result = imageClassifierHelper.classifyImage(bitmap)

            // Tampilkan hasil
            Toast.makeText(context, result, Toast.LENGTH_LONG).show()
            Log.d("CLASSIFY_RESULT", result)
        }
    }

    private fun loadBitmapFromUri(uri: Uri): Bitmap? {
        return try {
            context?.contentResolver?.openInputStream(uri)?.use { inputStream ->
                BitmapFactory.decodeStream(inputStream)
            }
        } catch (e: Exception) {
            Log.e("BitmapLoader", "Gagal membaca bitmap dari URI", e)
            null
        }
    }


    private fun getProductGrade(): String {
        return "red"
    }

    private fun setupSearchBar() {
        with(binding) {
            searchBar.setOnClickListener {
                val intent = Intent(requireContext(), SearchProductActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}