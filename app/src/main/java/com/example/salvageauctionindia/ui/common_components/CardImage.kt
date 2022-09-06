package com.example.salvageauctionindia.ui.common_components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.example.salvageauctionindia.ui.vehicle_detail_screen.components.CarouselImageItem
import com.example.salvageauctionindia.util.Constants.BASE_URL


@ExperimentalCoilApi
@Composable
fun CardImage(
    isSold: Boolean,
    isTokenized: Boolean = false,
    postId: String,
    primeImage: String,
    height: Dp = 250.dp,
    contentScale: ContentScale = ContentScale.FillBounds,
    fraction: Float = 1.0f,
    shape: CornerBasedShape? = null
) {

    val modifier = Modifier
        .fillMaxWidth(fraction)
        .border(width = 0.3.dp, color = Color.LightGray, shape = shape ?: RectangleShape)
        .background(color = Color.White, shape = shape ?: RectangleShape)
        .clip(shape ?: RectangleShape)
        .height(height)

    if (isSold || isTokenized) {
        if (isSold) {
            SoldCarImage(modifier)
        } else {
            TokenizedCarImage(modifier)
        }
    } else {
        CarouselImageItem(
            modifier = modifier,
            data = "${BASE_URL}get-image/$postId/$primeImage",
            contentScale = contentScale,
        )
    }

}