package com.pa.sugarcare.presentation.feature.sugargrade.alert

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.DialogFragment
import com.pa.sugarcare.R
import android.content.Context
import androidx.core.content.ContextCompat

class GradeAlertFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            val colorString = arguments?.getString(ARG_COLOR) ?: "red"

            val titleColor = getColorFromString(requireContext(),colorString)
            val (titleText, messageText) = getTitleAndMessage(requireContext(), colorString)

            val title = SpannableString(titleText)
            title.setSpan(
                ForegroundColorSpan(titleColor),
                0,
                title.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            builder.setTitle(title)
                .setMessage(messageText)
                .setPositiveButton("Ok") { dialog, _ -> dialog.dismiss() }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    companion object {
        private const val ARG_COLOR = "color"

        fun newInstance(color: String): GradeAlertFragment {
            val fragment = GradeAlertFragment()
            val args = Bundle()
            args.putString(ARG_COLOR, color)
            fragment.arguments = args
            return fragment
        }

        private fun getColorFromString(context: Context, color: String): Int {
            return when (color.lowercase()) {
                "merah" -> ContextCompat.getColor(context, R.color.red)
                "hijau" -> ContextCompat.getColor(context, R.color.green)
                "kuning" -> ContextCompat.getColor(context, R.color.dark_yellow)
                else -> Color.BLACK
            }
        }

        private fun getTitleAndMessage(context: Context, color: String): Pair<String, String> {
            return when (color.lowercase()) {
                "merah" -> context.getString(R.string.title_alert_redgrade) to context.getString(R.string.message_alert_redgrade)
                "hijau" -> context.getString(R.string.title_alert_greengrade) to context.getString(R.string.message_alert_greengrade)
                "kuning" -> context.getString(R.string.title_alert_yellowgrade) to context.getString(
                    R.string.message_alert_yellowgrade
                )

                else -> context.getString(R.string.title_alert_unknowngrade) to context.getString(R.string.message_alert_unknowngrade)
            }
        }

    }
}
