package com.pa.sugarcare.presentation.feature.sugargrade.tab

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.pa.sugarcare.R
import com.pa.sugarcare.databinding.FragmentSugarGradeBinding
import com.pa.sugarcare.models.request.ConsumeProductRequest
import com.pa.sugarcare.presentation.feature.MainActivity
import com.pa.sugarcare.presentation.feature.sugargrade.vm.SugarGradeViewModel
import com.pa.sugarcare.repository.di.CommonVmInjector
import com.pa.sugarcare.utility.Resources

class SugarGradeFragment : Fragment() {
    private var _binding: FragmentSugarGradeBinding? = null
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
        _binding = FragmentSugarGradeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (viewModel.detailProduct.value == null) {
            getDetailProduct(productId)
        }
        observeDetailProduct()
        setupConsumeButton()
        observePostConsume()

    }

    override fun onResume() {
        super.onResume()
        if (viewModel.detailProduct.value == null) {
            getDetailProduct(productId)
        }
    }

    private fun setupConsumeButton() {
        binding.btnConsume.setOnClickListener {
            Toast.makeText(requireContext(), "Produk berhasil dikonsumsi!", Toast.LENGTH_SHORT)
                .show()
            postConsumeProduct(productId)
        }
    }

    private fun getDetailProduct(productId: Int) {
        viewModel.getDetailProduct(productId)
    }

    private fun postConsumeProduct(productId: Int) {
        val amount = 1
        val request = ConsumeProductRequest(amount, productId)
        viewModel.postConsumeProduct(request)
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

                    val grade = dataProduct?.sugarGrade?.lowercase()
                    grade?.let { setupSugarGrade(it) }
                    Log.d("CEKSUGARGRADE", "$grade")


                    Log.d(TAG, "Successfully get Detail product")
                }

                is Resources.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Log.e(TAG, result.error)
                }
            }
        }
    }

    private fun observePostConsume() {
        viewModel.consumeProduct.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resources.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Resources.Success -> {
                    binding.progressBar.visibility = View.GONE
                    Log.d(TAG, "You consumed this product")
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }

                is Resources.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Log.e(TAG, result.error)
                    Toast.makeText(requireContext(), "Error: ${result.error}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun setupSugarGrade(grade: String) {
        when (grade) {
            "merah" -> {
                binding.tvSugarGrade.text = getString(R.string.redgrade)
                binding.tvSugarGrade.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.red
                    )
                )
                binding.tvSugarGrade.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.circle_redgrade,
                    0,
                    0,
                    0
                )
                binding.tvGradeDetail.text = getString(R.string.desc_redgrade)
            }

            "kuning" -> {
                binding.tvSugarGrade.text = getString(R.string.yellowgrade)
                binding.tvSugarGrade.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.dark_yellow
                    )
                )
                binding.tvSugarGrade.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.circle_yellowgrade,
                    0,
                    0,
                    0
                )
                binding.tvGradeDetail.text = getString(R.string.desc_yellowgrade)
            }

            "hijau" -> {
                binding.tvSugarGrade.text = getString(R.string.greengrade)
                binding.tvSugarGrade.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.green
                    )
                )
                binding.tvSugarGrade.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.circle_greengrade,
                    0,
                    0,
                    0
                )
                binding.tvGradeDetail.text = getString(R.string.desc_greengrade)
            }
        }

    }

    companion object {
        private const val TAG = "SugarGradeFragment"
    }
}