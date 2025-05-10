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
import com.pa.sugarcare.databinding.ActivityEditNameBinding
import com.pa.sugarcare.models.request.UpdateUserRequest
import com.pa.sugarcare.presentation.feature.userprofile.vm.UserProfileViewModel
import com.pa.sugarcare.repository.di.CommonVmInjector
import com.pa.sugarcare.utility.Resources

class EditNameActivity : AppCompatActivity() {
    private var _binding: ActivityEditNameBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UserProfileViewModel by viewModels {
        CommonVmInjector.common(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityEditNameBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        setupInsets()
        defaultName()
        observeName()
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

    private fun defaultName(){
        val userName = intent.getStringExtra("user_name")
        binding.edName.setText(userName)
    }

    private fun editName() {
        val updatedName = binding.edName.text.toString().trim()

        if (updatedName.isEmpty()) {
            binding.edName.error = "Nama tidak boleh kosong"
            return
        }

        val request = UpdateUserRequest(name = updatedName)

        viewModel.updateUser(request)

    }

    private fun setupListeners() {
        binding.tvDone.setOnClickListener {
            editName()
        }
    }

    private fun observeName() {
        viewModel.updateUser.observe(this) { result ->
            when (result) {
                is Resources.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Resources.Success -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Nama berhasil diperbarui", Toast.LENGTH_SHORT).show()
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