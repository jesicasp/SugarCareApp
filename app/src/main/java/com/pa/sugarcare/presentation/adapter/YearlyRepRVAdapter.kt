package com.pa.sugarcare.presentation.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pa.sugarcare.databinding.YearlyRepItemBinding
import com.pa.sugarcare.models.response.WeeklyListResponse
import com.pa.sugarcare.presentation.feature.report.yearly.YearlyChartRepActivity

class YearlyRepRVAdapter(private val values: List<WeeklyListResponse>) :
    RecyclerView.Adapter<YearlyRepRVAdapter.ViewHolder>() {

    class ViewHolder
        (private val binding: YearlyRepItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: WeeklyListResponse) {
            binding.tvYear.text = item.year.toString()
            binding.root.setOnClickListener {
                val intent = Intent(itemView.context, YearlyChartRepActivity::class.java)
                intent.putExtra("YEAR", item.year)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = YearlyRepItemBinding.inflate(
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