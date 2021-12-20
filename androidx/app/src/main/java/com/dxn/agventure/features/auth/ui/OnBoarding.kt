package com.dxn.agventure.features.auth.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.dxn.agventure.R
import com.dxn.agventure.databinding.FragmentOnBoardingBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class OnBoarding : Fragment() {

    private var _binding: FragmentOnBoardingBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnBoardingBinding.inflate(inflater, container, false)
        navController = findNavController()
        val bundle = Bundle()

        binding.continueConsumer.setOnClickListener {
            Log.d(TAG, "onCreateView: CLICKED")
            bundle.putInt("user_role", 0)
            navController.navigate(R.id.action_onBoarding_to_auth, bundle)
        }
        binding.continueSeller.setOnClickListener {
            Log.d(TAG, "onCreateView: CLICKED")
            bundle.putInt("user_role", 1)
            navController.navigate(R.id.action_onBoarding_to_auth, bundle)
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

    override fun onStart() {
        super.onStart()
        if (Firebase.auth.currentUser != null) {
            navController.navigate(R.id.action_onBoarding_to_settingUpUser)
        }
    }

    companion object {
        const val TAG = "ON_BOARDING_FRAGMENT"
    }
}