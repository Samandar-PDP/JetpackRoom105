package com.sdk.roomcomposemvvm.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    val fullName: String,
    val age: Int,
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
)
