package com.pa.sugarcare.presentation.feature.producthistory

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.pa.sugarcare.databinding.FragmentProductHistoryBinding
import com.pa.sugarcare.presentation.feature.producthistory.placeholder.PlaceholderContent

class MyProductHistoryRecyclerViewAdapter(
    private val values: List<PlaceholderContent.PlaceholderItem2>
) : RecyclerView.Adapter<MyProductHistoryRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentProductHistoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(values[position])
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(private val binding: FragmentProductHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PlaceholderContent.PlaceholderItem2) {
            binding.txtProductName.text = item.productName
            binding.txtSugar.text = "Gula ${item.sugarAmount}g"
            binding.txtMl.text = "${item.volume} ml"
        }
    }
}