package com.example.salvageauctionindia.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.fragment.app.FragmentActivity
import coil.annotation.ExperimentalCoilApi
import com.example.salvageauctionindia.data.remote.SalvageMessagingService
import com.example.salvageauctionindia.domain.authentication.Authentication.sendOTP
import com.example.salvageauctionindia.domain.authentication.Authentication.verifyOTP
import com.example.salvageauctionindia.domain.workers.WorkerStarter
import com.example.salvageauctionindia.ui.app_navigation.home_screen_navigation.HomeScreen
import com.example.salvageauctionindia.ui.theme.MyApp
import com.example.salvageauctionindia.util.Constants.IS_ACTIVITY_RUNNING
import com.example.salvageauctionindia.util.Constants.NOTIFY_REFRESHED_TOKEN_ID
import com.example.salvageauctionindia.util.SharedPreferencesUtil
import com.example.salvageauctionindia.util.showToast
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

@OptIn(ExperimentalPagerApi::class, ExperimentalCoilApi::class)
@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    private lateinit var scaffoldState: ScaffoldState

    private lateinit var scope: CoroutineScope

    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions.all { it.value }) {
                showToast("Permission Granted")
            } else {
                showToast("Please Grant Permissions")
                onExit()
            }
        }


    private val takePhoto =
        registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris ->
            if (viewModel.images.value.size == 15) {
                showToast("Maximum 15 images can be selected.")
            } else {
                if ((uris + viewModel.images.value).size > 15) {
                    viewModel.images.value = (uris + viewModel.images.value).take(15)
                } else {
                    viewModel.images.value = uris + viewModel.images.value
                }
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initConnectionLiveData(this)
        Firebase.initialize(this)
        setContent {
            MyApp {
                scaffoldState = rememberScaffoldState()
                scope = rememberCoroutineScope()
                MainScreen()
                authListener()
                observeNetwork()
            }
        }
    }

    private fun observeNetwork() {
        viewModel.connectionLiveData.observe(this) {
            viewModel.isNetworkAvailable.value = it
        }
    }


    private val selectImage: () -> Unit = {
        takePhoto.launch("image/*")
    }


    private val onExit: () -> Unit = {
        try {
            exitProcess(0)
        } catch (e: Exception) {
            showToast("Error in Closing App")
        }
    }


    @Composable
    fun MainScreen() {
        HomeScreen(
            scaffoldState = scaffoldState,
            scope = scope,
            mainActivityViewModel = viewModel,
            selectImage = selectImage,
            sendOtp = sendOtp,
            verifyOtp = verifyOtp,
            onExit = onExit,
            requestPermissions = requestPermissions
        )
    }


    override fun onStart() {
        SharedPreferencesUtil(this).put(IS_ACTIVITY_RUNNING, true)
        super.onStart()
    }

    override fun onStop() {
        SharedPreferencesUtil(this).put(IS_ACTIVITY_RUNNING, false)
        super.onStop()
    }

    override fun onRestart() {
        viewModel.connectionLiveData.checkActiveNetwork()
        SharedPreferencesUtil(this).put(IS_ACTIVITY_RUNNING, true)
        super.onRestart()
    }


    private fun authListener() {
        FirebaseAuth.getInstance().addAuthStateListener {
            viewModel.user.value = it.currentUser
            scope.launch { scaffoldState.drawerState.close() }
        }
    }


    private val sendOtp: (String) -> Unit = { mobileNo ->
        sendOTP(mobileNo, this, verificationStateChangeListener)
    }

    private val verifyOtp: (String) -> Unit = { code ->
        viewModel.verificationId?.let { verificationId ->
            verifyOTP(
                context = this,
                verificationId = verificationId,
                code = code,
                isVerified = viewModel.isVerified,
                isOtpInvalid = viewModel.isOtpInvalid,
                errorMsg = viewModel.errorMsg
            )
        }
    }

    private val requestPermissions: () -> Unit = {
        val permissions = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        val isPermitted = permissions.all {
            checkSelfPermission(it) == PackageManager.PERMISSION_GRANTED
        }

        if (!isPermitted) {
            requestPermission.launch(permissions)
        }
    }


    override fun onBackPressed() {
        if (viewModel.isNetworkAvailable.value) {
            if (scaffoldState.drawerState.isOpen) {
                scope.launch { scaffoldState.drawerState.close() }
            } else {
                super.onBackPressed()
            }
        }
    }


    private val verificationStateChangeListener =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onCodeAutoRetrievalTimeOut(errorMsg: String) {
                viewModel.errorMsg.value = errorMsg
                viewModel.isResendOtp.value = true
                super.onCodeAutoRetrievalTimeOut(errorMsg)
            }

            override fun onCodeSent(
                verificationId: String,
                p1: PhoneAuthProvider.ForceResendingToken
            ) {
                viewModel.isOtpReceived.value = true
                viewModel.verificationId = verificationId
                super.onCodeSent(verificationId, p1)
            }

            override fun onVerificationCompleted(p0: PhoneAuthCredential) {}

            override fun onVerificationFailed(p0: FirebaseException) {
                showToast("Please Enter Correct Mobile Number Or You've tried too many times")
            }
        }

}