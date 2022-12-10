package com.example.ivyapp.notificationDisplay

import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ivyapp.network.Notification
import com.example.ivyapp.network.NotificationApi
import kotlinx.coroutines.launch
import java.lang.Exception


enum class NotificationApiStatus { LOADING, ERROR, DONE }

class NotificationDisplayViewModel: ViewModel(), Observable {


    // private val _response = MutableLiveData<String>()

//    val response: LiveData<String>
//        get() = _response

    private val _notifications = MutableLiveData<List<Notification>>()

    val notifications: LiveData<List<Notification>>
        get() = _notifications

    private val _status = MutableLiveData<NotificationApiStatus>()

    val status: LiveData<NotificationApiStatus>
        get() = _status

    init {
        getNotifications()
    }

    private fun getNotifications() {
        viewModelScope.launch {
            _status.value = NotificationApiStatus.LOADING
            try {
                val notifListResult = NotificationApi.notificationRetrofitService.getNotifications()
                _status.value = NotificationApiStatus.DONE
                _notifications.value = notifListResult
            }catch (e: Exception) {
                _status.value = NotificationApiStatus.ERROR
                _notifications.value = ArrayList()
            }
        }
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}