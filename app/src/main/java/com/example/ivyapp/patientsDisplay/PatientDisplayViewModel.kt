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

    private val _patientClicked = MutableLiveData<Boolean>()
    val patientClicked: LiveData<Boolean>
        get() = _patientClicked

    private val _navigateToPatientDetail = MutableLiveData<Int>()
    val navigateToPatientDetail
        get() = _navigateToPatientDetail

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
        _patientClicked.value = false
    }


    fun saveOrUpdate() {
//        Log.i("MYTAG", "${saveOrUpdateButtonText.value}")
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



               viewModelScope.launch {
                   val newPatient = repository.searchPatientByIVPumpUnitNum(patientToUpdateOrDelete.infusionPumpUnitNum!!)
                   if ((newPatient != null) && (newPatient.patientId != patientToUpdateOrDelete.patientId)) {
                       _statusMessage.value = PatientDisplayEvent("IV Pump Unit ${patientToUpdateOrDelete.infusionPumpUnitNum} already being used. Choose another IV Pump Unit.")
                   }else {
                       update(patientToUpdateOrDelete)
                       _patientClicked.value = false
                   }
               }
           }else {
//               Log.i("MYTAG", "${inputPatientFirstname.value} ${inputPatientLastName.value}")
               val patientFirstName: String = inputPatientFirstname.value!!
               val patientLastName: String = inputPatientLastName.value!!
               val infusionPumpUnitNum: Int = inputIvPumpUnitNum.value!!.toInt()
               val flowRate: Double = inputFlowRate.value!!.toDouble()
               Log.i("MYTAG", "${inputPatientFirstname.value} ${inputPatientLastName.value}")

               viewModelScope.launch {
                   val newPatient = repository.searchPatientByIVPumpUnitNum(infusionPumpUnitNum!!)
                   if (newPatient != null) {
                       _statusMessage.value = PatientDisplayEvent("IV Pump Unit $infusionPumpUnitNum already being used. Choose another IV Pump Unit.")
                       _patientClicked.value = true
                   }else {
                       insert(Patient(0, patientFirstName, patientLastName, infusionPumpUnitNum, flowRate))
                       _patientClicked.value = false
                   }
               }

               inputPatientFirstname.value = null
               inputPatientLastName.value = null
               inputIvPumpUnitNum.value = null
               inputFlowRate.value = null
               _patientClicked.value = false
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
        _patientClicked.value = false
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

    fun onPatientClicked() {
        _patientClicked.value = true
    }

    fun onBackToPatientCard() {
        _patientClicked.value = false
    }

    fun addPatientCardClicked() {
        _patientClicked.value = true
        isUpdateOrDelete = false
        inputPatientFirstname.value = null
        inputPatientLastName.value = null
        inputIvPumpUnitNum.value = null
        inputFlowRate.value = null
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }

//    fun onPatientClicked(patientId: Int) {
//        _navigateToPatientDetail.value = patientId
//    }

//    fun onPatientNavigated() {
//        _navigateToPatientDetail.value = null
//    }


    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

}