package com.example.contentresolverclientnoteapp.presentation.screens

import androidx.lifecycle.ViewModel
import com.example.contentresolverclientnoteapp.Note
import kotlinx.coroutines.flow.MutableStateFlow

class MainViewModel : ViewModel() {
    val notes = MutableStateFlow<List<Note>>(emptyList<Note>())

}