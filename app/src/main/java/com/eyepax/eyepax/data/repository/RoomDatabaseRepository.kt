package com.eyepax.eyepax.data.repository

import com.eyepax.eyepax.data.entities.User
import com.eyepax.eyepax.data.local.AppDatabase

class RoomDatabaseRepository(
    private val roomDatabase: AppDatabase
) {
    suspend fun insertUser(user: User) = roomDatabase.userDao().insert(user)
    suspend fun updateUser(user: User) = roomDatabase.userDao().update(user)
    fun getUser() = roomDatabase.userDao().getUser()
}