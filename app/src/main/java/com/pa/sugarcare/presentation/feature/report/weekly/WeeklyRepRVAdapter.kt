package com.pa.sugarcare.presentation.feature.report.weekly

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pa.sugarcare.databinding.WeeklyRepItemBinding
import com.pa.sugarcare.presentation.feature.report.ReportData

class WeeklyRepRVAdapter(private val values: List<ReportData>) :
    RecyclerView.Adapter<WeeklyRepRVAdapter.ViewHolder>() {

    class ViewHolder
        (private val binding: WeeklyRepItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ReportData) {
            binding.tvWeek.text = item.week
            binding.tvMonthYear.text = item.month_year
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = WeeklyRepItemBinding.inflate(
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