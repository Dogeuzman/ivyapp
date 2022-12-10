package com.example.ivyapp.patientsDisplay

import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ivyapp.patientDatabase.Patient
import com.example.ivyapp.patientDatabase.PatientRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PatientDisplayViewModel(private val repository: PatientRepository): ViewModel(), Observable {

    val patients = repository.patients

    private var isUpdateOrDelete = false
    private lateinit var patientToUpdateOrDelete: Patient

    private val _navigateToLogin = MutableLiveData<Boolean>()
    val navigateToLogin: LiveData<Boolean>
        get() = _navigateToLogin

    private val _statusMessage = MutableLiveData<PatientDisplayEvent<String>>()
    val statusMessage: LiveData<PatientDisplayEvent<String>>
        get() = _statusMessage


    @Bindable
    val inputPatientFirstname = MutableLiveData<String>()

    @Bindable
    val inputPatientLastName = MutableLiveData<String>()

    @Bindable
    val inputIvPumpUnitNum = MutableLiveData<String>()

    @Bindable
    val inputFlowRate = MutableLiveData<String>()

    val saveOrUpdateButtonText = MutableLiveData<String>()
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear"
    }

    fun saveOrUpdate() {
       // Log.i("MYTAG", "${inputPatientFirstname.value}, ${inputPatientLastName.value}, ${inputIvPumpUnitNum.value}, ${inputFlowRate.value}")
       if ((inputPatientFirstname.value == null) || (inputPatientLastName.value == null) || (inputIvPumpUnitNum.value == null) || (inputFlowRate.value == null)) {
           _statusMessage.value = PatientDisplayEvent("Please complete all the fields before submitting")
       }else {
           if (isUpdateOrDelete) {
//               Log.i("MYTAG", "${inputPatientFirstname.value} ${inputPatientLastName.value}")
               patientToUpdateOrDelete.patientFirstName = inputPatientFirstname.value!!
               patientToUpdateOrDelete.patientLastName = inputPatientLastName.value!!
               patientToUpdateOrDelete.infusionPumpUnitNum = inputIvPumpUnitNum.value!!.toInt()
               patientToUpdateOrDelete.flowRate = inputFlowRate.value!!.toDouble()
               update(patientToUpdateOrDelete)
           }else {
//               Log.i("MYTAG", "${inputPatientFirstname.value} ${inputPatientLastName.value}")
               val patientFirstName: String = inputPatientFirstname.value!!
               val patientLastName: String = inputPatientLastName.value!!
               val infusionPumpUnitNum: Int = inputIvPumpUnitNum.value!!.toInt()
               val flowRate: Double = inputFlowRate.value!!.toDouble()
               Log.i("MYTAG", "${inputPatientFirstname.value} ${inputPatientLastName.value}")
               insert(Patient(0, patientFirstName, patientLastName, infusionPumpUnitNum, flowRate))
               inputPatientFirstname.value = null
               inputPatientLastName.value = null
               inputIvPumpUnitNum.value = null
               inputFlowRate.value = null
           }
       }
    }

    fun clearAllOrDelete() {
        if (isUpdateOrDelete) {
            delete(patientToUpdateOrDelete)
        }else {
            clearAll()
        }
    }

    fun initUpdateAndDelete(patient: Patient) {
        inputPatientFirstname.value = patient.patientFirstName
        inputPatientLastName.value = patient.patientLastName
        inputIvPumpUnitNum.value = patient.infusionPumpUnitNum.toString()
        inputFlowRate.value = patient.flowRate.toString()
        isUpdateOrDelete = true
        patientToUpdateOrDelete = patient
        saveOrUpdateButtonText.value = "Update"
        clearAllOrDeleteButtonText.value = "Delete"
    }

    fun insert(patient: Patient): Job = viewModelScope.launch {
        repository.insert(patient)
        _statusMessage.value = PatientDisplayEvent("Patient record successfully added")
    }

    fun update(patient: Patient): Job = viewModelScope.launch {
        repository.update(patient)
        inputPatientFirstname.value = null
        inputPatientLastName.value = null
        inputIvPumpUnitNum.value = null
        inputFlowRate.value = null
        isUpdateOrDelete = false
        patientToUpdateOrDelete = patient
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
        _statusMessage.value = PatientDisplayEvent("Patient record successfully updated")
    }

    fun delete(patient: Patient): Job = viewModelScope.launch {
        repository.delete(patient)
        inputPatientFirstname.value = null
        inputPatientLastName.value = null
        inputIvPumpUnitNum.value = null
        inputFlowRate.value = null
        isUpdateOrDelete = false
        patientToUpdateOrDelete = patient
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
        _statusMessage.value = PatientDisplayEvent("Patient record successfully deleted")
    }

    fun clearAll(): Job = viewModelScope.launch {
        repository.deleteAll()
        _statusMessage.value = PatientDisplayEvent("All patient records successfully deleted")
    }

    fun goToLogin() {
        _navigateToLogin.value = true
    }

    fun doneNavigatingToLogin() {
        _navigateToLogin.value = false
        _statusMessage.value = PatientDisplayEvent("Successfully logged out")
    }


    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

}