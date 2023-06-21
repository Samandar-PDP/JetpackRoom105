package com.sdk.roomcomposemvvm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sdk.roomcomposemvvm.repository.NoteRepository

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(
    private val repository: NoteRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}