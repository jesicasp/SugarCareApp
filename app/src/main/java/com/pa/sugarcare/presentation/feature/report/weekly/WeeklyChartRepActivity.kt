package com.pa.sugarcare.presentation.feature.report.weekly

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
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

data class BarChartData(
    val gram: Float,
    val color: Int,
    val date: String
)

class DateValueFormatter(private val dates: List<String>) : ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        val index = value.toInt() - 1
        return if (index in dates.indices) dates[index] else ""
    }
}

class WeeklyChartRepActivity : AppCompatActivity() {
    private var _binding: ActivityWeeklyChartRepBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityWeeklyChartRepBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupBarChart()
    }

    private fun setupBarChart() {
        val barChart = binding.barChart
        val dummyData = getDummyData()

        val barEntries = dummyData.mapIndexed { index, data ->
            BarEntry(index.toFloat() + 1, data.gram)
        }

        val barDataSet = BarDataSet(barEntries, "").apply {
            colors = dummyData.map { ContextCompat.getColor(this@WeeklyChartRepActivity, it.color) }
            valueTextSize = 16f
        }

        val barData = BarData(barDataSet)
        barChart.data = barData
        barChart.setFitBars(true)
        barChart.animateY(1000)
        barChart.description.isEnabled = false
        barChart.legend.isEnabled = false

        barChart.xAxis.valueFormatter = DateValueFormatter(dummyData.map { it.date })
        barChart.xAxis.granularity = 1f
        barChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        barChart.xAxis.axisLineWidth = 3f
        barChart.axisLeft.axisLineWidth = 3f
        barChart.axisRight.isEnabled = false
        barChart.xAxis.setDrawGridLines(false)
        barChart.axisLeft.setDrawGridLines(false)
        barChart.axisRight.setDrawGridLines(false)


    }

    private fun getDummyData(): List<BarChartData> {
        return listOf(
            BarChartData(45f, R.color.yellow, "01"),
            BarChartData(30f, R.color.green, "02"),
            BarChartData(65f, R.color.red, "03"),
            BarChartData(25f, R.color.yellow, "04"),
            BarChartData(38f, R.color.green, "05"),
            BarChartData(40f, R.color.green, "06"),
            BarChartData(28f, R.color.yellow, "07"),
        )
    }
}
