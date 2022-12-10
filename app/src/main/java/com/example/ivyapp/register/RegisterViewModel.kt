package com.example.ivyapp.register

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ivyapp.database.User
import com.example.ivyapp.database.UsersRepository
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: UsersRepository): ViewModel(), Observable {

    val users = repository.users

    @Bindable
    val inputName = MutableLiveData<String>()

    @Bindable
    val inputEmail = MutableLiveData<String>()

    @Bindable
    val inputPassword = MutableLiveData<String>()


    private val _statusMessage = MutableLiveData<RegisterEvent<String>>()

    val statusMessage: LiveData<RegisterEvent<String>>
        get() = _statusMessage


    private val _navigateToLogin = MutableLiveData<Boolean>()

    val navigateToLogin: LiveData<Boolean>
        get() = _navigateToLogin


    fun onRegisterButtonClicked() {
        // Log.i("MYTAG", "${inputName.value} and ${inputEmail.value} and ${inputPassword.value}")

        if ((inputName.value == null) || (inputEmail.value == null) || (inputPassword.value == null)) {
            _statusMessage.value = RegisterEvent("Please complete all the fields")
        }else {
            viewModelScope.launch {
                val searchResult = repository.search(inputName.value!!)
                if (searchResult != null) {
                    _statusMessage.value = RegisterEvent("Username already used, choose another one.")
                }else {
                    val name = inputName.value!!
                    val email = inputEmail.value!!
                    val password = inputPassword.value!!
                    repository.insert(User(0, name, email, password))
                    inputName.value = null
                    inputEmail.value = null
                    inputPassword.value = null
                    _navigateToLogin.value = true
                    _statusMessage.value = RegisterEvent("Account successfully created, try logging in!")
                }
            }
        }
    }

    fun goToLogin() {
        _navigateToLogin.value = true
    }

    fun doneNavigatingToLogin() {
        _navigateToLogin.value = false
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

}