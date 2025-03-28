package com.pa.sugarcare.presentation.feature.userprofile.myconsumption

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.pa.sugarcare.presentation.feature.userprofile.myconsumption.placeholder.PlaceholderContent.PlaceholderItem
import com.pa.sugarcare.databinding.FragmentProductBinding

class MyConsumptionRecyclerViewAdapter(
    private val values: List<PlaceholderItem>
) : RecyclerView.Adapter<MyConsumptionRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(values[position]) // Langsung bind tanpa variabel tambahan
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(private val binding: FragmentProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PlaceholderItem) {
            binding.txtDate.text = item.date
            binding.txtProductName.text = item.productName
            binding.txtSugar.text = "Gula ${item.sugarAmount}g"
            binding.txtMl.text = "${item.volume} ml"
        }
    }
}
