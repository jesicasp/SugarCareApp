package com.pa.sugarcare.presentation.feature.userprofile.myconsumption.placeholder

import java.util.ArrayList
import java.util.HashMap

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object PlaceholderContent {

    /**
     * An array of sample (placeholder) items.
     */
    val ITEMS: MutableList<PlaceholderItem> = ArrayList()

    /**
     * A map of sample (placeholder) items, by ID.
     */
    val ITEM_MAP: MutableMap<String, PlaceholderItem> = HashMap()

    private val COUNT = 25

    init {
        // Add some sample items.
        for (i in 1..COUNT) {
            addItem(createPlaceholderItem(i))
        }
    }

    private fun addItem(item: PlaceholderItem) {
        ITEMS.add(item)
        ITEM_MAP.put(item.id, item)
    }

    private fun createPlaceholderItem(position: Int): PlaceholderItem {
        val sampleProducts = listOf("You C1000", "Teh Botol", "Coca Cola", "Sprite", "Fanta")
        val sampleDates = listOf("26 Januari 2025", "27 Januari 2025", "28 Januari 2025", "29 Januari 2025", "30 Januari 2025")

        return PlaceholderItem(
            id = position.toString(),
            date = sampleDates[position % sampleDates.size],
            productName = sampleProducts[position % sampleProducts.size],
            sugarAmount = (10..40).random(),
            volume = listOf(200, 250, 300, 350, 500).random()
        )
    }


    /**
     * A placeholder item representing a piece of content.
     */
    data class PlaceholderItem(
        val id: String,
        val date: String,
        val productName: String,
        val sugarAmount: Int,
        val volume: Int
    ) {
        override fun toString(): String = productName
    }

}