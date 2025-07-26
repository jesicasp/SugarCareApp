package com.pa.sugarcare.presentation.feature.signup

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.pa.sugarcare.R
import com.pa.sugarcare.databinding.ActivitySignUpBinding
import com.pa.sugarcare.models.request.RegisterRequest
import com.pa.sugarcare.presentation.feature.MainActivity
import com.pa.sugarcare.presentation.feature.signin.SignInActivity
import com.pa.sugarcare.presentation.feature.signup.vm.SignUpViewModel
import com.pa.sugarcare.repository.di.CommonVmInjector
import com.pa.sugarcare.utility.Resources
import android.util.Patterns


class SignUpActivity : AppCompatActivity() {
    private var _binding: ActivitySignUpBinding? = null
    private val binding get() = _binding!!
    private var isPasswordVisible = false
    private var isPasswordConfVisible = false


    private val viewModel: SignUpViewModel by viewModels {
        CommonVmInjector.common(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySignUpBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        setupInsets()
        observeRegisterResult()
        setupSigninListener()
        setupSignupListener()
        setupPasswordToggle()

    }

    private fun setupInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun observeRegisterResult() {
        viewModel.registerResult.observe(this) { result ->
            when (result) {
                is Resources.Loading -> {
                    binding.progressBar.bringToFront()
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Resources.Success -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Register Success", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }

                is Resources.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Register failed: ${result.error}", Toast.LENGTH_SHORT)
                        .show()
                    Log.e(TAG, result.error)
                }
            }
        }
    }

    private fun setupSignupListener() {
        binding.btnSignUp.setOnClickListener {
            val email = binding.edEmail.text.toString()
            val password = binding.edPassword.text.toString()
            val passwordConfirmation = binding.edConfPassword.text.toString()
            val name = binding.edFullName.text.toString()

            var isValid = true

            binding.txtEmailError.text = ""
            binding.txtPassError.text = ""
            binding.txtPassConfError.text = ""
            binding.txtNameError.text = ""

            if (email.isEmpty() && password.isEmpty() && passwordConfirmation.isEmpty() && name.isEmpty()) {
                binding.txtEmailError.text = getString(R.string.fill_this_field)
                binding.txtPassError.text = getString(R.string.fill_this_field)
                binding.txtPassConfError.text = getString(R.string.fill_this_field)
                binding.txtNameError.text = getString(R.string.fill_this_field)
                isValid = false
            } else {
                if (email.isEmpty()) {
                    binding.txtEmailError.text = getString(R.string.fill_this_field)
                    isValid = false
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    binding.txtEmailError.text = getString(R.string.invalid_email_format)
                    isValid = false
                }

                if (password.isEmpty()) {
                    binding.txtPassError.text = getString(R.string.fill_this_field)
                    isValid = false
                }
                if (passwordConfirmation.isEmpty()) {
                    binding.txtPassConfError.text = getString(R.string.fill_this_field)
                    isValid = false
                }
                if (name.isEmpty()) {
                    binding.txtNameError.text = getString(R.string.fill_this_field)
                    isValid = false
                }

                if (password.isNotEmpty() && passwordConfirmation.isNotEmpty() && password != passwordConfirmation) {
                    binding.txtPassConfError.text = getString(R.string.pass_match)
                    isValid = false
                }
            }

            if (isValid) {
                val registerRequest = RegisterRequest(password, passwordConfirmation, name, email)
                viewModel.register(registerRequest)
            }
        }

    }

    private fun setupSigninListener() {
        binding.txtSignIn.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    private fun setupPasswordToggle() {
        val editText = binding.edPassword

        editText.setOnTouchListener { v, event ->
            val DRAWABLE_END = 2
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableEnd = editText.compoundDrawables[DRAWABLE_END]
                if (drawableEnd != null && event.rawX >= (editText.right - drawableEnd.bounds.width())) {
                    isPasswordVisible = !isPasswordVisible

                    if (isPasswordVisible) {
                        editText.inputType =
                            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                        editText.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.baseline_remove_red_eye_24,
                            0
                        )
                    } else {
                        editText.inputType =
                            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                        editText.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.baseline_visibility_off_24,
                            0
                        )
                    }

                    editText.setSelection(editText.text?.length ?: 0)
                    return@setOnTouchListener true
                }
            }
            false
        }

        val editTextConf = binding.edConfPassword


        editTextConf.setOnTouchListener { v, event ->
            val DRAWABLE_END = 2
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableEnd = editTextConf.compoundDrawables[DRAWABLE_END]
                if (drawableEnd != null && event.rawX >= (editTextConf.right - drawableEnd.bounds.width())) {
                    isPasswordConfVisible = !isPasswordConfVisible

                    if (isPasswordConfVisible) {
                        editTextConf.inputType =
                            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                        editTextConf.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.baseline_remove_red_eye_24,
                            0
                        )
                    } else {
                        editTextConf.inputType =
                            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                        editTextConf.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.baseline_visibility_off_24,
                            0
                        )
                    }

                    editTextConf.setSelection(editTextConf.text?.length ?: 0)
                    return@setOnTouchListener true
                }
            }
            false
        }
    }

    companion object {
        private const val TAG = "SignUpActivity"
    }
}