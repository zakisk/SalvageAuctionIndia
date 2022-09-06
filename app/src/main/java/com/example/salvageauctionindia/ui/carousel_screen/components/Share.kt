package com.example.salvageauctionindia.ui.carousel_screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.salvageauctionindia.ui.theme.LocalSpacing

@Composable
fun Share(onClick: () -> Unit) {
    val spacing = LocalSpacing.current.medium

    Box(
        modifier = Modifier
            .clickable { onClick() }
            .padding(vertical = spacing)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = null,
                tint = Color.White
            )
            Text(
                text = " Share",
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )
        }
    }
}