package com.sdk.roomcomposemvvm.repository

import com.sdk.roomcomposemvvm.database.User
import com.sdk.roomcomposemvvm.database.UserDao

class NoteRepository(
    private val dao: UserDao
) {
    suspend fun saveUser(user: User) = dao.saveUser(user)
    suspend fun deleteUser(user: User) = dao.deleteUser(user)
    suspend fun updateUser(user: User) = dao.updateUser(user)
    fun getAllUsers() = dao.getAllUsers()
    fun getUserById(id: Int) = dao.getUserById(id)
}