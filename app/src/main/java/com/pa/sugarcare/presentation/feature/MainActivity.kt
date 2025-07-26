package com.pa.sugarcare.presentation.feature

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.pa.sugarcare.R
import com.pa.sugarcare.databinding.ActivityMainBinding
import com.pa.sugarcare.presentation.feature.sugargrade.ProductResultActivity
import com.pa.sugarcare.utility.ImageClassifierHelper


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var cameraLauncher: ActivityResultLauncher<Intent>

    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupNavigation()
        setupCameraLauncher()
        setupNavItemSelection()

        val navigateTo = intent.getStringExtra("navigate_to")
        if (navigateTo == "user_profile") {
            binding.navView.selectedItemId = R.id.navigation_profile
        }


    }

    private fun setupBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setupNavigation() {
        navController = findNavController(R.id.nav_host_fragment_activity_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.navigation_home, R.id.navigation_camera, R.id.navigation_profile)
        )

        binding.navView.setupWithNavController(navController)
    }


    private fun setupCameraLauncher() {
        cameraLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val imageBitmap = result.data?.extras?.get("data") as Bitmap?
                    imageBitmap?.let {
                        // Inisialisasi ImageClassifierHelper
                        val imageClassifierHelper = ImageClassifierHelper(this)

                        // Jalankan klasifikasi
                        val result = imageClassifierHelper.classifyImage(imageBitmap)

                        // Tampilkan hasil
                        //Toast.makeText(context, result, Toast.LENGTH_LONG).show()
                        Log.d("CLASSIFY_RESULT", result)
                        val productId = result.toInt()

                        val intent = Intent(this, ProductResultActivity::class.java)
                        intent.putExtra("PRODUCT_ID", productId)
                        intent.putExtra("IS_USING_CAMERA", true)
                        startActivity(intent)
                    }
                }
            }
    }

    private fun setupNavItemSelection() {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        binding.navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_camera -> {
                    openCamera()
                    true
                }

                R.id.navigation_home -> {
                    navController.navigate(item.itemId)
                    true
                }

                else -> {
                    navController.navigate(item.itemId)
                    true
                }
            }
        }
    }

    private fun openCamera() {
        val alertDialog = androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Petunjuk Pemotretan")
            .setMessage(
                "• Gunakanlah ukuran 1:1\n" +
                        "• Potret produk hingga terlihat keseluruhan sisi depannya\n" +
                        "• Hindari background yang mencolok saat memotret produk"
            )
            .setPositiveButton("Lanjutkan") { _, _ ->
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                cameraLauncher.launch(intent)
            }
            .create()

        alertDialog.show()
    }

    

    companion object {
        private const val TAG = "MainActivity"
    }

}