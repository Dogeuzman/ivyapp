package com.example.ivyapp.patientDatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Patients_table")
data class Patient(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "patient_id")
    var patientId: Int,

    @ColumnInfo(name = "patient_first_name")
    var patientFirstName: String,

    @ColumnInfo(name = "patient_last_name")
    var patientLastName: String,

    @ColumnInfo(name = "infusion_pump_unit_num")
    var infusionPumpUnitNum: Int,

    @ColumnInfo(name = "flow_rate")
    var flowRate: Double

        )