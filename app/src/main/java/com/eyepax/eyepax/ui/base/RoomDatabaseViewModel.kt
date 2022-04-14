package com.eyepax.eyepaxtest.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eyepax.eyepaxtest.data.entities.User
import com.eyepax.eyepaxtest.data.repository.RoomDatabaseRepository

class RoomDatabaseViewModel(
    private val repository: RoomDatabaseRepository
) : ViewModel() {

    private val user = MutableLiveData<User>()

    fun loadUser() {
        user.postValue(repository.getUser())
    }

    fun getUser(): MutableLiveData<User> {
        return user
    }

    suspend fun saveUser(user: User) {
        repository.insertUser(user)
    }

    suspend fun updateUser(user: User) {
        repository.updateUser(user)
    }
}
