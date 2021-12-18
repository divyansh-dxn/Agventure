package com.dxn.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.dxn.auth.databinding.FragmentOnBoardingBinding


class OnBoarding : Fragment() {

    private var _binding: FragmentOnBoardingBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnBoardingBinding.inflate(inflater, container, false)
        navController = findNavController()

        binding.continueConsumer.setOnClickListener {
            Log.d(TAG, "onCreateView: CLICKED")
            navController.navigate(R.id.action_onBoarding_to_auth)
        }
        return binding.root
//        return null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

    companion object {
        const val TAG = "ON_BOARDING_FRAGMENT"
    }
}