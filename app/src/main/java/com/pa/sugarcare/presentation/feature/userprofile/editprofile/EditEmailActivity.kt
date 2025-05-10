package com.pa.sugarcare.presentation.feature.userprofile.editprofile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.appbar.MaterialToolbar
import com.pa.sugarcare.R
import com.pa.sugarcare.databinding.ActivityEditEmailBinding
import com.pa.sugarcare.models.request.UpdateUserRequest
import com.pa.sugarcare.presentation.feature.userprofile.vm.UserProfileViewModel
import com.pa.sugarcare.repository.di.CommonVmInjector
import com.pa.sugarcare.utility.Resources

class EditEmailActivity : AppCompatActivity() {

    private var _binding: ActivityEditEmailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UserProfileViewModel by viewModels {
        CommonVmInjector.common(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityEditEmailBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        setupInsets()
        defaultEmail()
        observeEmail()
        setupListeners()

        val toolbar = findViewById<MaterialToolbar>(R.id.topAppBar)
        toolbar.setNavigationOnClickListener {
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

    private fun defaultEmail() {
        val userName = intent.getStringExtra("user_email")
        binding.edEmail.setText(userName)
    }

    private fun editEmail() {
        val updatedEmail = binding.edEmail.text.toString().trim()

        if (updatedEmail.isEmpty()) {
            binding.edEmail.error = "Email tidak boleh kosong"
            return
        }

        if (!updatedEmail.matches(Regex("^[A-Za-z0-9+_.-]+@(.+)$"))) {
            binding.edEmail.error = "Format email tidak valid"
            return
        }

        val request = UpdateUserRequest(email = updatedEmail)

        viewModel.updateUser(request)

    }

    private fun setupListeners() {
        binding.tvDone.setOnClickListener {
            editEmail()
        }
    }

    private fun observeEmail() {
        viewModel.updateUser.observe(this) { result ->
            when (result) {
                is Resources.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Resources.Success -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Email berhasil diperbarui", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, EditProfilActivity::class.java)
                    startActivity(intent)
                }

                is Resources.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        this,
                        "Gagal memperbarui email: ${result.error}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}