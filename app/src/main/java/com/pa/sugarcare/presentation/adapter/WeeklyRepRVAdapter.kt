package com.pa.sugarcare.presentation.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pa.sugarcare.R
import com.pa.sugarcare.databinding.WeeklyRepItemBinding
import com.pa.sugarcare.models.response.WeeklyListResponse
import com.pa.sugarcare.presentation.feature.report.weekly.WeeklyChartRepActivity

class WeeklyRepRVAdapter(
    private val items: List<WeeklyListResponse>
) : RecyclerView.Adapter<WeeklyRepRVAdapter.ViewHolder>() {

    inner class ViewHolder(internal val binding: WeeklyRepItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(item: WeeklyListResponse) {
            val context = itemView.context
            val drawable = context.getDrawable(R.drawable.icon_week_rep)
            val sizeInDp = 40
            val sizeInPx = (sizeInDp * context.resources.displayMetrics.density).toInt()
            drawable?.setBounds(0, 0, sizeInPx, sizeInPx)
            binding.tvReportName.setCompoundDrawables(null, null, drawable, null)

            val formattedText = item.report?.replace("\\n", "\n")
            binding.tvReportName.text = formattedText

            binding.root.setOnClickListener {
                val intent = Intent(itemView.context, WeeklyChartRepActivity::class.java)
                intent.putExtra("REPORT_ID", item.id)
                itemView.context.startActivity(intent)
            }
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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size


}