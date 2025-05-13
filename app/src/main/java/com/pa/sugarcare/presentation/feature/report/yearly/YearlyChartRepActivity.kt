package com.pa.sugarcare.presentation.feature.report.yearly

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.pa.sugarcare.R
import com.pa.sugarcare.databinding.ActivityYearlyChartRepBinding
import com.pa.sugarcare.models.response.YearlyChartResponse
import com.pa.sugarcare.presentation.feature.report.vm.YearlyChartVm
import com.pa.sugarcare.repository.di.CommonVmInjector
import com.pa.sugarcare.utility.Resources

private class DateValueFormatter(private val monthName: List<String>) : ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        return monthName.getOrNull(value.toInt() - 1) ?: ""
    }
}

class YearlyChartRepActivity : AppCompatActivity() {
    private var _binding: ActivityYearlyChartRepBinding? = null
    private val binding get() = _binding!!

    private val viewModel: YearlyChartVm by viewModels {
        CommonVmInjector.common(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityYearlyChartRepBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        setupInsets()
        binding.topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val year: Int = intent.getIntExtra("YEAR", 0)

        binding.tvYear.text = "Tahun $year"

        getYearlyData(year)
        observeResults()
    }

    private fun setupInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun getYearlyData(year: Int) {
        viewModel.getYearlyData(year)
    }

    private fun observeResults() {
        viewModel.yearlyData.observe(this) { result ->
            when (result) {
                is Resources.Success -> {
                    binding.progressBar.visibility = View.GONE

                    val dataList = result.data.data
                    if (dataList != null) {
                        if (dataList.isNotEmpty()) {
                            setupBarChart(dataList)
                        } else {
                            Toast.makeText(
                                this,
                                "Tidak ada report yang ditemukan",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

                is Resources.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Gagal memuat data: ${result.error}", Toast.LENGTH_SHORT)
                        .show()
                }

                is Resources.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }
    }


    private fun setupBarChart(yearlyData: List<YearlyChartResponse>) {
        val barChart = binding.barChart

        val barEntries = yearlyData.mapIndexed { index, data ->
            BarEntry(index.toFloat() + 1, data.totalSugar.toFloat())
        }

        val barDataSet = BarDataSet(barEntries, "").apply {
            colors =
                yearlyData.map {
                    ContextCompat.getColor(
                        this@YearlyChartRepActivity,
                        it.colorResId
                    )
                }
            valueTextSize = 16f
        }

        val barData = BarData(barDataSet)
        barChart.data = barData
        barChart.setFitBars(true)
        barChart.animateY(1000)
        barChart.description.isEnabled = false
        barChart.legend.isEnabled = false

        barChart.xAxis.valueFormatter = DateValueFormatter(
            yearlyData.map { it.month }
        )
        barChart.xAxis.granularity = 1f
        barChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        barChart.xAxis.axisLineWidth = 3f
        barChart.axisLeft.axisLineWidth = 3f
        barChart.axisRight.isEnabled = false
        barChart.xAxis.setDrawGridLines(false)
        barChart.axisLeft.setDrawGridLines(false)
        barChart.axisRight.setDrawGridLines(false)


    }

}