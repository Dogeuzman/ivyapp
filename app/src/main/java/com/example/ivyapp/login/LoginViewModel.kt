package com.example.ivyapp.login

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.*
import com.example.ivyapp.database.UsersRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UsersRepository): ViewModel(), Observable {

    val users = repository.users

    @Bindable
    val inputUsername = MutableLiveData<String>()

    @Bindable
    val inputPassword = MutableLiveData<String>()


//    private val _loginButtonClicked = MutableLiveData<Boolean>()
//
//    val loginButtonClicked: LiveData<Boolean>
//        get() = _loginButtonClicked

    private val _statusMessage = MutableLiveData<LoginEvent<String>>()

    val statusMessage: LiveData<LoginEvent<String>>
        get() = _statusMessage

    private val _navigateToUserDisplay = MutableLiveData<Boolean>()

    val navigateToUserDisplay: LiveData<Boolean>
        get() = _navigateToUserDisplay

    private val _navigateToRegister = MutableLiveData<Boolean>()

    val navigateToRegister: LiveData<Boolean>
        get() = _navigateToRegister

    fun onLoginButtonClicked() {
        // Log.i("MYTAG", "${inputUsername.value} and ${inputPassword.value}")

        if ((inputUsername.value == null) || (inputPassword.value == null)) {
            _statusMessage.value = LoginEvent("Please complete all the fields")
        }else {
            viewModelScope.launch {
                val searchResult = repository.search(inputUsername.value!!)
                if (searchResult != null) {
                    if (searchResult.password == inputPassword.value) {
                        inputUsername.value = null
                        inputPassword.value = null
                        _navigateToUserDisplay.value = true
                        _statusMessage.value = LoginEvent("Successfully logged in!")
                    }else {
                        _statusMessage.value = LoginEvent("Please check your username and password!")
                    }
                }
            }
        }
    }

    fun doneNavigatingToUserDisplay() {
        _navigateToUserDisplay.value = false
    }

    fun goToRegister() {
        _navigateToRegister.value = true
    }

    fun doneNavigatingToRegister() {
        _navigateToRegister.value = false
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

}