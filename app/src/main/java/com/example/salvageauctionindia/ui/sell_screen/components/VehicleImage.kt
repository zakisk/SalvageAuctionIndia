package com.example.salvageauctionindia.ui.sell_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import com.example.salvageauctionindia.ui.theme.LocalSpacing


@Composable
fun VehicleImage(
    image: ImageBitmap,
    index: Int,
    onCloseClicked: (Int) -> Unit
) {
    val spacing = LocalSpacing.current

    Box(modifier = Modifier
        .width(120.dp)
        .height(150.dp)
        .padding(spacing.small)
    ) {

            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(spacing.small),
                elevation = 4.dp
            ) {
                Image(
                    bitmap = image,
                    contentDescription = null,
                    modifier = Modifier.padding(spacing.extraSmall)
                )
            }

            Card(
                modifier = Modifier
                    .size(16.dp)
                    .align(Alignment.TopEnd),
                elevation = 4.dp,
                shape = CircleShape
            ) {
                Image(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Color.Black),
                    modifier = Modifier
                        .background(Color.White, shape = CircleShape)
                        .size(16.dp)
                        .padding(2.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { onCloseClicked(index) }
                )
            }

    }
}