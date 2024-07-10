package com.mehmetbaloglu.firebasecalismasi_dsncepaylas.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mehmetbaloglu.firebasecalismasi_dsncepaylas.databinding.FragmentFeedBinding
import com.mehmetbaloglu.firebasecalismasi_dsncepaylas.ui.adapters.PostAdapter
import com.mehmetbaloglu.firebasecalismasi_dsncepaylas.ui.viewmodel.PostsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedFragment : Fragment() {
    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!

    private lateinit var postViewModel: PostsViewModel

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    private lateinit var postAdapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: PostsViewModel by viewModels()
        postViewModel = tempViewModel

        auth = Firebase.auth
        db = Firebase.firestore
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.floatingActionButton.setOnClickListener { goToShareFragment(it) }

        binding.textView3.text = auth.currentUser?.email

        binding.button2.setOnClickListener { signOut() }

        postViewModel.getPosts()

        setPostAdapter()

        observeViewModel()

    }

    //------------------------------------------------------------------------//


    fun signOut() {
        auth.signOut()
        val action = FeedFragmentDirections.actionFeedFragmentToLoginFragment()
        view?.let { Navigation.findNavController(it).navigate(action) }
    }

    private fun observeViewModel() {
        postViewModel.deleteMessage.observe(viewLifecycleOwner) { message ->
            message?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                postViewModel.deleteMessage.postValue(null)
            }
        }
        postViewModel.postsMessage.observe(viewLifecycleOwner) { message ->
            message?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                postViewModel.deleteMessage.postValue(null)
            }
        }
    }


    fun setPostAdapter() {
        postAdapter = PostAdapter(postViewModel)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = postAdapter
        }
        postViewModel.postList.observe(viewLifecycleOwner) {
            postAdapter.differ.submitList(it)
            Log.e("postList", it.toString())
        }

    }

    fun goToShareFragment(view: View) {
        val action = FeedFragmentDirections.actionFeedFragmentToShareFragment()
        view.let { Navigation.findNavController(it).navigate(action) }
    }


}
