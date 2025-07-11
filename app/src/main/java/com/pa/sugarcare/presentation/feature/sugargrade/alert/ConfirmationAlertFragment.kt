package com.pa.sugarcare.presentation.feature.sugargrade.alert

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.pa.sugarcare.R

class ConfirmationAlertFragment : DialogFragment() {
    interface OnAlertConfirmedListener {
        fun onAlertConfirmed()
        fun onAlertCancelled()
    }

    private var listener: OnAlertConfirmedListener? = null

    fun setOnAlertConfirmedListener(listener: OnAlertConfirmedListener) {
        this.listener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val productName = arguments?.getString(ARG_PRODUCT_NAME) ?: "Produk"

        val builder = AlertDialog.Builder(requireActivity())

        val titleColor = ContextCompat.getColor(requireContext(), R.color.teal_green)
        val title = SpannableString(productName)
        title.setSpan(
            ForegroundColorSpan(titleColor),
            0,
            title.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        builder.setTitle(title)
            .setMessage("Apakah benar produk ini yang Anda cari?")
            .setPositiveButton("Benar") { dialog, _ ->
                listener?.onAlertConfirmed()
                dialog.dismiss()
            }
            .setNegativeButton("Tidak") { _, _ ->
                listener?.onAlertCancelled()
                dismiss()
            }

        val dialog = builder.create()

        dialog.setOnShowListener {
            val negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
            negativeButton.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.black
                )
            )

            val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.teal_green))
        }

        return dialog
    }

    companion object {
        private const val ARG_PRODUCT_NAME = "Nama Produk"

        fun newInstance(
            productName: String,
        ): ConfirmationAlertFragment {
            return ConfirmationAlertFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PRODUCT_NAME, productName)
                }
            }
        }


    }

}