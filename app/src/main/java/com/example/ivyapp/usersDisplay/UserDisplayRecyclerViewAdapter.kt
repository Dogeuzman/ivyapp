package com.example.ivyapp.usersDisplay

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.ivyapp.R
import com.example.ivyapp.database.User
import com.example.ivyapp.databinding.ListItemBinding

class UserDisplayRecyclerViewAdapter(private val clickListener: (User) -> Unit): RecyclerView.Adapter<UserViewHolder>() {

    private val usersList = ArrayList<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListItemBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.list_item,
            parent,
            false
        )
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
         holder.bind(usersList[position], clickListener)
    }

    override fun getItemCount(): Int {
        return usersList.size
    }

    fun setList(users: List<User>) {
        usersList.clear()
        usersList.addAll(users)
    }
}

class UserViewHolder(val binding: ListItemBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(user: User, clickListener: (User) -> Unit) {
        binding.nameTextView.text = user.name
        binding.emailTextView.text = user.email
        binding.listItemLayout.setOnClickListener {
            clickListener(user)
        }
    }

}