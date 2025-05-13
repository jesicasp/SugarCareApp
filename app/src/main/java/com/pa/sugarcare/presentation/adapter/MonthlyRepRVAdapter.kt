package com.pa.sugarcare.presentation.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pa.sugarcare.databinding.MonthlyRepItemBinding
import com.pa.sugarcare.models.response.WeeklyListResponse
import com.pa.sugarcare.presentation.feature.report.monthly.MonthlyChartRepActivity

class MonthlyRepRVAdapter(private val values: List<WeeklyListResponse>) :
    RecyclerView.Adapter<MonthlyRepRVAdapter.ViewHolder>() {

    class ViewHolder
        (private val binding: MonthlyRepItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: WeeklyListResponse) {
            binding.tvMonthYear.text = "${item.month} ${item.year}"
            binding.root.setOnClickListener {
                val intent = Intent(itemView.context, MonthlyChartRepActivity::class.java)
                intent.putExtra("MONTH", item.month)
                intent.putExtra("YEAR", item.year)
                itemView.context.startActivity(intent)
            }
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