package com.example.salvageauctionindia.ui.verify_otp_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class VerifyOtpViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val otp : MutableState<String> = mutableStateOf("")
    val otpError : MutableState<Boolean> = mutableStateOf(true)
    val seconds : MutableState<Int> = mutableStateOf(60)

}