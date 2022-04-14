package com.eyepax.eyepaxtest.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
data class User(
    @PrimaryKey
    val id: Int,
    val username: String?,
    val password: String?,
    var isLogged: Boolean
) {
    constructor(id: Int, isLogged: Boolean) : this(
        id = id,
        isLogged = isLogged,
        username = null,
        password = null
    )
}