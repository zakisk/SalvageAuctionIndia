package com.example.salvageauctionindia.ui.sell_screen.components

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import com.example.salvageauctionindia.util.asBitmap


@Composable
fun ImagesRow(
    images: MutableState<List<Uri>>,
    selectImage: () -> Unit,
    onCloseClicked: (Int) -> Unit
) {

    val context = LocalContext.current

    val bitmaps: MutableState<List<Bitmap?>> = remember { mutableStateOf(emptyList()) }
    LaunchedEffect(key1 = images.value) {
        bitmaps.value = images.value.map { it.asBitmap(context) }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        AddImage(onClick = selectImage, isActiveAddImage = images.value.size < 15)
        if (bitmaps.value.isNotEmpty()) {
            LazyRow {
                itemsIndexed(bitmaps.value) { index, image ->
                    image.let {
                        image?.asImageBitmap()?.let { imageBitmap ->
                            VehicleImage(
                                image = imageBitmap,
                                index = index,
                                onCloseClicked = onCloseClicked
                            )
                        }
                    }
                }
            }
        }
    }

}