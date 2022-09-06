package com.example.salvageauctionindia.ui.login_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    val name : MutableState<String> = mutableStateOf("")
    val nameError : MutableState<Boolean> = mutableStateOf(true)
    val mobile : MutableState<String> = mutableStateOf("")
    val mobileError : MutableState<Boolean> = mutableStateOf(true)
    val city : MutableState<String> = mutableStateOf("")
    val cityError : MutableState<Boolean> = mutableStateOf(true)
    val state : MutableState<String> = mutableStateOf("")
    val stateError : MutableState<Boolean> = mutableStateOf(true)
    val isChecked: MutableState<Boolean> = mutableStateOf(false)
}