package com.sdk.roomcomposemvvm.viewmodel

import com.sdk.roomcomposemvvm.database.User

sealed interface NoteEvent {
    data class OnSaveUser(val user: User): NoteEvent
    data class OnUpdateUser(val user: User): NoteEvent
    data class OnDeleteUser(val user: User): NoteEvent
    data class OnGetByIdUser(val id: Int): NoteEvent
}