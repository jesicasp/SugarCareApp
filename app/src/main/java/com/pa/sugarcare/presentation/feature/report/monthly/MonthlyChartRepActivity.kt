package com.pa.sugarcare.presentation.feature.report.monthly

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
import com.pa.sugarcare.databinding.ActivityMonthlyChartRepBinding


private data class BarChartData(
    val gram: Float,
    val color: Int,
    val week: String
)

private class DateValueFormatter(private val week: List<String>) : ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        return week.getOrNull(value.toInt() - 1) ?: ""
    }
}


class MonthlyChartRepActivity : AppCompatActivity() {
    private var _binding: ActivityMonthlyChartRepBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMonthlyChartRepBinding.inflate(layoutInflater)
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
            colors =
                dummyData.map { ContextCompat.getColor(this@MonthlyChartRepActivity, it.color) }
            valueTextSize = 16f
        }

        val barData = BarData(barDataSet)
        barChart.data = barData
        barChart.setFitBars(true)
        barChart.animateY(1000)
        barChart.description.isEnabled = false
        barChart.legend.isEnabled = false

        barChart.xAxis.valueFormatter = DateValueFormatter(dummyData.map { it.week })
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
            BarChartData(250f, R.color.yellow, "Minggu 1"),
            BarChartData(150f, R.color.green, "Minggu 2"),
            BarChartData(325f, R.color.red, "Minggu 3"),
            BarChartData(140f, R.color.yellow, "Minggu 4"),
        )
    }
}