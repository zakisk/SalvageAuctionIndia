package com.example.salvageauctionindia.ui.app_navigation.home_screen_navigation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.salvageauctionindia.ui.theme.Apricot
import com.example.salvageauctionindia.ui.theme.LocalCustomShapes
import com.example.salvageauctionindia.ui.vehicle_detail_screen.components.Div
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ProfileSection(
    userName: String,
    onLogout : () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val shape = LocalCustomShapes.current.ovalShape

        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = null,
            tint = Apricot.copy(0.8f),
            modifier = Modifier.size(80.dp)
        )

        Text(
            text = userName, modifier = Modifier.padding(bottom = 8.dp)
        )

        TextButton(
            onClick = {
                FirebaseAuth.getInstance().signOut()
                onLogout()
            },
            shape = shape,
            colors = ButtonDefaults.buttonColors(
                contentColor = Apricot,
                backgroundColor = Color.Transparent,
                disabledBackgroundColor = Color.Transparent
            )
        ) {
            Text(text = "Logout", color = Apricot, modifier = Modifier.padding(4.dp))
        }

        Div()


    }

}