package com.example.ivyapp.notificationDisplay

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ivyapp.databinding.NotificationItemBinding
import com.example.ivyapp.network.Notification

class NotificationDisplayListAdapter: ListAdapter<Notification, NotificationDisplayListAdapter.NotificationViewHolder>(NotifCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        return NotificationViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val notification = getItem(position)
        holder.bind(notification)
    }


    class NotificationViewHolder(private var binding: NotificationItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(notification: Notification) {
            binding.notification = notification
            if (notification.problem == 1) {
                binding.notificationMessage.text = "Alert: Problem occurred in IV Pump Unit No.${notification.unitId}!"
                binding.executePendingBindings()
            }else {
                binding.notificationMessage.visibility = GONE
                binding.executePendingBindings()
            }
        }

        companion object {
            fun from(parent: ViewGroup): NotificationViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = NotificationItemBinding.inflate(layoutInflater, parent, false)
                return NotificationViewHolder(binding)
            }
        }
    }

}


class NotifCallback: DiffUtil.ItemCallback<Notification>() {

    override fun areItemsTheSame(oldItem: Notification, newItem: Notification): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Notification, newItem: Notification): Boolean {
        return oldItem.notifId == newItem.notifId
    }

}

