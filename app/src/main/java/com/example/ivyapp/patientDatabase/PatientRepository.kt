package com.example.ivyapp.patientDatabase

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

    suspend fun search(patientId: Int): Patient? {
        return patientDao.patientSearch(patientId)
    }

}