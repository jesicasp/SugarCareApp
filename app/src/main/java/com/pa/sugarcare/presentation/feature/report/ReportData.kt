package com.pa.sugarcare.presentation.feature.report

object Report {
    val ITEMS: MutableList<ReportData> = mutableListOf()
    val ITEM_MAP: MutableMap<String, ReportData> = mutableMapOf()

    private const val COUNT = 10

    init {
        repeat(COUNT) { i ->
            addItem(createReportItem(i))
        }
    }

    private fun createReportItem(position: Int) = ReportData(
        id = position.toString(),
        week = listOf("Minggu 1", "Minggu 2", "Minggu 3", "Minggu 4", "Minggu 5")[position % 5],
        month_year = listOf("Januari 2025", "Februari 2025", "Maret 2025")[position % 3]
    )

    private fun addItem(item: ReportData) {
        ITEMS.add(item)
        ITEM_MAP[item.id] = item
    }
}

data class ReportData(
    val id: String,
    val week: String,
    val month_year: String
)
