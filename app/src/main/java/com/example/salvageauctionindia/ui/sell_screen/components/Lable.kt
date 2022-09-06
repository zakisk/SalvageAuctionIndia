package com.example.salvageauctionindia.ui.sell_screen.components


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.salvageauctionindia.ui.theme.LocalSpacing


@Composable
fun Label(label: String) {
    val spacing = LocalSpacing.current
    Text(
        text = label,
        color = Color.Gray,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = spacing.small, end = spacing.small, top = spacing.small)
    )
}