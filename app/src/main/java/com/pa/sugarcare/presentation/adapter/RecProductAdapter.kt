package com.pa.sugarcare.presentation.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pa.sugarcare.R
import com.pa.sugarcare.databinding.ItemProductBinding
import com.pa.sugarcare.models.response.RecProductResponse
import com.pa.sugarcare.presentation.feature.sugargrade.ProductResultActivity

class RecProductAdapter(
    private val items: List<RecProductResponse>
) : RecyclerView.Adapter<RecProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(internal val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: RecProductResponse) {
            binding.txtProductName.text = item.name
            binding.txtSugar.text = "Gula ${item.grSugarContent}g"
            binding.txtMl.text = item.netWeight
            binding.txtSugarGrade.text = item.sugarGrade

            Glide.with(binding.ivProductImage.context)
                .load(item.image)
                .into(binding.ivProductImage)

            binding.root.setOnClickListener {
                val intent = Intent(itemView.context, ProductResultActivity::class.java)
                intent.putExtra("PRODUCT_ID", item.productId)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)

        val context = holder.binding.txtSugarGrade.context
        val txtSugarGrade = holder.binding.txtSugarGrade

        txtSugarGrade.typeface = ResourcesCompat.getFont(context, R.font.poppins_regular)

        when (item.sugarGrade.lowercase()) {
            "kuning" -> {
                holder.binding.txtSugarGrade.apply {
                    text = context.getString(R.string.yellowgrade)
                    setBackgroundResource(R.drawable.yellowgrade)
                    setTextColor(ContextCompat.getColor(context, R.color.black))
                }
            }
            "merah" -> {
                holder.binding.txtSugarGrade.apply {
                    text = context.getString(R.string.redgrade)
                    setBackgroundResource(R.drawable.redgrade)
                    setTextColor(ContextCompat.getColor(context, R.color.white))
                }
            }
            "hijau" -> {
                holder.binding.txtSugarGrade.apply {
                    text = context.getString(R.string.greengrade)
                    setBackgroundResource(R.drawable.greengrade)
                    setTextColor(ContextCompat.getColor(context, R.color.black))
                }
            }
        }
    }

    override fun getItemCount(): Int = items.size
}