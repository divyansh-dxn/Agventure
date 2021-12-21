package com.dxn.agventure.features.consumer.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dxn.agventure.R
import com.dxn.agventure.databinding.CartFragmentBinding

class Cart : Fragment() {

    private lateinit var viewModel: CartViewModel
    private var _binding: CartFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CartFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }


}