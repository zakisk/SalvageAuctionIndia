package com.example.salvageauctionindia.domain.authentication

import android.app.Activity
import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.lifecycle.LifecycleOwner
import androidx.work.WorkInfo
import com.example.salvageauctionindia.data.remote.dtos.UserDto
import com.example.salvageauctionindia.domain.workers.WorkerStarter
import com.example.salvageauctionindia.util.Constants.USER_CITY
import com.example.salvageauctionindia.util.Constants.USER_INFORMATION_UPLOADED_TO_SERVER
import com.example.salvageauctionindia.util.Constants.USER_MOBILE
import com.example.salvageauctionindia.util.Constants.USER_NAME
import com.example.salvageauctionindia.util.Constants.USER_STATE
import com.example.salvageauctionindia.util.SharedPreferencesUtil
import com.example.salvageauctionindia.util.toFormattedString
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.*
import java.util.concurrent.TimeUnit

object Authentication {
    private val auth = FirebaseAuth.getInstance()
    private const val countryCode = "+91"

    fun sendOTP(
        mobileNo: String,
        activity: Activity,
        verificationStateChangeListener: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    ) {

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(countryCode + mobileNo)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(verificationStateChangeListener)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun verifyOTP(
        context: Context,
        verificationId: String,
        code: String,
        isVerified: MutableState<Boolean>,
        isOtpInvalid: MutableState<Boolean>,
        errorMsg: MutableState<String?>
    ) {
        val credential = PhoneAuthProvider.getCredential(verificationId, code)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                startCreateUserWorker(context, it.result.user!!.uid)
                isVerified.value = true
            } else {
                if (it.exception is FirebaseAuthInvalidCredentialsException) {
                    isOtpInvalid.value = true
                } else {
                    errorMsg.value = it.exception?.localizedMessage
                }
                isVerified.value = false
            }
        }
    }

    private fun startCreateUserWorker(
        context: Context,
        userId: String
    ) {
        val user = createUserDto(context, userId)
        WorkerStarter(context).startCreateUser(user)
            .observe(context as LifecycleOwner) {
                when (it.state) {

                    WorkInfo.State.FAILED -> {
                        SharedPreferencesUtil(context).put(
                            USER_INFORMATION_UPLOADED_TO_SERVER,
                            false
                        )
                    }

                    WorkInfo.State.SUCCEEDED -> {
                        SharedPreferencesUtil(context).put(
                            USER_INFORMATION_UPLOADED_TO_SERVER,
                            true
                        )
                    }

                    WorkInfo.State.ENQUEUED -> {}

                    WorkInfo.State.RUNNING -> {}

                    WorkInfo.State.BLOCKED -> {}

                    WorkInfo.State.CANCELLED -> {}

                }
            }
    }


    private fun createUserDto(context: Context, userId: String): UserDto {
        val pref = SharedPreferencesUtil(context)
        val name = pref.get<String>(USER_NAME) ?: "N/A"
        val city = pref.get<String>(USER_CITY) ?: "N/A"
        val state = pref.get<String>(USER_STATE) ?: "N/A"
        val mobileNo = pref.get<String>(USER_MOBILE) ?: "N/A"
        return UserDto(
            userId = userId,
            name = name,
            city = city,
            state = state,
            mobileNo = mobileNo,
            timeStamp = Calendar.getInstance().time.toFormattedString(),
            isBlocked = 0
        )
    }

}