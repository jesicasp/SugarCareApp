package com.pa.sugarcare.presentation.feature.sugargrade.tab

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.pa.sugarcare.databinding.FragmentOtherInfoBinding
import com.pa.sugarcare.presentation.feature.sugargrade.vm.SugarGradeViewModel
import com.pa.sugarcare.repository.di.CommonVmInjector
import com.pa.sugarcare.utility.Resources

class OtherInfoFragment : Fragment() {
    private var _binding: FragmentOtherInfoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SugarGradeViewModel by viewModels {
        CommonVmInjector.common(requireContext())
    }

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


                    Log.d(TAG, "Successfully get Detail product")
                }

                is Resources.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Log.e(TAG, result.error)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "OtherInfoFragment"
    }
}
