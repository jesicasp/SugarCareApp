package com.pa.sugarcare.presentation.feature.sugargrade

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.pa.sugarcare.R
import com.pa.sugarcare.databinding.ActivityProductResultBinding
import com.pa.sugarcare.models.request.SearchProductRequest
import com.pa.sugarcare.presentation.feature.sugargrade.alert.GradeAlertFragment
import com.pa.sugarcare.presentation.feature.sugargrade.vm.ProductResultViewModel
import com.pa.sugarcare.repository.di.CommonVmInjector
import com.pa.sugarcare.utility.Resources

class ProductResultActivity : AppCompatActivity() {
    private var _binding: ActivityProductResultBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProductResultViewModel by viewModels {
        CommonVmInjector.common(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityProductResultBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        val sectionsPagerAdapter = SugarGradePagerAdapter(this)
        binding.viewPager.adapter = sectionsPagerAdapter

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        setupInsets()

        val productId = intent.getIntExtra("PRODUCT_ID", -1)
        postSearchProduct(productId)
        observePostProduct()
        getDetailProduct(productId)
        observeDetailProduct()

    }

    private fun setupInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun getDetailProduct(productId: Int) {
        viewModel.getDetailProduct(productId)
    }

    private fun observeDetailProduct() {
        viewModel.detailProduct.observe(this) { result ->
            when (result) {
                is Resources.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Resources.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val dataProduct = result.data.data

                    val imageUri = getImageUri()
                    if (imageUri != null) {
                        Log.d("IMAGE", "$imageUri")
                        binding.ivProduct.setImageURI(imageUri)
                    } else {
                        val image = dataProduct?.image
                        Glide.with(this)
                            .load(image)
                            .into(binding.ivProduct)
                    }

                    val grade = dataProduct?.sugarGrade?.lowercase()
                    Log.d("CEKSUGARGRADE", "$grade")

                    grade?.let { showAlert(it) }

                    binding.toolbarTitle.text = dataProduct?.name

                    Log.d(TAG, "Successfully get Detail product")
                }

                is Resources.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Log.e(TAG, result.error)
                }
            }
        }
    }

    private fun getImageUri(): Uri? {
        val uriString = intent.getStringExtra(IMAGE_URI)
        Log.d("getImageUri()", uriString.toString())
        return uriString?.let { Uri.parse(it) }
    }

    private fun showAlert(color: String) {
        if (!isFinishing && !isDestroyed) {
            val dialog = GradeAlertFragment.newInstance(color)
            dialog.show(supportFragmentManager, "GRADE_ALERT")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun postSearchProduct(productId: Int) {
        val searchProductRequest = SearchProductRequest(productId)
        viewModel.postProduct(searchProductRequest)
    }

    private fun observePostProduct() {
        viewModel.product.observe(this) { result ->
            when (result) {
                is Resources.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Resources.Success -> {
                    binding.progressBar.visibility = View.GONE
                    Log.d(TAG, "Search product POST success")
                }

                is Resources.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Log.e(TAG, result.error)
                }
            }
        }
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_sugargrade,
            R.string.tab_otherinfo
        )

        const val IMAGE_URI = "image_uri"
        const val PRODUCT_GRADE = "product_grade"
        const val TAG = "ProductResultActivity"
    }
}