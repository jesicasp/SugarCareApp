package com.pa.sugarcare.presentation.feature.searchproduct

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.pa.sugarcare.R
import com.pa.sugarcare.databinding.ActivitySearchProductBinding
import com.pa.sugarcare.models.response.DataItem
import com.pa.sugarcare.presentation.adapter.ProductAdapter
import com.pa.sugarcare.presentation.feature.searchproduct.vm.SearchProductViewModel
import com.pa.sugarcare.repository.di.CommonVmInjector
import com.pa.sugarcare.utility.Resources

class SearchProductActivity : AppCompatActivity() {

    private var _binding: ActivitySearchProductBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchProductViewModel by viewModels {
        CommonVmInjector.common(this)
    }

    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySearchProductBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        setupInsets()
        listProduct()
        setupSearch()
        observeSearchResults()

        viewModel.productsResult.observe(this) { resource ->
            when (resource) {
                is Resources.Success -> {
                    val products = resource.data.data
                    setListProduct(products)
                }
                is Resources.Error -> {
                    Toast.makeText(this, "Gagal memuat produk", Toast.LENGTH_SHORT).show()
                }
                is Resources.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE

                }
            }
        }

    }

    private fun setupInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setListProduct(data: List<DataItem>) {
        adapter = ProductAdapter(data)
        binding.rvProduct.adapter = adapter
    }

    private fun showRec(data: List<DataItem>) {
        val layoutManager = LinearLayoutManager(this)
        binding.rvProduct.layoutManager = layoutManager
        adapter = ProductAdapter(data)
        binding.rvProduct.adapter = adapter

    }

    private fun listProduct() {
        viewModel.products.observe(this) { result ->
            when (result) {
                is Resources.Success -> {
                    binding.progressBar.visibility = View.GONE

                    val dataList = result.data.data
                    if (dataList.isNotEmpty()) {
                        showRec(dataList)
                    } else {
                        Toast.makeText(
                            this,
                            "Tidak ada produk ditemukan",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                is Resources.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        this,
                        "Gagal memuat data: ${result.error}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is Resources.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }
    }


    private fun setupSearch() {
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {
                        val query = searchView.text.toString()
                        Log.d("SEARCHPRODUCT", query)
                        viewModel.findProducts(query)
                        searchBar.setText(query)
                        searchView.hide()
                        return@setOnEditorActionListener true
                    }
                    false
                }

        }

    }

    private fun observeSearchResults() {
        viewModel.productsResult.observe(this) { result ->
            when (result) {
                is Resources.Success -> {
                    binding.progressBar.visibility = View.GONE

                    val dataList = result.data.data
                    if (dataList.isNotEmpty()) {
                        showRec(dataList)
                    } else {
                        Toast.makeText(this, "Tidak ada produk ditemukan", Toast.LENGTH_SHORT).show()
                    }
                }
                is Resources.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Gagal memuat data: ${result.error}", Toast.LENGTH_SHORT).show()
                }
                is Resources.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }
    }

}