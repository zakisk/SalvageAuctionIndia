package com.example.salvageauctionindia.ui.carousel_screen.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun ButtonsRow(
    onClickDownload: () -> Unit,
    onClickShare: () -> Unit
) {

    Row(modifier = Modifier.fillMaxWidth()) {
        Download {
            onClickDownload()
        }

        Share {
            onClickShare()
        }
    }
}