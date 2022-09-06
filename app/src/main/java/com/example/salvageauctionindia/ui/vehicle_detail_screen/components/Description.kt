package com.example.salvageauctionindia.ui.vehicle_detail_screen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun Description(description: String) {
    Text(
        text = "Description:- ",
        color = Color.DarkGray,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 5.dp),
        style = MaterialTheme.typography.caption,
        textAlign = TextAlign.Start,
        fontWeight = FontWeight.Bold
    )

    Text(
        text = description,
        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
        style = MaterialTheme.typography.caption
    )
}