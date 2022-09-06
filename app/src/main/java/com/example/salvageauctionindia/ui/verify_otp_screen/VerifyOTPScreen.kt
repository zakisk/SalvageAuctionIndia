package com.example.salvageauctionindia.ui.verify_otp_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.salvageauctionindia.ui.common_components.MyButton
import com.example.salvageauctionindia.ui.common_components.MyTextButton
import com.example.salvageauctionindia.ui.common_components.MyTextField
import com.example.salvageauctionindia.ui.login_screen.components.ErrorMessage
import com.example.salvageauctionindia.ui.theme.Apricot
import com.example.salvageauctionindia.ui.theme.LocalCustomShapes
import com.example.salvageauctionindia.ui.theme.LocalSpacing
import com.example.salvageauctionindia.ui.verify_otp_screen.components.HeaderText
import com.example.salvageauctionindia.ui.verify_otp_screen.components.VectorImage
import com.example.salvageauctionindia.util.coloredShadow
import kotlinx.coroutines.delay


@Composable
fun VerifyOTPScreen(
    viewModel: VerifyOtpViewModel = hiltViewModel(),
    mobileNo: String,
    onVerified: @Composable () -> Unit,
    verifyOtp: (String) -> Unit,
    sendOtp: (String) -> Unit,
    isResendOtp: MutableState<Boolean>,
    isVerified: MutableState<Boolean>,
    isOtpInvalid: MutableState<Boolean>,
    isOtpReceived: MutableState<Boolean>,
    errorMessage: MutableState<String?>
) {
    val spacing = LocalSpacing.current
    val oval = LocalCustomShapes.current.ovalShape
    var startTimer by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = spacing.medium)
                .coloredShadow(Apricot),
            elevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing.small),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                HeaderText(spacing = spacing.small)

                VectorImage()

                MyTextField(
                    text = viewModel.otp,
                    error = viewModel.otpError,
                    minCharactersConstraint = 0,
                    maxCharacterConstraint = 6,
                    placeholderText = "Enter OTP",
                    errorText = "Please Enter OTP",
                    shape = oval,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                if (isOtpInvalid.value) {
                    ErrorMessage(
                        message = "Please Enter Valid OTP",
                        modifier = Modifier.padding(vertical = spacing.small)
                    )
                }

                LaunchedEffect(key1 = startTimer) {
                    while (viewModel.seconds.value != 0) {
                        viewModel.seconds.value--
                        delay(1000)
                    }
                    isResendOtp.value = true
                    isOtpReceived.value = false
                }

                Text(
                    text = "OTP has been sent to $mobileNo.",
                    modifier = Modifier.padding(spacing.small),
                    color = Color.Gray,
                    style = MaterialTheme.typography.caption
                )

                Text(
                    text = "Timeout : 00 : ${if (viewModel.seconds.value < 10) "0${viewModel.seconds.value}" else viewModel.seconds.value}"
                )

                MyButton(
                    text = "verify",
                    enabled = viewModel.otp.value.length == 6,
                    onClick = { verifyOtp(viewModel.otp.value) }
                )

                MyTextButton(
                    text = "Resend OTP",
                    enabled = isResendOtp.value && !isOtpReceived.value,
                    onClick = {
                        sendOtp(mobileNo)
                        viewModel.seconds.value = 60
                        startTimer = !startTimer
                        isResendOtp.value = false
                    }
                )


                isVerified.let {
                    if (it.value) {
                        viewModel.seconds.value = 0
                        onVerified()
                    }
                }
            }
        }
    }
}