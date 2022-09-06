package com.example.salvageauctionindia.ui.vehicle_detail_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import com.example.salvageauctionindia.R
import com.example.salvageauctionindia.ui.theme.DarkGreen
import com.example.salvageauctionindia.ui.theme.LocalSpacing


@Composable
fun ChatButton(
    onClick: () -> Unit,
) {
    val spacing = LocalSpacing.current.medium

    Box(
        modifier = Modifier
            .clickable { onClick() }
            .fillMaxWidth(0.5f)
            .background(Color.DarkGreen)
            .padding(vertical = spacing)
    ) {
        Row(modifier = Modifier.align(Alignment.Center)) {
            Icon(
                painter = painterResource(id = R.drawable.ic_whatsapp),
                contentDescription = null,
                tint = Color.White
            )
            Text(
                text = " CHAT",
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )
        }
    }
}