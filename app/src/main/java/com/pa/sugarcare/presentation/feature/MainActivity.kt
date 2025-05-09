package com.pa.sugarcare.presentation.feature

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.pa.sugarcare.R
import com.pa.sugarcare.databinding.ActivityMainBinding


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
                        // TODO: Handle imageBitmap
                    }
                }
            }
    }

    private fun setupNavItemSelection() {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        binding.navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_camera -> {
                    binding.fabHistory.hide()
                    openCamera()
                    true
                }

                R.id.navigation_home -> {
                    binding.fabHistory.show()
                    navController.navigate(item.itemId)
                    true
                }

                else -> {
                    binding.fabHistory.hide()
                    navController.navigate(item.itemId)
                    true
                }
            }
        }
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraLauncher.launch(intent)
    }


    companion object {
        private const val TAG = "MainActivity"
    }

}