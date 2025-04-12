package com.pa.sugarcare.presentation.feature.report.monthly

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pa.sugarcare.databinding.MonthlyRepItemBinding
import com.pa.sugarcare.presentation.feature.report.ReportData

class MonthlyRepRVAdapter(private val values: List<ReportData>) :
    RecyclerView.Adapter<MonthlyRepRVAdapter.ViewHolder>() {

    class ViewHolder
        (private val binding: MonthlyRepItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ReportData) {
            binding.tvMonthYear.text = item.month_year
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MonthlyRepItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = values.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(values[position])
    }
}