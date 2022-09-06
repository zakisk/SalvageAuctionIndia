package com.example.salvageauctionindia.ui.verify_otp_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.salvageauctionindia.R


@Composable
fun VectorImage() {
    Image(
        painter = painterResource(id = R.drawable.ic_otp_verification),
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.4f)
    )
}