package com.pa.sugarcare.presentation.feature.sugargrade

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.pa.sugarcare.R
import com.pa.sugarcare.databinding.ActivityProductResultBinding
import com.pa.sugarcare.presentation.feature.sugargrade.alert.GradeAlertFragment

class ProductResultActivity : AppCompatActivity() {
    private var _binding: ActivityProductResultBinding? = null
    private val binding get() = _binding!!

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

        val imageUri = getImageUri()
        if (imageUri != null) {
            Log.d("IMAGE", "$imageUri")
            binding.ivProduct.setImageURI(imageUri)

            val grade = getProductGrade()
            grade?.let { showAlert(it) }
        }

    }

    private fun getImageUri(): Uri? {
        val uriString = intent.getStringExtra(IMAGE_URI)
        Log.d("getImageUri()", uriString.toString())
        return uriString?.let { Uri.parse(it) }
    }

    private fun getProductGrade(): String? {
        val grade = intent.getStringExtra(PRODUCT_GRADE)
        Log.d("getProductGrade()", grade.toString())
        return grade
    }

    private fun showAlert() {
        if (!isFinishing && !isDestroyed) {
            GradeAlertFragment().show(supportFragmentManager, "GRADE_ALERT")
        }
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

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_sugargrade,
            R.string.tab_otherinfo
        )

        const val IMAGE_URI = "image_uri"
        const val PRODUCT_GRADE = "product_grade"
    }
}