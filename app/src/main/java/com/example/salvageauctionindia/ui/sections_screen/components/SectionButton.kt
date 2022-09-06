package com.example.salvageauctionindia.ui.sections_screen.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.salvageauctionindia.ui.theme.LocalCustomShapes
import com.example.salvageauctionindia.ui.theme.LocalSpacing
import com.example.salvageauctionindia.util.dpToSp

@Composable
fun SectionButton(
    modifier: Modifier,
    @DrawableRes iconId : Int,
    color : Color,
    @StringRes string1Id : Int,
    @StringRes string2Id : Int,
    navigate : () -> Unit
) {
    val containerSpacing = LocalSpacing.current.medium

    val buttonShape = LocalCustomShapes.current.mediumShape

    Box(
        modifier = modifier
            .padding(containerSpacing)
            .clip(buttonShape)
            .background(color = color.copy(0.05f))
            .width(120.dp)
            .height(130.dp)
            .clickable { navigate() }
    ) {

        Column(modifier = Modifier
            .padding(containerSpacing)
            .align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = null,
                tint = Color.Unspecified
            )

            Text(
                text = stringResource(id = string1Id),
                fontSize = dpToSp(dp = 14.dp),
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = stringResource(id = string2Id),
                fontSize = dpToSp(dp = 14.dp),
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        }

    }

}