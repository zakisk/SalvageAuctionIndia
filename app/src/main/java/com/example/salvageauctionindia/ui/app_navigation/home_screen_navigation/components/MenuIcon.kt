package com.example.salvageauctionindia.ui.app_navigation.home_screen_navigation.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun MenuIcon(
    modifier: Modifier,
    imageVector: ImageVector = Icons.Default.Menu,
    onClick : () -> Unit
) {
    IconButton(onClick = {
           onClick()
        },
        modifier = modifier
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = null
        )
    }
}