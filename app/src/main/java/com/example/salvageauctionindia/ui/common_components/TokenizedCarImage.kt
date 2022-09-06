package com.example.salvageauctionindia.ui.common_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.salvageauctionindia.R
import com.example.salvageauctionindia.ui.theme.LocalSpacing

@Composable
fun TokenizedCarImage(modifier: Modifier) {
    val spacing = LocalSpacing.current.small

    Image(
        painter = painterResource(id = R.drawable.ic_token),
        contentDescription = null,
        modifier = modifier.padding(spacing)
    )
}