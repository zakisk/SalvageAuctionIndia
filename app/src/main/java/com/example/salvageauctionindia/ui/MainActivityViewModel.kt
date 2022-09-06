package com.example.salvageauctionindia.ui

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.salvageauctionindia.data.remote.dtos.NotificationTokenDto
import com.example.salvageauctionindia.domain.workers.WorkerStarter
import com.example.salvageauctionindia.util.ConnectionLiveData
import com.example.salvageauctionindia.util.Constants.DEVICE_ID
import com.example.salvageauctionindia.util.Constants.USER_NAME
import com.example.salvageauctionindia.util.SharedPreferencesUtil
import com.example.salvageauctionindia.util.showToast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject


@HiltViewModel
class MainActivityViewModel @Inject constructor() : ViewModel() {

    var imagePrefix: MutableState<String> = mutableStateOf("")

    lateinit var connectionLiveData: ConnectionLiveData

    val images: MutableState<List<Uri>> = mutableStateOf(emptyList())
    val isNetworkAvailable: MutableState<Boolean> = mutableStateOf(true)
    val isResendOtp: MutableState<Boolean> = mutableStateOf(false)
    val isVerified: MutableState<Boolean> = mutableStateOf(false)
    val isOtpInvalid: MutableState<Boolean> = mutableStateOf(false)
    val isOtpReceived: MutableState<Boolean> = mutableStateOf(false)
    val errorMsg: MutableState<String?> = mutableStateOf(null)
    val user: MutableState<FirebaseUser?> = mutableStateOf(FirebaseAuth.getInstance().currentUser)
    val uri: MutableState<Uri?> = mutableStateOf(null)
    var verificationId: String? = null

    fun initConnectionLiveData(context: Context) {
        connectionLiveData = ConnectionLiveData(context)
    }
}