package com.pa.sugarcare.presentation.feature.suggestproduct

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.pa.sugarcare.R
import com.pa.sugarcare.databinding.ActivitySuggestProductBinding
import com.pa.sugarcare.models.request.SuggestedProductRequest
import com.pa.sugarcare.presentation.feature.MainActivity
import com.pa.sugarcare.presentation.feature.suggestproduct.vm.SuggestProductVm
import com.pa.sugarcare.repository.di.CommonVmInjector
import com.pa.sugarcare.utility.Resources
import java.io.File

class SuggestProductActivity : AppCompatActivity() {
    private var _binding: ActivitySuggestProductBinding? = null
    private val binding get() = _binding!!
    private val categoryOptions = listOf("Pilih kategori", "drink", "food")
    private var imageBitmap: Bitmap? = null

    private val viewModel: SuggestProductVm by viewModels {
        CommonVmInjector.common(this)
    }

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
            if (bitmap != null) {
                binding.imageCapturePreview.setImageBitmap(bitmap)
                imageBitmap = bitmap
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivitySuggestProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupInsets()
        setupCategory()
        observePostProduct()
        setupListeners()
        cameraListeners()



        binding.topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

    }

    private fun setupInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupCategory() {

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            categoryOptions
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinnerCategory.adapter = adapter
    }

    private fun cameraListeners() {
        binding.imageCapturePreview.setOnClickListener {
            cameraLauncher.launch(null)
        }

    }

    private fun bitmapToFile(context: Context, bitmap: Bitmap): File {
        val file = File(context.cacheDir, "image_${System.currentTimeMillis()}.jpg")
        file.outputStream().use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
            it.flush()
        }
        return file
    }


    private fun observePostProduct() {
        viewModel.sProduct.observe(this) { result ->
            when (result) {
                is Resources.Loading -> {
                    binding.progressBar.bringToFront()
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Resources.Success -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Berhasil Mengirimkan Data Produk", Toast.LENGTH_SHORT)
                        .show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }

                is Resources.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        this,
                        "Gagal Mengirimkan Data Produk: ${result.error}",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    Log.e(TAG, result.error)
                }
            }
        }
    }

    private fun setupListeners() {
        binding.tvDone.setOnClickListener {
            postProduct()
        }
    }

    private fun postProduct() {
        val name = binding.edPName.text.toString()
        val grSugarStr = binding.edPSugar.text.toString()
        val netWeightStr = binding.edPNetWeight.text.toString()
        val servingsPerPackageStr = binding.edPServing.text.toString()
        val servingSizeMlStr = binding.edPServingVol.text.toString()

        var isValid = true

        if (name.isBlank()) {
            binding.edPName.error = "Nama produk wajib diisi"
            isValid = false
        }

        if (grSugarStr.isBlank()) {
            binding.edPSugar.error = "Gula per sajian wajib diisi"
            isValid = false
        }

        if (netWeightStr.isBlank()) {
            binding.edPNetWeight.error = "Berat bersih wajib diisi"
            isValid = false
        }

        if (servingsPerPackageStr.isBlank()) {
            binding.edPServing.error = "Jumlah sajian per kemasan wajib diisi"
            isValid = false
        }

        if (servingSizeMlStr.isBlank()) {
            binding.edPServingVol.error = "Ukuran sajian (ml) wajib diisi"
            isValid = false
        }

        if (!isValid) return

        val grSugarContent = grSugarStr.toDouble()
        val netWeight = netWeightStr.toDouble()
        val servingsPerPackage = servingsPerPackageStr.toDouble()
        val servingSizeMl = servingSizeMlStr.toDouble()

        val selectedCategory = binding.spinnerCategory.selectedItem.toString()
        if (selectedCategory == "Pilih kategori") {
            Toast.makeText(this, "Silakan pilih kategori", Toast.LENGTH_SHORT).show()
            return
        }


        val request = SuggestedProductRequest(
            name = name,
            category = selectedCategory,
            grSugarContent = grSugarContent,
            netWeight = netWeight,
            servingsPerPackage = servingsPerPackage,
            servingSizeMl = servingSizeMl
        )

        if (imageBitmap == null) {
            binding.tvImageError.visibility = View.VISIBLE
        } else {
            binding.tvImageError.visibility = View.GONE
            val imageFile = bitmapToFile(this, imageBitmap!!)

            viewModel.postConsumeProduct(imageFile, request)
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val TAG = "SignUpViewModel"
    }
}