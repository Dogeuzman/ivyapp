package com.example.ivyapp

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.ivyapp.notificationDisplay.NotificationApiStatus

@BindingAdapter("notificationApiStatus")
fun bindStatus(statusImageView: ImageView, status: NotificationApiStatus?) {
    when (status) {
        NotificationApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        NotificationApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        else -> {
            statusImageView.visibility = View.GONE
        }
    }
}