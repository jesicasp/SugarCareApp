package com.pa.sugarcare.presentation.feature.sugargrade.tab

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.pa.sugarcare.databinding.FragmentOtherInfoBinding
import com.pa.sugarcare.models.response.RecProductResponse
import com.pa.sugarcare.presentation.adapter.RecProductAdapter
import com.pa.sugarcare.presentation.feature.sugargrade.vm.SugarGradeViewModel
import com.pa.sugarcare.repository.di.CommonVmInjector
import com.pa.sugarcare.utility.Resources

class OtherInfoFragment : Fragment() {
    private var _binding: FragmentOtherInfoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SugarGradeViewModel by viewModels {
        CommonVmInjector.common(requireContext())
    }

    private lateinit var adapter: RecProductAdapter

    private var productId: Int = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productId = arguments?.getInt("product_id") ?: -1

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

        if (viewModel.detailProduct.value == null) {
            getDetailProduct(productId)
        }
        observeDetailProduct()

        getProduct()

        viewModel.listRecProduct.observe(viewLifecycleOwner) { resource ->
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

    override fun onResume() {
        super.onResume()
        if (viewModel.detailProduct.value == null) {
            getDetailProduct(productId)
        }
    }

    private fun getDetailProduct(productId: Int) {
        viewModel.getDetailProduct(productId)
    }

    private fun getProduct() {
        viewModel.getRecProduct(productId)
    }

    private fun observeDetailProduct() {
        viewModel.detailProduct.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resources.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Resources.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val dataProduct = result.data.data

                    binding.tvInfo.text = dataProduct?.information
                    Log.d(TAG, "Successfully get rec product")
                }

                is Resources.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Log.e(TAG, result.error)
                }
            }
        }
    }

    private fun setListProduct(data: List<RecProductResponse>) {
        adapter = RecProductAdapter(data)
        binding.rvProduct.layoutManager = LinearLayoutManager(requireContext())
        binding.rvProduct.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "OtherInfoFragment"
    }
}
