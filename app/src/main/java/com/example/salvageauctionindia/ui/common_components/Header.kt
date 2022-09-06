package com.example.salvageauctionindia.ui.common_components


import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import com.example.salvageauctionindia.R
import com.example.salvageauctionindia.util.dpToSp

@Composable
fun Header(modifier: Modifier, fontSize : Dp, color : Color) {
    Text(
        text = stringResource(id = R.string.app_name),
        modifier = modifier,
        fontSize = dpToSp(dp = fontSize),
        fontWeight = FontWeight.ExtraBold,
        color = color
    )
}