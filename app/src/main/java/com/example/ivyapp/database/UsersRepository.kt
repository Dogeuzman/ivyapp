package com.example.ivyapp.database


class UsersRepository(private val userDao: UserDao) {

    val users = userDao.getAllUsers()

    suspend fun insert(user: User): Long {
        return userDao.insertUser(user)
    }

    suspend fun update(user: User): Int {
        return userDao.updateUser(user)
    }

    suspend fun delete(user: User): Int {
        return userDao.deleteUser(user)
    }

    suspend fun deleteAll(): Int {
        return userDao.deleteAll()
    }

    suspend fun search(userName: String): User? {
        return userDao.userSearch(userName)
    }


}