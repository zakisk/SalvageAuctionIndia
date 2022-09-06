package com.example.salvageauctionindia.ui.verify_otp_screen.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.salvageauctionindia.R
import com.example.salvageauctionindia.util.dpToSp


@Composable
fun HeaderText(spacing: Dp) {
    Text(
        text = stringResource(id = R.string.app_name),
        modifier = androidx.compose.ui.Modifier.padding(spacing),
        fontSize = dpToSp(dp = 16.dp),
        style = MaterialTheme.typography.h5,
        color = Color.Black
    )
}