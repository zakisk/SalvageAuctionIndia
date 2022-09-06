package com.example.salvageauctionindia.ui.login_screen.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ErrorMessage(
    message: String,
    modifier: Modifier
) {
        Text(
            text = message,
            color = MaterialTheme.colors.error,
            style = MaterialTheme.typography.caption,
            modifier = modifier.padding(start = 24.dp)
        )
}