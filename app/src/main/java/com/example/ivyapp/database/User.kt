package com.example.ivyapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Users_table")
data class User (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    var userId: Int,


    @ColumnInfo(name = "user_name")
    var name: String,


    @ColumnInfo(name = "user_email")
    var email: String,


    @ColumnInfo(name = "user_password")
    val password: String

        )