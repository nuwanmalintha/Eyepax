package com.eyepax.eyepaxtest.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.eyepax.eyepaxtest.data.entities.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getUser(): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(user: User)
}