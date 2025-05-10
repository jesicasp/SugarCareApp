package com.pa.sugarcare.presentation.feature.userprofile.myconsumption

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.pa.sugarcare.databinding.FragmentMyConsumptionProductBinding
import com.pa.sugarcare.models.response.ConsumedProductResponse
import com.pa.sugarcare.presentation.adapter.ConsumptionProductAdapter
import com.pa.sugarcare.presentation.feature.userprofile.vm.MyConsumptionViewModel
import com.pa.sugarcare.repository.di.CommonVmInjector
import com.pa.sugarcare.utility.Resources

class MyConsumptionFragment : Fragment() {

    private var _binding: FragmentMyConsumptionProductBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MyConsumptionViewModel

    private lateinit var adapter: ConsumptionProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyConsumptionProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, CommonVmInjector.common(requireContext()))
            .get(MyConsumptionViewModel::class.java)

        getProduct()

        viewModel.productsResult.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resources.Success -> {
                    val products = resource.data.data
                    products?.let {
                        setListProduct(it)
                        binding.progressBar.visibility = View.GONE
                    }
                }

                is Resources.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), "Gagal memuat produk", Toast.LENGTH_SHORT)
                        .show()
                }

                is Resources.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE

                }
            }
        }
    }

    private fun getProduct() {
        viewModel.getProducts()
    }

    private fun setListProduct(data: List<ConsumedProductResponse>) {
        adapter = ConsumptionProductAdapter(data)
        binding.rvProduct.layoutManager = LinearLayoutManager(requireContext())
        binding.rvProduct.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
