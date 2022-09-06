package com.example.salvageauctionindia.ui.common_components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.salvageauctionindia.ui.theme.Apricot
import com.example.salvageauctionindia.ui.theme.LocalCustomShapes
import com.example.salvageauctionindia.ui.theme.LocalSpacing


@Composable
fun MyButton(
    text : String,
    enabled : Boolean = true,
    onClick : () -> Unit
) {
    val spacing = LocalSpacing.current
    val oval = LocalCustomShapes.current.ovalShape
    val defaultModifier = Modifier
        .fillMaxWidth()
        .padding(all = spacing.small)

    Button(
        onClick = { onClick() },
        modifier = defaultModifier,
        enabled = enabled,
        shape = oval,
        colors = ButtonDefaults.buttonColors(backgroundColor = Apricot)
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            modifier = defaultModifier,
            textAlign = TextAlign.Center
        )
    }
}