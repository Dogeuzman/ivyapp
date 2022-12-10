package com.example.ivyapp.notificationDisplay

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ivyapp.usersDisplay.UserDisplayViewModel

class NotificationDisplayViewModelFactory: ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotificationDisplayViewModel::class.java)) {
            return NotificationDisplayViewModel() as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }

}