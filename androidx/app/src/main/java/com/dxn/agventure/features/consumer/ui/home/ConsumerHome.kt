package com.dxn.agventure.features.consumer.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dxn.agventure.R
import com.dxn.agventure.databinding.ConsumerHomeFragmentBinding
import com.dxn.agventure.features.auth.AuthActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class ConsumerHome : Fragment() {

    private var _binding: ConsumerHomeFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel by hiltNavGraphViewModels<ConsumerHomeViewModel>(R.id.consumer_navigation)

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ConsumerHomeFragmentBinding.inflate(inflater, container, false)
        initUi()
        return binding.root
    }


    private fun initUi() {
        val productListAdapter = ProductListAdapter({}, {})
        binding.productsRecycler.adapter = productListAdapter
        binding.productsRecycler.layoutManager =
            GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
        binding.signOut.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(requireActivity(), AuthActivity::class.java))
        }

        lifecycleScope.launchWhenCreated {
            viewModel.products.collect { uiState ->
                when (uiState) {
                    is ConsumerHomeViewModel.HomeUiState.Success -> {
                        productListAdapter.products = uiState.products
                        productListAdapter.notifyDataSetChanged()
                    }
                    is ConsumerHomeViewModel.HomeUiState.Loading -> {

                    }
                    is ConsumerHomeViewModel.HomeUiState.Error -> {

                    }
                }
            }
        }

    }

}