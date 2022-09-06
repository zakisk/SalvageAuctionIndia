package com.example.salvageauctionindia.ui.login_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.salvageauctionindia.ui.app_navigation.Screen
import com.example.salvageauctionindia.ui.common_components.Header
import com.example.salvageauctionindia.ui.common_components.MyButton
import com.example.salvageauctionindia.ui.common_components.MyTextField
import com.example.salvageauctionindia.ui.login_screen.components.TAndCRow
import com.example.salvageauctionindia.ui.login_screen.components.VectorImage
import com.example.salvageauctionindia.ui.theme.Apricot
import com.example.salvageauctionindia.ui.theme.LocalCustomShapes
import com.example.salvageauctionindia.ui.theme.LocalSpacing
import com.example.salvageauctionindia.util.SharedPreferencesUtil
import com.example.salvageauctionindia.util.coloredShadow

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navController: NavHostController,
    sendOtp: (String) -> Unit,
) {

    val spacing = LocalSpacing.current.small
    val oval = LocalCustomShapes.current.ovalShape
    val context = LocalContext.current

    val defaultModifier = Modifier
        .fillMaxWidth()
        .padding(all = spacing)

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = defaultModifier.coloredShadow(color = Apricot),
            elevation = 4.dp
        ) {
            LazyColumn(modifier = defaultModifier) {

                //Header
                item {
                    Header(defaultModifier, 24.dp, Color.White)
                }

                //Drawables
                item {
                    VectorImage(defaultModifier)
                }

                //Input Fields
                item {
                    Column(
                        modifier = defaultModifier
                    ) {

                        MyTextField(
                            text = viewModel.name,
                            error = viewModel.nameError,
                            minCharactersConstraint = 3,
                            placeholderText = "Name *",
                            errorText = "Please Enter Name",
                            shape = oval
                        )

                        MyTextField(
                            text = viewModel.mobile,
                            error = viewModel.mobileError,
                            minCharactersConstraint = 10,
                            maxCharacterConstraint = 10,
                            placeholderText = "Mobile Number *",
                            errorText = "Please Enter Mobile Number",
                            leadingIcon = { Text("  +91 ") },
                            shape = oval,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )

                        MyTextField(
                            text = viewModel.city,
                            error = viewModel.cityError,
                            minCharactersConstraint = 3,
                            placeholderText = "City *",
                            errorText = "Please EnterCity",
                            shape = oval
                        )

                        MyTextField(
                            text = viewModel.state,
                            error = viewModel.stateError,
                            minCharactersConstraint = 3,
                            placeholderText = "State *",
                            errorText = "Please Enter Residing State",
                            shape = oval
                        )

                        TAndCRow(modifier = defaultModifier, isChecked = viewModel.isChecked)

                        MyButton(
                            text = "Login",
                            enabled = viewModel.nameError.value &&
                                    viewModel.mobileError.value &&
                                    viewModel.cityError.value &&
                                    viewModel.stateError.value &&
                                    viewModel.isChecked.value,
                            onClick = {
                                SharedPreferencesUtil(context).savePreferences(
                                    viewModel.name.value,
                                    viewModel.mobile.value,
                                    viewModel.city.value,
                                    viewModel.state.value
                                )
                                sendOtp(viewModel.mobile.value)
                                navController.navigate(Screen.VerifyOtpScreen.route + "/${viewModel.mobile.value}")
                            })

                    }
                }
            }
        }
    }
}

