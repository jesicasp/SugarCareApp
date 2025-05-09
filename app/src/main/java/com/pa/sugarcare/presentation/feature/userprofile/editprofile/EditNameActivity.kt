package com.pa.sugarcare.presentation.feature.userprofile.editprofile

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.pa.sugarcare.R
import com.pa.sugarcare.databinding.ActivityEditEmailBinding
import com.pa.sugarcare.databinding.ActivityEditNameBinding
import com.pa.sugarcare.presentation.feature.userprofile.vm.UserProfileViewModel
import com.pa.sugarcare.repository.di.CommonVmInjector

class EditNameActivity : AppCompatActivity() {
    private var _binding: ActivityEditNameBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UserProfileViewModel by viewModels {
        CommonVmInjector.common(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_name)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}