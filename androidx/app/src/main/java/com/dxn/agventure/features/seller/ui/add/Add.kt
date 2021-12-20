package com.dxn.agventure.features.seller.ui.add

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.hardware.input.InputManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import com.dxn.agventure.R
import com.dxn.agventure.databinding.AddFragmentBinding
import com.dxn.data.models.CatalogueProduct
import com.dxn.data.models.Product
import com.dxn.data.models.User
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import android.app.Activity
import androidx.navigation.fragment.findNavController


class Add : Fragment() {

    //    private val viewModel by viewModels<AddViewModel>()
    private val viewModel: AddViewModel by hiltNavGraphViewModels(R.id.seller_navigation)
    private var products: List<Product> = listOf()
    private var user: User? = null


    private var _binding: AddFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AddFragmentBinding.inflate(inflater, container, false)
        initUi()
        lifecycleScope.launch {
            user = viewModel.getLoggedInUser()
        }

        return binding.root
    }

    private fun initUi() {
        lifecycleScope.launchWhenCreated {
            viewModel.products.collect { products ->
                showSuccessScreen(products)
            }
        }
        lifecycleScope.launch {
            viewModel.isLoading.collect { isLoading ->
                if (isLoading) {
                    binding.loading.visibility = View.VISIBLE
                } else {
                    binding.loading.visibility = View.GONE
                }
            }
        }

        binding.submitBtn.setOnClickListener {
            val price = binding.inputPrice.text.toString().toFloat()
            val quantity = binding.inputQuantity.text.toString().toInt()
            val product = products.filter { it.name == binding.menu.text.toString() }[0]
            if (user != null) {
                val catalogueProduct = CatalogueProduct(
                    productId = product.id,
                    sellerId = user!!.phoneNumber,
                    quantity = quantity,
                    price = price,
                    name = product.name,
                    photoUrl = product.photoUrl
                )
                viewModel.addProduct(catalogueProduct)
                binding.inputQuantity.setText("")
                binding.inputPrice.setText("")
                hideKeyboard(requireActivity())
                findNavController().popBackStack()
            } else {
                Toast.makeText(requireContext(), "Please wait some time", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun showSuccessScreen(productList: List<Product>) {
        products = productList
        val items = productList.map { it.name }
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            items
        )
        binding.menu.setAdapter(adapter)
    }

    fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}