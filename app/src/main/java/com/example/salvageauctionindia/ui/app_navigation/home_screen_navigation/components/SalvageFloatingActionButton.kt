package com.example.salvageauctionindia.ui.app_navigation.home_screen_navigation.components

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.salvageauctionindia.ui.theme.Apricot

@Composable
fun SalvageFloatingActionButton(
    onClick : () -> Unit
) {

    FloatingActionButton(
        onClick = { onClick() },
        backgroundColor = Apricot,
        elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 2.dp)
    ) {
        Text(
            "Sell",
            fontWeight = FontWeight.Bold,
            color = Color.Black,
        )
    }
    
}