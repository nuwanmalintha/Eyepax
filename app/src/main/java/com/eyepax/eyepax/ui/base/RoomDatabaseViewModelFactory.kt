package com.eyepax.eyepax.ui.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eyepax.eyepax.data.repository.RoomDatabaseRepository
import java.lang.Exception

class RoomDatabaseViewModelFactory(

    private val repository: RoomDatabaseRepository

) : ViewModelProvider.NewInstanceFactory() {
    private val TAG = "RoomDatabaseViewModelFa"
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        try {

            val constructor = modelClass.getDeclaredConstructor(RoomDatabaseRepository::class.java)
            return constructor.newInstance(repository)

        } catch (e: Exception) {

            Log.e(TAG, e.message.toString())

        }

        return super.create(modelClass)
    }
}