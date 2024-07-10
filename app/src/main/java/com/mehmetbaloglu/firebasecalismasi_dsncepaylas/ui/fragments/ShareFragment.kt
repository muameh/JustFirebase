package com.mehmetbaloglu.firebasecalismasi_dsncepaylas.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.mehmetbaloglu.firebasecalismasi_dsncepaylas.data.model.Post
import com.mehmetbaloglu.firebasecalismasi_dsncepaylas.databinding.FragmentShareBinding
import com.mehmetbaloglu.firebasecalismasi_dsncepaylas.ui.viewmodel.PostsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShareFragment : Fragment() {
    private var _binding: FragmentShareBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var storage: FirebaseStorage

    private lateinit var postViewModel: PostsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: PostsViewModel by viewModels()
        postViewModel = tempViewModel

        auth = Firebase.auth
        db = Firebase.firestore
        storage = Firebase.storage


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

        binding.buttonShare.setOnClickListener {

            createTPost(binding.textUserMessage.text.toString())
            observeViewModel()
            goToFeedFragment()

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //------------------------------------------------------------------------//

    private fun observeViewModel() {
        postViewModel.sharePostMessage.observe(viewLifecycleOwner) { message ->
            message?.let {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                postViewModel.sharePostMessage.postValue(null)
            }
        }
    }

    fun createTPost(text: String) {
        var postId: String? = ""
        var userMail: String? = auth.currentUser?.email
        var userMessage: String? = text
        var postUrl: String? = "_imageUrl"
        var postDate: Timestamp? = Timestamp.now()

        val post = Post(postId, userMail, userMessage, postUrl, postDate)
        postViewModel.sharePost(post)

    }

    fun goToFeedFragment() {
        val action = ShareFragmentDirections.actionShareFragmentToFeedFragment()
        view?.let { Navigation.findNavController(it).navigate(action) }

    }


}