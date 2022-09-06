package com.example.salvageauctionindia.ui.app_navigation.home_screen_navigation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.salvageauctionindia.R
import com.example.salvageauctionindia.ui.app_navigation.Screen


@Composable
fun SalvageDrawer(
    userName : String,
    onLogout : () -> Unit,
    navController: NavHostController,
    openCloseDrawer: () -> Unit
) {

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .align(Alignment.TopCenter)
            .fillMaxWidth()
        ) {

            ProfileSection(userName, onLogout)

            DrawerButton(
                imageVector = Icons.Default.Call,
                text = "CONTACT US",
                onClick = {
                    openCloseDrawer()
                    navController.navigate(Screen.ContactUsScreen.route)
                }
            )

            DrawerButton(
                imageVector = Icons.Default.Info,
                text = "ABOUT US",
                onClick = {
                    openCloseDrawer()
                    navController.navigate(Screen.AboutUsScreen.route)
                }
            )

            DrawerButton(
                resId = R.drawable.ic_terms_and_conditions,
                text = "TERMS & CONDITIONS",
                onClick = {
                    openCloseDrawer()
                    navController.navigate(Screen.TermsAndConditionsScreen.route)
                }
            )

            DrawerButton(
                imageVector = Icons.Default.Share,
                text = "Share App",
                onClick = {
                    openCloseDrawer()
                }
            )

        }
    }

}