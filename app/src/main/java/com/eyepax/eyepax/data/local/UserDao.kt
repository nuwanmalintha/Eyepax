package com.eyepax.eyepax.data.local

import androidx.room.*
import com.eyepax.eyepax.data.entities.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getUser(): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(user: User)
}