package com.example.salvageauctionindia.ui.app_navigation.home_screen_navigation.components

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.salvageauctionindia.R
import com.example.salvageauctionindia.ui.common_components.MyTextButton
import com.example.salvageauctionindia.ui.theme.LocalCustomShapes
import com.example.salvageauctionindia.util.dpToSp
import com.example.salvageauctionindia.util.showToast


@Composable
fun NetworkNotAvailableDialog(onExit: () -> Unit) {

    Dialog(
        onDismissRequest = { },
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        val backgroundShape = LocalCustomShapes.current.cardShape
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White, shape = backgroundShape),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Title()
            Body()
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                val context = LocalContext.current
                val intent = Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS)
                MyTextButton(text = "Exit", onClick = { onExit() })
                MyTextButton(text = "Settings", onClick = {
                    try {
                        context.startActivity(intent)
                    } catch (e: Exception) {
                        context.showToast("error in opening settings : $e")
                    }
                })
            }
        }
    }
}

@Composable
fun Title() {
    Text(
        text = "Please Check your Connectivity",
        fontSize = dpToSp(dp = 16.dp),
        fontWeight = FontWeight.W900,
        textAlign = TextAlign.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 16.dp)
    )
}

@Composable
fun Body() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        Image(
            painter = painterResource(id = R.mipmap.ic_banner_foreground),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .fillMaxHeight(0.6f)
        )
    }
}