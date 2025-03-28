package com.pa.sugarcare.presentation.feature.sugargrade.tab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.pa.sugarcare.databinding.FragmentOtherInfoBinding
import com.pa.sugarcare.presentation.feature.userprofile.producthistory.placeholder.PlaceholderContent

class OtherInfoFragment : Fragment() {
    private var columnCount = 1
    private var _binding: FragmentOtherInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOtherInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.list) {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            adapter = ProductRecRecyclerViewAdapter(PlaceholderContent.ITEMS)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ARG_COLUMN_COUNT = "column-count"

        @JvmStatic
        fun newInstance(columnCount: Int) = OtherInfoFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_COLUMN_COUNT, columnCount)
            }
        }
    }
}
