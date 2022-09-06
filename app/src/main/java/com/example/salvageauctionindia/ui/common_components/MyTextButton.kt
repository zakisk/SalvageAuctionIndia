package com.example.salvageauctionindia.ui.common_components

import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.salvageauctionindia.ui.theme.Apricot
import com.example.salvageauctionindia.ui.theme.LocalCustomShapes


@Composable
fun MyTextButton(
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit
) {

    val oval = LocalCustomShapes.current.ovalShape

    TextButton(
        onClick = { onClick() },
        shape = oval,
        colors = ButtonDefaults.buttonColors(
            contentColor = Apricot,
            backgroundColor = Color.Transparent,
            disabledBackgroundColor = Color.Transparent
        ),
        enabled = enabled
    ) {
        Text(text = text)
    }

}