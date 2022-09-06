package com.example.salvageauctionindia.ui.vehicle_detail_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp


@Composable
fun BoxScope.ArrowButton(
    alignment: Alignment,
    imageVector: ImageVector,
    onClick : () -> Unit
) {

    Row(modifier = Modifier
        .align(alignment)
        .clip(CircleShape)
        .background(Color.LightGray.copy(0.1f), shape = CircleShape)
        .padding(8.dp)
        .clickable { onClick() }
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .clip(CircleShape)
                .padding(8.dp)
        )
    }
}