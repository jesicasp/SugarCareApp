package com.pa.sugarcare.presentation.feature.report

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.pa.sugarcare.R
import com.pa.sugarcare.databinding.ActivityReportBinding
import com.pa.sugarcare.presentation.feature.report.monthly.MonthlyRepActivity
import com.pa.sugarcare.presentation.feature.report.weekly.WeeklyRepActivity
import com.pa.sugarcare.presentation.feature.report.yearly.YearlyRepActivity

class ReportActivity : AppCompatActivity() {
    private var _binding: ActivityReportBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityReportBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.cvWeeklyRep.setOnClickListener {
            goToWeeklyRep()
        }

        binding.cvMonthlyRep.setOnClickListener {
            goToMonthlyRep()
        }
        binding.cvYearRep.setOnClickListener {
            goToYearlyRep()
        }
    }

    private fun goToWeeklyRep() {
        val intent = Intent(this, WeeklyRepActivity::class.java)
        startActivity(intent)
    }

    private fun goToMonthlyRep() {
        val intent = Intent(this, MonthlyRepActivity::class.java)
        startActivity(intent)
    }

    private fun goToYearlyRep() {
        val intent = Intent(this, YearlyRepActivity::class.java)
        startActivity(intent)
    }
}