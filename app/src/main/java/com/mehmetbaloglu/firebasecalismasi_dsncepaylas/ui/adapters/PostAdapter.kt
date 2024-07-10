package com.mehmetbaloglu.firebasecalismasi_dsncepaylas.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mehmetbaloglu.firebasecalismasi_dsncepaylas.data.model.Post
import com.mehmetbaloglu.firebasecalismasi_dsncepaylas.databinding.CardForPostsBinding
import com.mehmetbaloglu.firebasecalismasi_dsncepaylas.ui.viewmodel.PostsViewModel
import com.mehmetbaloglu.firebasecalismasi_dsncepaylas.utils.CommonFunctions

class PostAdapter(var postsViewModel: PostsViewModel) :
    RecyclerView.Adapter<PostAdapter.PostHolder>() {

    inner class PostHolder(val binding: CardForPostsBinding) : RecyclerView.ViewHolder(binding.root)


    private val differCallback = object : DiffUtil.ItemCallback<Post>() {

        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.postDate == newItem.postDate
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val binding =
            CardForPostsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        val post = differ.currentList[position]
        post.postDate?.let {
            val formattedDate = CommonFunctions.formatTimestampToString(it)
            holder.binding.textViewPostDate.text = formattedDate
        }
        holder.binding.textViewUserEmail.text = post.userMail
        holder.binding.textViewPostMessage.text = post.userMessage

        holder.binding.imageViewDelete.setOnClickListener {
            postsViewModel.deletePost(post.postId!!)
        }


    }

}