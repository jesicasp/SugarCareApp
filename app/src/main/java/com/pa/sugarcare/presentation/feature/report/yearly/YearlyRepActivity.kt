package com.pa.sugarcare.presentation.feature.report.yearly

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.pa.sugarcare.R
import com.pa.sugarcare.databinding.ActivityMonthlyRepBinding
import com.pa.sugarcare.databinding.ActivityReportBinding
import com.pa.sugarcare.databinding.ActivityYearlyRepBinding
import com.pa.sugarcare.presentation.feature.report.Report
import com.pa.sugarcare.presentation.feature.report.monthly.MonthlyRepRVAdapter

class YearlyRepActivity : AppCompatActivity() {
    private var _binding: ActivityYearlyRepBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityYearlyRepBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.rv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = YearlyRepRVAdapter(Report.ITEMS)
        }
    }
}