package com.example.salvageauctionindia.ui.sell_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import com.example.salvageauctionindia.ui.theme.LocalCustomShapes
import com.example.salvageauctionindia.ui.theme.LocalSpacing


@Composable
fun AddImage(
    onClick: () -> Unit,
    isActiveAddImage: Boolean
) {

    val spacing = LocalSpacing.current
    val shape = LocalCustomShapes.current.smallShape

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.small)
            .border(
                1.dp,
                color = if (isActiveAddImage) Color.Black else Color.LightGray,
                shape = shape
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                if (isActiveAddImage) {
                    onClick()
                }
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            imageVector = Icons.Default.Add,
            contentDescription = null,
            modifier = Modifier.padding(spacing.small),
            colorFilter = ColorFilter.tint(if (isActiveAddImage) Color.Black else Color.LightGray)
        )
    }
}