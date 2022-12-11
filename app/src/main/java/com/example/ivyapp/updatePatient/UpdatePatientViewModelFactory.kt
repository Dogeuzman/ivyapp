package com.example.ivyapp.updatePatient

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ivyapp.patientDatabase.Patient
import com.example.ivyapp.patientDatabase.PatientRepository
import com.example.ivyapp.patientsDisplay.PatientDisplayViewModel

class UpdatePatientViewModelFactory(private val patientId: Int , private val repository: PatientRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UpdatePatientViewModel::class.java)) {
            return UpdatePatientViewModel(patientId, repository) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }

}