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

class ManualSearchAlertFragment : DialogFragment() {
    interface OnAlertConfirmedListener {
        fun onAlertConfirmed()
    }

    private var listener: OnAlertConfirmedListener? = null

    fun setOnAlertConfirmedListener(listener: OnAlertConfirmedListener) {
        this.listener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(requireActivity())

        val titleColor = ContextCompat.getColor(requireContext(), R.color.teal_green)
        val title = SpannableString("Produk Ini Tidak Sesuai?")
        title.setSpan(
            ForegroundColorSpan(titleColor),
            0,
            title.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        builder.setTitle(title)
            .setMessage(
                "Coba cari produk di kolom pencarian yuk.\n" +
                        "Jika tidak ditemukan, klik ikon + untuk memberikan masukan produk baru."
            )
            .setPositiveButton("Ok") { dialog, _ ->
                listener?.onAlertConfirmed()
                dialog.dismiss()
            }


        val dialog = builder.create()

        dialog.setOnShowListener {
            val negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
            negativeButton.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.teal_green
                )
            )

            val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        }

        return dialog
    }

    companion object {
        private const val ARG_PRODUCT_NAME = "Nama Produk"

        fun newInstance(): ManualSearchAlertFragment {
            return ManualSearchAlertFragment().apply {

            }
        }


    }

}