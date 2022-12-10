package com.example.ivyapp.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(user: User): Long

    @Update
    suspend fun updateUser(user: User): Int

    @Delete
    suspend fun deleteUser(user: User): Int

    @Query("DELETE FROM Users_table")
    suspend fun deleteAll(): Int

    @Query("SELECT * FROM Users_table")
    fun getAllUsers(): LiveData<List<User>>

    @Query("SELECT * FROM Users_table WHERE user_name = :userName")
    suspend fun userSearch(userName: String): User?


//    @Query("SELECT * FROM Users_table ORDER BY user_id DESC")
//    fun getAllUsers(): LiveData<List<User>>
//
//    @Query("SELECT * FROM Users_table WHERE user_name = :userName")
//    suspend fun getUsername(userName: String): User?

//    @Delete
//    suspend fun deleteAll(user: User)

}