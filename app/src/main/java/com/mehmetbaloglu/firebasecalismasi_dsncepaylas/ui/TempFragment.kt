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
import com.mehmetbaloglu.firebasecalismasi_dsncepaylas.databinding.FragmentTempBinding


class TempFragment : Fragment() {


    private var _binding: FragmentTempBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize Firebase Auth
        auth = Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTempBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSignOut.setOnClickListener {
            auth.signOut()

            val action = TempFragmentDirections.actionTempFragmentToLoginFragment()
            Navigation.findNavController(view).navigate(action)

            Toast.makeText(requireContext(), "You have signed out succesfully", Toast.LENGTH_SHORT)
                .show()
        }

        binding.textViewUserEmail.text = auth.currentUser?.email

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}