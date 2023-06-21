package com.sdk.roomcomposemvvm.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Query("select * from user order by id desc")
    fun getAllUsers(): LiveData<List<User>>

    @Query("select * from user where id = :id")
    fun getUserById(id: Int): LiveData<User?>
}