package com.dxn.auth.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.dxn.auth.R
import com.dxn.auth.data.models.User
import com.dxn.auth.databinding.FragmentOnBoardingBinding
import com.dxn.auth.databinding.FragmentSettingUpUserBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

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
            user = firestore.collection("users_collection").document(auth.uid!!).get().await()
                .toObject(User::class.java)!!
            if(user.role==0) {
                Toast.makeText(requireContext(), "Signed In as consumer", Toast.LENGTH_SHORT).show()
                // navigate to user app
            } else {
                Toast.makeText(requireContext(), "Signed In as seller", Toast.LENGTH_SHORT).show()
                // navigate to seller app
            }
        }

        return binding.root
    }

}