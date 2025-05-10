package com.pa.sugarcare.presentation.feature.userprofile.producthistory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.pa.sugarcare.databinding.FragmentProductHistoryBinding
import com.pa.sugarcare.models.response.ProductDataSearchHistory
import com.pa.sugarcare.presentation.adapter.SearchedProductAdapter
import com.pa.sugarcare.presentation.feature.userprofile.vm.ProductHistoryViewModel
import com.pa.sugarcare.repository.di.CommonVmInjector
import com.pa.sugarcare.utility.Resources

class ProductHistoryFragment : Fragment() {

    private var columnCount = 1
    private var _binding: FragmentProductHistoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ProductHistoryViewModel

    private lateinit var adapter: SearchedProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        with(binding.list) {
//            layoutManager = when {
//                columnCount <= 1 -> LinearLayoutManager(context)
//                else -> GridLayoutManager(context, columnCount)
//            }
//            adapter = MyProductHistoryRecyclerViewAdapter(PlaceholderContent.ITEMS)
//        }

        viewModel = ViewModelProvider(this, CommonVmInjector.common(requireContext()))
            .get(ProductHistoryViewModel::class.java)

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

    private fun setListProduct(data: List<ProductDataSearchHistory>) {
        adapter = SearchedProductAdapter(data)
        binding.rvProduct.layoutManager = LinearLayoutManager(requireContext())
        binding.rvProduct.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ARG_COLUMN_COUNT = "column-count"

        @JvmStatic
        fun newInstance(columnCount: Int) = ProductHistoryFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_COLUMN_COUNT, columnCount)
            }
        }
    }
}
