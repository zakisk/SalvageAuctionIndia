package com.example.salvageauctionindia.ui.login_screen.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.salvageauctionindia.R

@Composable
fun VectorImage(modifier : Modifier) {
    Image(
        painter = painterResource(id = R.drawable.ic_login_svg),
        contentDescription = null,
        modifier = modifier
    )
}