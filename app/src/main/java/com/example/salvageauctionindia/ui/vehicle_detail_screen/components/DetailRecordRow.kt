package com.example.salvageauctionindia.ui.vehicle_detail_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DetailRecordRow(modifier : Modifier, headText : String, valText : String) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        HeadText(text = headText)
        ValText(text = valText)
    }
}