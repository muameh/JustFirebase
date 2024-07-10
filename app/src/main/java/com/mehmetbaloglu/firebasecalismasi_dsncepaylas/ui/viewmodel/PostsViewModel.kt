package com.mehmetbaloglu.firebasecalismasi_dsncepaylas.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mehmetbaloglu.firebasecalismasi_dsncepaylas.data.model.Post
import com.mehmetbaloglu.firebasecalismasi_dsncepaylas.data.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(val postRepository: PostRepository) : ViewModel() {

    var postList = MutableLiveData<List<Post>>()

    val sharePostMessage = postRepository.sharePostMessage
    val deleteMessage = postRepository.deleteMessage
    val postsMessage = postRepository.getPostsMessage


    fun getPosts() {
        postList = postRepository.getPosts()
    }

    fun sharePost(post: Post) {
        postRepository.sharePost(post)
    }

    fun deletePost(postId: String) {
        postRepository.deletePost(postId)
    }


}