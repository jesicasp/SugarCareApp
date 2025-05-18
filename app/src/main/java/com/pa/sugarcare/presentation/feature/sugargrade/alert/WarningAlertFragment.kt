package com.pa.sugarcare.presentation.feature.sugargrade.alert

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.pa.sugarcare.R
import com.pa.sugarcare.R.color.red


class WarningAlertFragment : DialogFragment() {


    interface OnAlertConfirmedListener {
        fun onAlertConfirmed()
    }

    private var listener: OnAlertConfirmedListener? = null

    fun setOnAlertConfirmedListener(listener: OnAlertConfirmedListener) {
        this.listener = listener
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = activity?.let {
            val builder = AlertDialog.Builder(it)

            val colorString = arguments?.getString(ARG_COLOR) ?: "warning"
            val todayConsumption = arguments?.getDouble("today_consumption") ?: 0.0
            val productGram = arguments?.getDouble("product_gram") ?: 0.0

            val titleColor = getColorFromString(requireContext(), colorString)
            val titleText = getTitleAndMessage(requireContext(), colorString).first

            val messageText = "Konsumsi gula hari ini: $todayConsumption gram\n" +
                    "Gula produk ini: $productGram gram\n\n" +
                    "Jika kamu konsumsi produk ini, total gula harian akan melebihi batas yang disarankan."

            val title = SpannableString(titleText)
            title.setSpan(
                ForegroundColorSpan(titleColor),
                0,
                title.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            builder.setTitle(title)
                .setMessage(messageText)
                .setPositiveButton("Konsumsi") { dialog, _ ->
                    listener?.onAlertConfirmed()
                    dialog.dismiss()
                }
                .setNegativeButton("Batal") { _, _ ->
                    dismiss()
                }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")

        dialog.setOnShowListener {
            val negativeButton = (dialog as AlertDialog).getButton(AlertDialog.BUTTON_NEGATIVE)
            negativeButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.teal_green))

            val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))  // Ganti dengan warna yang kamu mau

        }

        return dialog
    }


    companion object {
        private const val ARG_COLOR = "color"

        private const val ARG_TODAY_CONSUMPTION = "today_consumption"
        private const val ARG_PRODUCT_GRAM = "product_gram"

        fun newInstance(
            color: String,
            todayConsumption: Double,
            productGram: Double
        ): WarningAlertFragment {
            return WarningAlertFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_COLOR, color)
                    putDouble(ARG_TODAY_CONSUMPTION, todayConsumption)
                    putDouble(ARG_PRODUCT_GRAM, productGram)
                }
            }
        }

        private fun getColorFromString(context: Context, color: String): Int {
            return when (color.lowercase()) {
                "warning" -> ContextCompat.getColor(context, red)
                else -> Color.BLACK
            }
        }

        private fun getTitleAndMessage(context: Context, color: String): Pair<String, String> {
            return when (color.lowercase()) {
                "warning" -> context.getString(R.string.title_alert_warngrade) to context.getString(
                    R.string.message_alert_warngrade
                )

                else -> context.getString(R.string.title_alert_unknowngrade) to context.getString(R.string.message_alert_unknowngrade)
            }
        }
    }
}