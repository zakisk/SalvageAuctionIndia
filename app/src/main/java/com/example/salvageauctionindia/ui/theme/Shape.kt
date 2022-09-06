package com.example.salvageauctionindia.ui.theme

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(4.dp),
    large = RoundedCornerShape(0.dp)
)

data class CustomShapes (
    val ovalShape : CornerBasedShape = RoundedCornerShape(50),
    val cardShape : CornerBasedShape = RoundedCornerShape(8.dp),
    val imageShape : CornerBasedShape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp),
    val drawerButtonShape : CornerBasedShape = RoundedCornerShape(topEndPercent = 50, bottomEndPercent = 50),
    val largeImageShape: CornerBasedShape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp),
    val smallShape : CornerBasedShape = RoundedCornerShape(4.dp),
    val mediumShape : CornerBasedShape = RoundedCornerShape(8.dp)
)

val LocalCustomShapes = compositionLocalOf { CustomShapes() }