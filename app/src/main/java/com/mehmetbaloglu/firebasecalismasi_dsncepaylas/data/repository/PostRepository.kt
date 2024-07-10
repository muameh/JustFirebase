package com.mehmetbaloglu.firebasecalismasi_dsncepaylas.data.repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import com.mehmetbaloglu.firebasecalismasi_dsncepaylas.data.model.Post

class PostRepository {

    private var auth: FirebaseAuth
    private var db: FirebaseFirestore

    var postList = MutableLiveData<List<Post>>()


    var sharePostMessage: MutableLiveData<String?>
    var deleteMessage: MutableLiveData<String?>
    var getPostsMessage: MutableLiveData<String>

    init {
        auth = Firebase.auth
        db = Firebase.firestore

        sharePostMessage = MutableLiveData()
        deleteMessage = MutableLiveData()
        getPostsMessage = MutableLiveData()
    }

    fun deletePost(postId: String) {
        db.collection("Posts").document(postId).delete().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                deleteMessage.postValue("Post deleted successfully")
            }
        }.addOnFailureListener { error ->
            deleteMessage.postValue(error.localizedMessage)
        }

    }

    fun sharePost(post: Post) {
        db.collection("Posts").document().set(post).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                sharePostMessage.postValue("Post shared successfully")
            }
        }.addOnFailureListener { error ->
            sharePostMessage.postValue(error.localizedMessage)
        }
    }

    fun getPosts(): MutableLiveData<List<Post>> {
        db.collection("Posts").orderBy("postDate", Query.Direction.DESCENDING)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    getPostsMessage.value = error.localizedMessage
                } else {
                    if (value != null) {
                        if (!value.isEmpty) {
                            var _postList = ArrayList<Post>()
                            val documents = value.documents

                            for (document in documents) {
                                if (!value.isEmpty) {
                                    val _post = document.toObject(Post::class.java)
                                    _post?.postId = document.id
                                    if (_post != null) {
                                        _postList.add(_post)
                                    }
                                }
                            }
                            postList.value = _postList
                        }
                    }
                }
            }
        return postList
    }


}