package com.mehmetbaloglu.firebasecalismasi_dsncepaylas.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mehmetbaloglu.firebasecalismasi_dsncepaylas.data.model.Post
import com.mehmetbaloglu.firebasecalismasi_dsncepaylas.databinding.FragmentFeedBinding
import com.mehmetbaloglu.firebasecalismasi_dsncepaylas.ui.adapters.PostAdapter
import java.text.SimpleDateFormat
import java.util.Locale

class FeedFragment : Fragment() {
    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    //private lateinit var storage: FirebaseStorage

    private lateinit var postAdapter: PostAdapter

    var postList = ArrayList<Post>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
        db = Firebase.firestore
        //storage = Firebase.storage
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

        getPosts()

        binding.floatingActionButton.setOnClickListener { goToShareFragment(it) }

        setPostAdapter(postList)

    }

    fun setPostAdapter(postList: ArrayList<Post>) {
        postAdapter = PostAdapter(postList)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = postAdapter
    }

    fun formatTimestampToString(timestamp: Timestamp): String {
        val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault())
        val date = timestamp.toDate()
        return sdf.format(date)
    }


    fun goToShareFragment(view: View) {
        val action = FeedFragmentDirections.actionFeedFragmentToShareFragment()
        view.let { Navigation.findNavController(it).navigate(action) }
    }

    fun getPosts() {
        db.collection("Posts").addSnapshotListener { value, error ->
            if (error != null) {
                Toast.makeText(requireContext(), error.localizedMessage, Toast.LENGTH_SHORT).show()
            } else {
                if (value != null) {
                    if (!value.isEmpty) {


                        val documents = value.documents

                        postList.clear()

                        for (document in documents) {
                            val userMail = document.get("userMail") as String?
                            val postMessage = document.get("userMessage") as String?
                            val _postDate = document.get("postDate") as Timestamp?
                            val postUrl = document.get("postUrl") as String?

                           var postDate = formatTimestampToString(_postDate as Timestamp)

                            var post = Post(userMail, postMessage, postUrl, postDate)

                            Log.e( "value",post.toString())

                            postList.add(post)

                        }
                        postAdapter?.notifyDataSetChanged()
                    }
                }
            }

        }
    }
}