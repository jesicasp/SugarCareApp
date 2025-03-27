package com.pa.sugarcare.presentation.feature.producthistory.placeholder

import java.util.ArrayList
import java.util.HashMap

object PlaceholderContent {

    val ITEMS: MutableList<PlaceholderItem2> = ArrayList()
    val ITEM_MAP: MutableMap<String, PlaceholderItem2> = HashMap()
    private val COUNT = 25

    init {
        for (i in 1..COUNT) {
            addItem(createPlaceholderItem2(i))
        }
    }

    private fun addItem(item: PlaceholderItem2) {
        ITEMS.add(item)
        ITEM_MAP[item.id] = item  // ✅ Menggunakan cara lebih idiomatis
    }

    private fun createPlaceholderItem2(position: Int): PlaceholderItem2 { // ✅ Menambahkan return type
        val sampleProducts = listOf("You C1000", "Teh Botol", "Coca Cola", "Sprite", "Fanta")

        return PlaceholderItem2(
            id = position.toString(),
            productName = sampleProducts[position % sampleProducts.size],
            sugarAmount = (10..40).random(),
            volume = listOf(200, 250, 300, 350, 500).random()
        )
    }

    data class PlaceholderItem2(
        val id: String,
        val productName: String,
        val sugarAmount: Int,
        val volume: Int
    ) {
        override fun toString(): String = productName
    }
}
