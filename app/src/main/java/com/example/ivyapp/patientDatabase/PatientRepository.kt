package com.example.ivyapp.patientDatabase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ivyapp.patientsDisplay.PatientDisplayEvent

class PatientRepository(private val patientDao: PatientDao) {

    val patients = patientDao.getAllPatients()

    suspend fun insert(patient: Patient) {
        return patientDao.insertPatient(patient)
    }

    suspend fun  update(patient: Patient) {
        return patientDao.updatePatient(patient)
    }

    suspend fun delete(patient: Patient) {
        return patientDao.deletePatient(patient)
    }

    suspend fun deleteAll() {
        return patientDao.deleteAll()
    }

    fun search(patientId: Int): LiveData<Patient> {
        return patientDao.patientSearch(patientId)
    }

//    fun searchReturnPatient(patientId: Int): Patient {
//        return patientDao.patientSearchReturnPatient(patientId)
//    }

    suspend fun searchPatientByIVPumpUnitNum(ivPumpNum: Int): Patient? {
        return patientDao.patientSearchByIVPumpUnitNum(ivPumpNum)
    }

}