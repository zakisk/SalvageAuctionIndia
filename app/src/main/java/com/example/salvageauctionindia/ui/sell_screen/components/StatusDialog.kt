package com.example.salvageauctionindia.ui.sell_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.salvageauctionindia.R
import com.example.salvageauctionindia.ui.theme.Apricot
import com.example.salvageauctionindia.ui.theme.LocalCustomShapes
import com.example.salvageauctionindia.ui.theme.LocalSpacing


@Composable
fun StatusDialog(
    isSuccess: Boolean = false,
    navigate: () -> Unit = {}
) {
    val shape = LocalCustomShapes.current.mediumShape

    val spacing = LocalSpacing.current

    Dialog(
        onDismissRequest = { },
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Column(
            modifier = Modifier.background(Color.Black.copy(0.4f), shape = shape)
        ) {
            if (isSuccess) {
                Image(
                    painter = painterResource(id = R.drawable.ic_done),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(spacing.medium)
                        .size(48.dp),
                    alignment = Alignment.Center
                )

                Text(
                    text = "Done",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { navigate.invoke() }
                        .padding(spacing.medium),
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            } else {
                CircularProgressIndicator(
                    color = Apricot,
                    modifier = Modifier
                        .padding(spacing.large)
                        .size(24.dp),
                    strokeWidth = 2.dp
                )
            }
        }

    }

}