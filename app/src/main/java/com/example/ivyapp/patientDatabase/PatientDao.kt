package com.example.ivyapp.patientDatabase

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PatientDao {

    @Insert
    suspend fun insertPatient(patient: Patient)

    @Update
    suspend fun updatePatient(patient: Patient)

    @Delete
    suspend fun deletePatient(patient: Patient)

    @Query("DELETE FROM Patients_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM Patients_table")
    fun getAllPatients(): LiveData<List<Patient>>

    @Query("SELECT * FROM Patients_table WHERE patient_id = :patientId")
    suspend fun patientSearch(patientId: Int): Patient?

}