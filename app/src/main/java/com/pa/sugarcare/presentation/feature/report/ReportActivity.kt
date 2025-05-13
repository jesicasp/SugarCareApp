package com.pa.sugarcare.presentation.feature.report

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.pa.sugarcare.R
import com.pa.sugarcare.databinding.ActivityReportBinding
import com.pa.sugarcare.presentation.feature.report.monthly.MonthlyRepActivity
import com.pa.sugarcare.presentation.feature.report.vm.ReportViewModel
import com.pa.sugarcare.presentation.feature.report.weekly.WeeklyRepActivity
import com.pa.sugarcare.presentation.feature.report.yearly.YearlyRepActivity
import com.pa.sugarcare.repository.di.CommonVmInjector
import com.pa.sugarcare.utility.Resources

class ReportActivity : AppCompatActivity() {
    private var _binding: ActivityReportBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ReportViewModel by viewModels {
        CommonVmInjector.common(this)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityReportBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        setupInsets()

        binding.cvWeeklyRep.setOnClickListener {
            goToWeeklyRep()
        }

        binding.cvMonthlyRep.setOnClickListener {
            goToMonthlyRep()
        }
        binding.cvYearRep.setOnClickListener {
            goToYearlyRep()
        }

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        getTodaySugarConsumption()
        observeSugarTodayResult()
        today()
    }

    private fun today() {
        viewModel.todayDate.observe(this) { formattedDate ->
            binding.tvDate.text = formattedDate
        }
    }

    private fun setupInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getTodaySugarConsumption() {
        viewModel.getToday()
        viewModel.getTodaySugarCons()

    }

    private fun observeSugarTodayResult() {
        viewModel.todaySugarCons.observe(this) { result ->
            when (result) {
                is Resources.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Resources.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.tvUserConsumption.text = result.data.data.toString()
                }

                is Resources.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Login failed: ${result.error}", Toast.LENGTH_SHORT).show()
                    Log.e(TAG, result.error)
                }
            }
        }
    }

    companion object {
        private const val TAG = "ReportActivity"
    }
}