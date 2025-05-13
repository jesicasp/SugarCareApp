package com.pa.sugarcare.presentation.feature.report.monthly

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.pa.sugarcare.R
import com.pa.sugarcare.databinding.ActivityMonthlyRepBinding
import com.pa.sugarcare.models.response.MonthlyListResponse
import com.pa.sugarcare.presentation.adapter.MonthlyRepRVAdapter
import com.pa.sugarcare.presentation.feature.report.vm.MonthlyRepVm
import com.pa.sugarcare.repository.di.CommonVmInjector
import com.pa.sugarcare.utility.Resources

class MonthlyRepActivity : AppCompatActivity() {
    private var _binding: ActivityMonthlyRepBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MonthlyRepVm by viewModels {
        CommonVmInjector.common(this)
    }

    private lateinit var adapter: MonthlyRepRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMonthlyRepBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        setupInsets()

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        getMonthlyList()
        getListReport()

    }

    private fun setupInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun getMonthlyList() {
        viewModel.getMonthlyList()
    }

    private fun getListReport() {
        viewModel.monthlyList.observe(this) { result ->
            when (result) {
                is Resources.Success -> {
                    binding.progressBar.visibility = View.GONE

                    val dataList = result.data.data
                    if (!dataList.isNullOrEmpty()) {
                        showRec(dataList)
                    } else {
                        Toast.makeText(
                            this,
                            "Tidak ada report ditemukan",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                is Resources.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        this,
                        "Gagal memuat data: ${result.error}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is Resources.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun showRec(data: List<MonthlyListResponse>) {
        val layoutManager = LinearLayoutManager(this)
        binding.rv.layoutManager = layoutManager
        adapter = MonthlyRepRVAdapter(data)
        binding.rv.adapter = adapter

    }
}