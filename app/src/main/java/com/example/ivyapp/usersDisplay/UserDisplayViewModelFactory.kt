package com.example.ivyapp.usersDisplay

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ivyapp.database.UsersRepository

class UserDisplayViewModelFactory(private val repository: UsersRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserDisplayViewModel::class.java)) {
            return UserDisplayViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }

}