package com.dxn.seller.ui.fragments.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.dxn.data.models.CatalogueProduct
import com.dxn.data.models.Product
import com.dxn.data.models.User
import com.dxn.seller.R
import com.dxn.seller.databinding.AddFragmentBinding
import kotlinx.coroutines.flow.collect
import javax.inject.Inject


class Add : Fragment() {

    //    private val viewModel by viewModels<AddViewModel>()
    private val viewModel: AddViewModel by hiltNavGraphViewModels(R.id.app_navigation)
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

        return binding.root
    }

    private fun initUi() {
        lifecycleScope.launchWhenCreated {
            viewModel.products.collect { productList ->
                products = productList
                val items = productList.map { it.name }
                val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                    requireContext(),
                    android.R.layout.simple_dropdown_item_1line,
                    items
                )
                binding.menu.setAdapter(adapter)
            }
            viewModel.loggedInUser.collect { loggedInUser ->
                user = loggedInUser
            }
        }

        binding.submitBtn.setOnClickListener {
            val price = binding.inputPrice.toString().toFloat()
            val quantity = binding.inputQuantity.toString().toInt()
            val productId = products.filter { it.name == binding.menu.text.toString() }[0].id
           if(user!=null) {
               val catalogueProduct = CatalogueProduct(
                   productId=productId,
                   sellerId = user!!.phoneNumber,
                   quantity = quantity,
                   price = price
               )
               viewModel.addProduct(catalogueProduct)
           } else {
               Toast.makeText(requireContext(),"Please wait some time",Toast.LENGTH_SHORT).show()
           }

        }
    }

}