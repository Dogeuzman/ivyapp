package com.example.ivyapp.updatePatient

import androidx.annotation.MainThread
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ivyapp.patientDatabase.Patient
import com.example.ivyapp.patientDatabase.PatientRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class UpdatePatientViewModel(private val patientId: Int, private val repository: PatientRepository): ViewModel(), Observable {

//    private val _patient = MutableLiveData<Patient>()
//    val patient: LiveData<Patient>
//        get() = repository.search(patientId)

    private var patient: LiveData<Patient>

    fun getPatient() = patient


    init {
        patient = repository.search(patientId)
    }



    private val _navigateToHome = MutableLiveData<Boolean?>()
    val navigateToHome: LiveData<Boolean?>
        get() = _navigateToHome

    fun doneNavigating() {
        _navigateToHome.value = null
    }

    fun onClose() {
        _navigateToHome.value = true
    }


    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }


}