package com.example.ivyapp.patientsDisplay

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ivyapp.patientDatabase.PatientRepository
import com.example.ivyapp.usersDisplay.UserDisplayViewModel

class PatientDisplayViewModelFactory(private val repository: PatientRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PatientDisplayViewModel::class.java)) {
            return PatientDisplayViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }

}