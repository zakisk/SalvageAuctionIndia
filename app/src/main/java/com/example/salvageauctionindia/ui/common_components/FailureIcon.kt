package com.example.salvageauctionindia.ui.common_components

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.salvageauctionindia.R

@Composable
fun BoxScope.FailureIcon(spacing : Dp = 0.dp) {
    Icon(
        painter = painterResource(id = R.drawable.ic_error),
        contentDescription = null,
        modifier = Modifier
            .align(Alignment.Center)
            .padding(vertical = spacing)
    )
}