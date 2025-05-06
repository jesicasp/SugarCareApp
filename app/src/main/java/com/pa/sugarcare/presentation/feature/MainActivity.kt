package com.pa.sugarcare.presentation.feature

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.pa.sugarcare.R
import com.pa.sugarcare.databinding.ActivityMainBinding
import com.pa.sugarcare.presentation.feature.onboarding.vm.OnBoardViewModel
import com.pa.sugarcare.repository.di.StateInjection
import com.pa.sugarcare.utility.TokenStorage

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var cameraLauncher: ActivityResultLauncher<Intent>

    private val onBoardViewModel: OnBoardViewModel by viewModels {
        StateInjection.onBoardInjection(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupNavigation()
        setupCameraLauncher()
        setupNavItemSelection()
        stateCheck()

//        val token = TokenStorage.getToken()
//        Log.d(TAG, "Access Token: $token")
    }

    private fun setupBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setupNavigation() {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.navigation_home, R.id.navigation_camera, R.id.navigation_profile)
        )

        binding.navView.setupWithNavController(navController)
    }

    private fun setupCameraLauncher() {
        cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
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

    private fun stateCheck() {
        onBoardViewModel.getOnBoardState().observe(this) {
            if (it.isNullOrBlank()) {
                println("is Board $it")
                val intent = Intent(this@MainActivity, OnBoardingActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                userViewModel.getAccessToken().observe(this@MainActivity) { token ->
                    if (token.isNullOrEmpty()) {
                        binding.main.setBackgroundColor(resources.getColor(R.color.md_theme_primary))
                        Utils.changeStatusBarColorWhite(this)
                        val intent = Intent(this@MainActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        if (Utils.checkConnection(this@MainActivity)) {
                            updateToken()
                        }
                        val intent = Intent(this@MainActivity, DashboardActivity::class.java)
                        startActivity(intent)
                        finishAffinity()
                    }
                }
            }
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }

}