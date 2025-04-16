package com.pa.sugarcare.presentation.feature.report.yearly

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
import com.pa.sugarcare.databinding.ActivityYearlyChartRepBinding


private data class BarChartData(
    val gram: Float,
    val color: Int,
    val month: String
)

private class DateValueFormatter(private val monthName: List<String>) : ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        return monthName.getOrNull(value.toInt() - 1) ?: ""
    }
}

class YearlyChartRepActivity : AppCompatActivity() {
    private var _binding: ActivityYearlyChartRepBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityYearlyChartRepBinding.inflate(layoutInflater)
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
                dummyData.map { ContextCompat.getColor(this@YearlyChartRepActivity, it.color) }
            valueTextSize = 16f
        }

        val barData = BarData(barDataSet)
        barChart.data = barData
        barChart.setFitBars(true)
        barChart.animateY(1000)
        barChart.description.isEnabled = false
        barChart.legend.isEnabled = false

        barChart.xAxis.valueFormatter = DateValueFormatter(dummyData.map { it.month })
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
            BarChartData(
                1200f,
                R.color.green,
                "Jan"
            ),
            BarChartData(
                1500f,
                R.color.yellow,
                "Feb"
            ),
            BarChartData(
                1050f,
                R.color.green,
                "Mar"
            ),
            BarChartData(
                1100f,
                R.color.green,
                "Apr"
            ),
            BarChartData(
                2100f,
                R.color.red,
                "Mei"
            ),
            BarChartData(
                2000f,
                R.color.red,
                "Jun"
            ),
            BarChartData(
                1400f,
                R.color.yellow,
                "Jul"
            ),
            BarChartData(
                900f,
                R.color.green,
                "Agu"
            ),
            BarChartData(
                1400f,
                R.color.green,
                "Sept"
            ),
            BarChartData(
                1500f,
                R.color.yellow,
                "Okt"
            ),
            BarChartData(
                1300f,
                R.color.yellow,
                "Nov"
            ),
            BarChartData(
                1400f,
                R.color.green,
                "Des"
            ),
        )
    }
}