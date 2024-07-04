package com.mehmetbaloglu.firebasecalismasi_dsncepaylas.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mehmetbaloglu.firebasecalismasi_dsncepaylas.data.model.Post
import com.mehmetbaloglu.firebasecalismasi_dsncepaylas.databinding.CardForPostsBinding

class PostAdapter(val postList : ArrayList<Post>) : RecyclerView.Adapter<PostAdapter.PostHolder>() {

    inner class PostHolder(val binding: CardForPostsBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val binding = CardForPostsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PostHolder(binding)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        holder.binding.textViewUserEmail.text = postList[position].userMail
        holder.binding.textViewPostMessage.text = postList[position].postMessage
        holder.binding.textViewPostDate.text = postList[position].postDate.toString()
        //Picasso.get().load(postList[position].downloadUrl).into(holder.binding.imageView)
    }
}