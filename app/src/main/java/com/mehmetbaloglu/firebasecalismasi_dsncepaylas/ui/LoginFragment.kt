package com.mehmetbaloglu.firebasecalismasi_dsncepaylas.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mehmetbaloglu.firebasecalismasi_dsncepaylas.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize Firebase Auth
        auth = Firebase.auth
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null && currentUser.isEmailVerified) {
            val action = LoginFragmentDirections.actionLoginFragmentToFeedFragment()
            view?.let { Navigation.findNavController(it).navigate(action) }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSignIn.setOnClickListener { signIn(it) }
        binding.buttonSignUp.setOnClickListener { signUp(it) }
        binding.textViewResetPassword.setOnClickListener { resetPassword() }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //------------------------------------------------------------------------//

    private fun signIn(view: View) {
        val email = binding.editTextTextEmailAddress.text.toString()
        val password = binding.editTextTextPassword.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null && user.isEmailVerified) {
                        val action = LoginFragmentDirections.actionLoginFragmentToFeedFragment()
                        Navigation.findNavController(view).navigate(action)
                        Toast.makeText(
                            requireContext(), "Logged in successfully", Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            requireContext(), "Please verify your email address", Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        requireContext(), task.exception?.localizedMessage, Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else {
            Toast.makeText(requireContext(), "Please fill in all the fields", Toast.LENGTH_SHORT)
                .show()
        }
    }


    private fun signUp(view: View) {
        val email = binding.editTextTextEmailAddress.text.toString()
        val password = binding.editTextTextPassword.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user?.sendEmailVerification()?.addOnSuccessListener {
                        Toast.makeText(
                            requireContext(), "Please verify your email", Toast.LENGTH_SHORT
                        ).show()
                    }?.addOnFailureListener { exception ->
                        Toast.makeText(
                            requireContext(), exception.localizedMessage, Toast.LENGTH_SHORT
                        ).show()
                    }
                    //auth.signOut()
                    /*
                    Toast.makeText(
                        requireContext(),
                        "User created successfully. Please verify your email.",
                        Toast.LENGTH_SHORT
                    ).show()
                     */

                } else {
                    Toast.makeText(
                        requireContext(), task.exception?.localizedMessage, Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else {
            Toast.makeText(requireContext(), "Please fill in all the fields", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun resetPassword() {
        val email = binding.editTextTextEmailAddress.text.toString()

        if (email.isNotEmpty()) {
            auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        requireContext(), "Password reset email sent", Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        requireContext(), task.exception?.localizedMessage, Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else {
            Toast.makeText(requireContext(), "Please enter your email address", Toast.LENGTH_SHORT)
                .show()
        }
    }


}