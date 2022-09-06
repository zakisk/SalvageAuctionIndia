package com.example.salvageauctionindia.ui.vehicle_detail_screen.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.salvageauctionindia.R


@ExperimentalCoilApi
@Composable
fun CarouselImageItem(
    modifier: Modifier,
    contentScale: ContentScale,
    data: String,
    onClick: () -> Unit = {},
    userScrollEnabled: MutableState<Boolean> = mutableStateOf(true),
    isZoomable: Boolean = false
) {

    val context = LocalContext.current
    val request = ImageRequest.Builder(context)
        .data(data)
        .crossfade(true)
        .error(R.drawable.ic_error)
        .build()

    val painter = rememberImagePainter(request = request)


    val maxScale = .30f
    val minScale = 3f
    var scale by remember { mutableStateOf(1f) }
    var rotationState by remember { mutableStateOf(1f) }
    var offsetX by remember { mutableStateOf(1f) }
    var offsetY by remember { mutableStateOf(1f) }
    fun offsetXMinScale() = if (offsetX > 1f) 1f * scale else -1f * scale
    fun offsetXMaxScale() = if (offsetX > 1f) 100f * scale else -100f * scale
    fun offsetYMinScale() = if (offsetY > 1f) 1f * scale else -1f * scale
    fun offsetYMaxScale() = if (offsetY > 1f) 100f * scale else -100f * scale

    userScrollEnabled.value = scale <= 1f


        Image(
            painter = painter,
            contentDescription = null,
            contentScale = contentScale,
            modifier = modifier
                .background(Color.Black)
                .graphicsLayer {
                    scaleX = maxOf(maxScale, minOf(minScale, scale))
                    scaleY = maxOf(maxScale, minOf(minScale, scale))
                    translationX = maxOf(offsetXMaxScale(), minOf(offsetXMinScale(), offsetX))
                    translationY = maxOf(offsetYMaxScale(), minOf(offsetYMinScale(), offsetY))
                }
                .pointerInput(Unit) {
                    if (isZoomable) {
                        forEachGesture {
                            awaitPointerEventScope {
                                awaitFirstDown()
                                do {
                                    val event = awaitPointerEvent()
                                    scale *= event.calculateZoom()
                                    if (scale > 1f) {
                                        val offset = event.calculatePan()
                                        offsetX += offset.x
                                        offsetY += offset.y
                                        rotationState += event.calculateRotation()
                                    } else {
                                        scale = 1f
                                        offsetX = 1f
                                        offsetY = 1f
                                    }
                                } while (event.changes.any { it.pressed })
                            }
                        }
                    } else {
                        forEachGesture {
                            awaitPointerEventScope {
                                val event = awaitPointerEvent()
                                if (event.type == PointerEventType.Release) {
                                    onClick()
                                }
                            }
                        }
                    }
                }
        )


}