package com.dxn.auth.ui.fragments

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
import androidx.navigation.fragment.findNavController
import com.dxn.data.models.User
import com.dxn.auth.databinding.FragmentAuthBinding
import com.dxn.auth.utils.formatPhoneNumber
import com.dxn.auth.R
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import io.grpc.InternalChannelz.id
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit


class Auth : Fragment() {

    private lateinit var auth: FirebaseAuth
    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var firestore: FirebaseFirestore
    var phoneNumber : String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthBinding.inflate(inflater, container, false)
        FirebaseApp.initializeApp(requireContext())
        auth = Firebase.auth
        firestore = FirebaseFirestore.getInstance()
        setupCallbacks()
        val userRole = arguments?.getInt("user_role")!!
        val options = PhoneAuthOptions.newBuilder(auth)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(requireActivity())
            .setCallbacks(callbacks)

        binding.signIn.setOnClickListener {
            binding.progressSignIn.visibility = VISIBLE
            val rawNumber = binding.inputMobNumber.text.toString()
            phoneNumber= "+91$rawNumber"
            PhoneAuthProvider.verifyPhoneNumber(
                options.setPhoneNumber("+91${formatPhoneNumber(rawNumber)}")
                    .build()
            )
        }

        binding.signUp.setOnClickListener {
            if (!binding.inputName.text.isNullOrBlank()) {
                val user = User(
                    name = binding.inputName.text.toString(),
                    phoneNumber = phoneNumber,
                    role = userRole,
                    uid = auth.uid!!
                )
                firestore.collection("users_collection").document(phoneNumber)
                    .set(user).addOnSuccessListener { navigateToApp() }
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
                        val user =
                            firestore.collection("users_collection").document(phoneNumber).get()
                                .await().toObject(User::class.java)
                        Log.d(TAG, "signInWithPhoneAuthCredential: $user")
                        if (user != null) {
                            navigateToApp()
                        } else {
                            binding.inputNameBox.visibility = VISIBLE
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

    private fun navigateToApp() {
        findNavController().navigate(R.id.action_auth_to_settingUpUser)
    }

    companion object {
        const val TAG = "AUTH_FRAGMENT"
    }
}