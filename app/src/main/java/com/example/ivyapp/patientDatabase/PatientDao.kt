package com.example.ivyapp.patientDatabase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    fun patientSearch(patientId: Int): LiveData<Patient>


//    @Query("SELECT infusion_pump_unit_num FROM Patients_table WHERE patient_id =")
//    fun searchIVPumpUnit(patientId: Int): Int

    @Query("SELECT * FROM Patients_table WHERE infusion_pump_unit_num = :ivPumpNum")
    suspend fun patientSearchByIVPumpUnitNum(ivPumpNum: Int): Patient?

}