package com.pa.sugarcare.presentation.feature.report.yearly

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
import com.pa.sugarcare.databinding.ActivityYearlyRepBinding
import com.pa.sugarcare.models.response.YearlyListResponse
import com.pa.sugarcare.presentation.adapter.YearlyRepRVAdapter
import com.pa.sugarcare.presentation.feature.report.vm.YearlyRepVm
import com.pa.sugarcare.repository.di.CommonVmInjector
import com.pa.sugarcare.utility.Resources

class YearlyRepActivity : AppCompatActivity() {
    private var _binding: ActivityYearlyRepBinding? = null
    private val binding get() = _binding!!

    private val viewModel: YearlyRepVm by viewModels {
        CommonVmInjector.common(this)
    }

    private lateinit var adapter: YearlyRepRVAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityYearlyRepBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        setupInsets()

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        getYearlyList()
        getListReport()

    }

    private fun setupInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun getYearlyList() {
        viewModel.getYearlyList()
    }

    private fun getListReport() {
        viewModel.yearlyList.observe(this) { result ->
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

    private fun showRec(data: List<YearlyListResponse>) {
        val layoutManager = LinearLayoutManager(this)
        binding.rv.layoutManager = layoutManager
        adapter = YearlyRepRVAdapter(data)
        binding.rv.adapter = adapter

    }
}