package com.dxn.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.dxn.auth.databinding.FragmentAuthBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit

class Auth : Fragment() {

    private lateinit var auth: FirebaseAuth
    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var firestore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthBinding.inflate(inflater, container, false)
        FirebaseApp.initializeApp(requireContext())
        auth = Firebase.auth
        firestore = FirebaseFirestore.getInstance()
        setupCallbacks()
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber("+91 956-958-8740")
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(requireActivity())
            .setCallbacks(callbacks)
            .build()
        binding.signIn.setOnClickListener {
            binding.progressSignIn.visibility = VISIBLE
            PhoneAuthProvider.verifyPhoneNumber(options)
        }
        binding.signUp.setOnClickListener {
            if (binding.inputName.text.isNullOrBlank()) {
                firestore.collection("").document(auth.uid!!)
                    .set(mapOf("name" to binding.inputName.text.toString())).addOnSuccessListener {
                        navigateToApp()
                    }
            }

        }
        return binding.root
    }

    private fun setupCallbacks() {
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                binding.progressSignIn.visibility = GONE
                Log.d(TAG, "onVerificationCompleted:$credential")
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Log.w(TAG, "onVerificationFailed", e)
                binding.progressSignIn.visibility = GONE
                if (e is FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(requireContext(), "Invalid credentials", Toast.LENGTH_SHORT)
                        .show()
                } else if (e is FirebaseTooManyRequestsException) {
                    Toast.makeText(
                        requireContext(),
                        "Too many requests! Try again later.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                Log.d(TAG, "onCodeSent:$verificationId")
//                val storedVerificationId = verificationId
//                val resendToken = token
            }
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")
                    lifecycleScope.launch {
                        val userDocument =
                            firestore.collection("users_collection").document(auth.uid!!).get()
                                .await()
                        if (userDocument != null) {
                            navigateToApp()
                        } else {
                            binding.inputName.visibility = VISIBLE
                            binding.signIn.visibility = GONE
                            binding.signUp.visibility = VISIBLE
                        }
                    }

                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(requireContext(), "Invalid credentials", Toast.LENGTH_SHORT)
                            .show()
                        Log.w(TAG, "signInWithPhoneAuthCredential: ")
                    }
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigateToApp()  {
        Toast.makeText(requireContext(),"Signed In",Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val TAG = "AUTH_FRAGMENT"
    }
}