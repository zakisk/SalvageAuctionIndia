package com.example.salvageauctionindia.ui.carousel_screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.example.salvageauctionindia.R
import com.example.salvageauctionindia.ui.theme.LocalSpacing


@Composable
fun Download(onClick: () -> Unit) {
    val spacing = LocalSpacing.current.medium

    Box(
        modifier = Modifier
            .clickable { onClick() }
            .fillMaxWidth(0.5f)
            .padding(vertical = spacing)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_cloud_download_24),
                contentDescription = null,
                tint = Color.White
            )
            Text(
                text = " Download",
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )
        }
    }
}