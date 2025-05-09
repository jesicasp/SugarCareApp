package com.pa.sugarcare.presentation.feature.userprofile.editprofile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.pa.sugarcare.R
import com.pa.sugarcare.databinding.ActivityEditProfilBinding
import com.pa.sugarcare.presentation.feature.MainActivity
import com.pa.sugarcare.presentation.feature.userprofile.vm.EditProfileViewModel
import com.pa.sugarcare.repository.di.CommonVmInjector
import com.pa.sugarcare.utility.Resources
import com.pa.sugarcare.utility.TokenStorage

class EditProfilActivity : AppCompatActivity() {

    private var _binding: ActivityEditProfilBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EditProfileViewModel by viewModels {
        CommonVmInjector.common(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityEditProfilBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        setupInsets()
        setDefaultEmail()
        getUserData()
        observeUserData()

        onBackPressedDispatcher.addCallback(this) {
            val intent = Intent(this@EditProfilActivity, MainActivity::class.java)
            intent.putExtra("navigate_to", "user_profile")
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

    }

    private fun setDefaultEmail() {
        val email = intent.getStringExtra("user_email")
        binding.edEmail.setText(email ?: "")
    }

    private fun setupInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun getUserData() {
        viewModel.getDetailUser()
    }

    private fun observeUserData() {
        Log.d("USERDATA", "masuk observeUserData()")

        viewModel.dataUser.observe(this) { result ->
            when (result) {
                is Resources.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Resources.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.edName.setText(result.data.data.name)
                    binding.edEmail.setText(result.data.data.email)

                    setupEditListeners()
                }

                is Resources.Error -> {
                    Log.d("USERDATA", "Token: ${TokenStorage.getToken()}")
                    binding.progressBar.visibility = View.GONE
                    Log.e(TAG, result.error)
                }
            }
        }
    }

    private fun setupEditListeners() {
        binding.edName.setOnClickListener {
            val intent = Intent(this, EditNameActivity::class.java)
            intent.putExtra("user_name", binding.edName.text.toString())
            startActivity(intent)
        }

        binding.edEmail.setOnClickListener {
            val intent = Intent(this, EditEmailActivity::class.java)
            intent.putExtra("user_email", binding.edEmail.text.toString())
            startActivity(intent)
        }
    }


    companion object {
        private const val TAG = "EditProfileFragment"
    }
}