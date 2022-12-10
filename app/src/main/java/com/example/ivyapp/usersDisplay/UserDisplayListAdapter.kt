package com.example.ivyapp.usersDisplay

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ivyapp.database.User
import com.example.ivyapp.databinding.ListItemBinding

class UserDisplayListAdapter(val clickListener: UserListener): ListAdapter<User, UserDisplayListAdapter.UserViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user!!, clickListener)
    }

    class UserViewHolder private constructor(val binding: ListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User, clickListener: UserListener) {
            binding.user = user
            binding.clickListener = clickListener
            binding.nameTextView.text = user.name
            binding.emailTextView.text = user.email
        }

        companion object {
            fun from(parent: ViewGroup): UserViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemBinding.inflate(layoutInflater, parent, false)
                return UserViewHolder(binding)
            }
        }
    }
}

class DiffCallback: DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.userId == newItem.userId
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }
}

class UserListener(val clickLister: (User) -> Unit) {
    fun onClick(user: User) = clickLister(user)
}