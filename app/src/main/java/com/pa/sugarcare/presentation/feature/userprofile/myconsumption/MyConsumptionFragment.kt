package com.pa.sugarcare.presentation.feature.userprofile.myconsumption

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pa.sugarcare.databinding.FragmentProductListBinding
import com.pa.sugarcare.presentation.feature.userprofile.myconsumption.placeholder.PlaceholderContent
class MyConsumptionFragment : Fragment() {

    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup RecyclerView
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context) // Pakai LinearLayoutManager
            adapter = MyConsumptionRecyclerViewAdapter(PlaceholderContent.ITEMS)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null  // Hindari memory leak
    }
}
