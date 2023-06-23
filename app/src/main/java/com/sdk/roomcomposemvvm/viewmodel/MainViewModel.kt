package com.sdk.roomcomposemvvm.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sdk.roomcomposemvvm.database.User
import com.sdk.roomcomposemvvm.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: NoteRepository
) : ViewModel() {

    val noteList = repository.getAllUsers()
    val user = MutableLiveData<User?>(null)


    fun getUserById(id: Int): LiveData<User?> {
        return repository.getUserById(id)
    }

    fun onEvent(event: NoteEvent) {
        when (event) {
            is NoteEvent.OnGetByIdUser -> {
                viewModelScope.launch {
                    val dbUser = repository.getUserById(event.id)
                    user.postValue(dbUser.value)
                }
            }

            is NoteEvent.OnSaveUser -> {
                viewModelScope.launch(Dispatchers.IO) {
                    repository.saveUser(event.user)
                }
            }

            is NoteEvent.OnDeleteUser -> {
                viewModelScope.launch(Dispatchers.IO) {
                    repository.deleteUser(event.user)
                }
            }

            is NoteEvent.OnUpdateUser -> {
                viewModelScope.launch(Dispatchers.IO) {
                    repository.updateUser(event.user)
                }
            }
        }
    }
}