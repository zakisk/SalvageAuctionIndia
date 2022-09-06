package com.example.salvageauctionindia.ui.vehicle_detail_screen.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun HeadText(text: String) {
    Text(
        text = text,
        color = Color.DarkGray,
        modifier = Modifier
            .padding(2.dp),
        fontWeight = FontWeight.ExtraBold,
        style = MaterialTheme.typography.caption
    )
}

@Composable
fun ValText(text: String) {
    Text(
        text = text,
        modifier = Modifier.padding(2.dp),
        style = MaterialTheme.typography.caption
    )
}