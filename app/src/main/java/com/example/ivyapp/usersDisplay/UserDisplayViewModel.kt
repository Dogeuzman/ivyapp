package com.example.ivyapp.usersDisplay

import android.util.Patterns
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ivyapp.database.User
import com.example.ivyapp.database.UsersRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class UserDisplayViewModel(private val repository: UsersRepository): ViewModel(), Observable {

    val users = repository.users

    private var isUpdateOrDelete = false
    private lateinit var userToUpdateOrDelete: User

//    private val _navigateToLogin = MutableLiveData<Boolean>()
//
//    val navigateToLogin: LiveData<Boolean>
//        get() = _navigateToLogin

    @Bindable
    val inputName = MutableLiveData<String>()

    @Bindable
    val inputEmail = MutableLiveData<String>()

    val saveOrUpdateButtonText = MutableLiveData<String>()

    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    private val _statusMessage = MutableLiveData<UserDisplayEvent<String>>()

    val statusMessage: LiveData<UserDisplayEvent<String>>
        get() = _statusMessage


    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear"
    }

    fun saveOrUpdate() {
        if (inputName.value == null) {
            _statusMessage.value = UserDisplayEvent("Please enter user's name!")
        }else if (inputEmail.value == null) {
            _statusMessage.value = UserDisplayEvent("Please enter user's email!")
        }else if (!Patterns.EMAIL_ADDRESS.matcher(inputEmail.value!!).matches()) {
            _statusMessage.value = UserDisplayEvent("Please enter a valid email address!")
        }else {
            if (isUpdateOrDelete) {
                userToUpdateOrDelete.name = inputName.value!!
                userToUpdateOrDelete.email = inputEmail.value!!
                update(userToUpdateOrDelete)
            }else {
                val name: String = inputName.value!!
                val email: String = inputEmail.value!!
                insert(User(0, name, email, "defaultPassword"))
                inputName.value = null
                inputEmail.value = null
            }
        }
    }

    fun clearAllOrDelete() {
        if (isUpdateOrDelete) {
            delete(userToUpdateOrDelete)
        }else {
            clearAll()
        }
    }

    fun initUpdateAndDelete(user: User) {
        inputName.value = user.name
        inputEmail.value = user.email
        isUpdateOrDelete = true
        userToUpdateOrDelete = user
        saveOrUpdateButtonText.value = "Update"
        clearAllOrDeleteButtonText.value = "Delete"
    }

    fun insert(user: User): Job = viewModelScope.launch {
            val newRowId = repository.insert(user)
            if (newRowId > -1) {
                _statusMessage.value = UserDisplayEvent("User $newRowId added successfully!")
            }else {
                _statusMessage.value = UserDisplayEvent("Error occurred")
            }
        }

    fun update(user: User): Job = viewModelScope.launch {
        val numOfRows = repository.update(user)
        if (numOfRows > 0) {
            inputName.value = null
            inputEmail.value = null
            isUpdateOrDelete = false
            userToUpdateOrDelete = user
            saveOrUpdateButtonText.value = "Save"
            clearAllOrDeleteButtonText.value = "Clear All"
            _statusMessage.value = UserDisplayEvent("$numOfRows Row updated successfully!")
        }else {
            _statusMessage.value = UserDisplayEvent("Error occurred")
        }
    }

    fun delete(user: User): Job = viewModelScope.launch {
        val numOfRowsDeleted = repository.delete(user)
        if (numOfRowsDeleted > 0) {
            inputName.value = null
            inputEmail.value = null
            isUpdateOrDelete = false
            userToUpdateOrDelete = user
            saveOrUpdateButtonText.value = "Save"
            clearAllOrDeleteButtonText.value = "Clear All"
            _statusMessage.value = UserDisplayEvent("$numOfRowsDeleted Row deleted successfully!")
        }else {
            _statusMessage.value = UserDisplayEvent("Error occurred")
        }
    }

    fun clearAll(): Job = viewModelScope.launch {
        val numOfRowsDeleted = repository.deleteAll()
        if (numOfRowsDeleted > 0) {
            _statusMessage.value = UserDisplayEvent("All $numOfRowsDeleted users deleted successfully!")
        }else {
            _statusMessage.value = UserDisplayEvent("Error occurred")
        }

    }

//    fun doneNavigatingToLogin() {
//        _navigateToLogin.value = false
//    }
//
//    fun onClickLogoutButton() {
//        _navigateToLogin.value = true
//        _statusMessage.value = UserDisplayEvent("Logged out successfully")
//    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

}