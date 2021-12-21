package com.dxn.agventure.features.auth.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.dxn.agventure.databinding.FragmentSettingUpUserBinding
import com.dxn.agventure.features.consumer.ConsumerActivity
import com.dxn.agventure.features.seller.SellerActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import com.dxn.data.models.User


@AndroidEntryPoint
class SettingUpUser : Fragment() {

    private var _binding: FragmentSettingUpUserBinding? = null
    private val binding get() = _binding!!
    private lateinit var user: User
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingUpUserBinding.inflate(inflater, container, false)
        auth = Firebase.auth
        firestore = FirebaseFirestore.getInstance()

        lifecycleScope.launch {
            user = firestore.collection("users_collection").whereEqualTo("uid", auth.uid!!).get()
                .await()
                .toObjects(User::class.java)[0]
            if(user.role==0) {
                // navigate to user app
                startActivity(Intent(requireContext(),ConsumerActivity::class.java))
                requireActivity().finish()
            } else {
                startActivity(Intent(requireContext(),SellerActivity::class.java))
                requireActivity().finish()
            }
        }

        return binding.root
    }

}