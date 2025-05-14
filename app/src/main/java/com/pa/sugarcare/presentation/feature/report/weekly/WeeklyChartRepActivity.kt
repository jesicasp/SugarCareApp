package com.pa.sugarcare.presentation.feature.report.weekly

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
import com.pa.sugarcare.databinding.ActivityWeeklyChartRepBinding
import com.pa.sugarcare.models.response.WeeklyChartResponse
import com.pa.sugarcare.presentation.feature.report.vm.WeeklyChartRepVm
import com.pa.sugarcare.repository.di.CommonVmInjector
import com.pa.sugarcare.utility.Resources

private class DateValueFormatter(private val dates: List<String>) : ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        val index = value.toInt() - 1
        return if (index in dates.indices) dates[index] else ""
    }
}

class WeeklyChartRepActivity : AppCompatActivity() {
    private var _binding: ActivityWeeklyChartRepBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WeeklyChartRepVm by viewModels {
        CommonVmInjector.common(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityWeeklyChartRepBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        setupInsets()
        binding.topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val repId: Int = intent.getIntExtra("REPORT_ID", 0)

        val month: String? = intent.getStringExtra("MONTH")
        val year: Int = intent.getIntExtra("YEAR", 0)
        "$month $year".also { binding.tvMonthYear.text = it }

        getWeeklyData(repId)
        observeResults()
    }

    private fun setupInsets(){
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun getWeeklyData(year: Int) {
        viewModel.getWeeklyData(year)
    }

    private fun observeResults() {
        viewModel.weeklyData.observe(this) { result ->
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

    private fun setupBarChart(weeklyData: List<WeeklyChartResponse>) {
        val barChart = binding.barChart

        val barEntries = weeklyData.mapIndexed { index, data ->
            BarEntry(index.toFloat() + 1, data.totalSugar.toFloat())
        }

        val barDataSet = BarDataSet(barEntries, "").apply {
            colors = weeklyData.map { ContextCompat.getColor(this@WeeklyChartRepActivity, it.colorResId) }
            valueTextSize = 16f
        }

        barDataSet.valueFormatter = object : ValueFormatter() {
            override fun getBarLabel(barEntry: BarEntry?): String {
                return "${barEntry?.y?.toDouble()}gr"
            }
        }

        val barData = BarData(barDataSet)
        barChart.data = barData
        barChart.setFitBars(true)
        barChart.animateY(1000)
        barChart.description.isEnabled = false
        barChart.legend.isEnabled = false

        barChart.xAxis.valueFormatter = DateValueFormatter(weeklyData.map { it.day.toString() })
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
