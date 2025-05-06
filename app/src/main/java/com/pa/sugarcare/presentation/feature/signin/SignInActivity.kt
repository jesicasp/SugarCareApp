package com.pa.sugarcare.presentation.feature.signin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.pa.sugarcare.R
import com.pa.sugarcare.databinding.ActivitySignInBinding
import com.pa.sugarcare.models.request.LoginRequest
import com.pa.sugarcare.presentation.feature.MainActivity
import com.pa.sugarcare.presentation.feature.signin.vm.SigninViewModel
import com.pa.sugarcare.presentation.feature.signup.SignUpActivity
import com.pa.sugarcare.repository.di.CommonVmInjector
import com.pa.sugarcare.utility.Resources

class SignInActivity : AppCompatActivity() {
    private var _binding: ActivitySignInBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SigninViewModel by viewModels {
        CommonVmInjector.common(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySignInBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        setupInsets()
        observeLoginResult()
        setupSigninListeners()
        setupSignupListener()
    }

    private fun setupInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun observeLoginResult() {
        viewModel.loginResult.observe(this) { result ->
            when (result) {
                is Resources.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Resources.Success -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }

                is Resources.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Login failed: ${result.error}", Toast.LENGTH_SHORT).show()
                    Log.e(TAG, result.error)
                }
            }
        }
    }

    private fun setupSigninListeners() {
        binding.btnSignIn.setOnClickListener {
            val email = binding.edEmail.text.toString()
            val password = binding.edPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                val loginRequest = LoginRequest(email, password)
                viewModel.login(loginRequest)
            } else {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupSignupListener() {
        binding.txtSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    companion object {
        private const val TAG = "SignInActivity"
    }
}