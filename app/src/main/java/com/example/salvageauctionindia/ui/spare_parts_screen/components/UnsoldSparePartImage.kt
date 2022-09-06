package com.example.salvageauctionindia.ui.spare_parts_screen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import coil.annotation.ExperimentalCoilApi
import com.example.salvageauctionindia.ui.vehicle_detail_screen.components.CarouselImageItem
import com.example.salvageauctionindia.util.Constants.BASE_URL


@ExperimentalCoilApi
@Composable
fun UnsoldSparePartImage(
    data: String,
    height: Dp
) {
    CarouselImageItem(
        modifier = Modifier.fillMaxWidth()
            .height(height),
        data = "${BASE_URL}get-image/$data",
        contentScale = ContentScale.FillHeight
    )
}