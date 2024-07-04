package com.mehmetbaloglu.firebasecalismasi_dsncepaylas.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mehmetbaloglu.firebasecalismasi_dsncepaylas.R
import com.mehmetbaloglu.firebasecalismasi_dsncepaylas.databinding.FragmentLoginBinding
import com.mehmetbaloglu.firebasecalismasi_dsncepaylas.databinding.FragmentShareBinding


class ShareFragment : Fragment() {
    private var _binding: FragmentShareBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth : FirebaseAuth
    private lateinit var db : FirebaseFirestore
    //private lateinit var storage: FirebaseStorage


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
        db = Firebase.firestore
        //storage = Firebase.storage
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShareBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //------------------------------------------------------------------------//

    fun shareText(){
        val userMail = auth.currentUser?.email.toString()
        val userMessage = binding.textUserMessage.text.toString()

        val postHashMap = hashMapOf<String,Any>()
        postHashMap["userMail"] = userMail
        postHashMap["userMessage"] = userMessage
        postHashMap["postDate"] = Timestamp.now()

        db.collection("Posts").add(postHashMap)

    }







}