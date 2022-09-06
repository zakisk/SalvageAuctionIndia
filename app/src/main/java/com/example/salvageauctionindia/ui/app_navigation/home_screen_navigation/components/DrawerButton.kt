package com.example.salvageauctionindia.ui.app_navigation.home_screen_navigation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.salvageauctionindia.ui.theme.Apricot
import com.example.salvageauctionindia.ui.theme.LocalCustomShapes

@Composable
fun DrawerButton(
    imageVector: ImageVector,
    text : String,
    color: Color = Apricot,
    onClick : () -> Unit
) {

    val shape = LocalCustomShapes.current.drawerButtonShape

    val modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp)

    Row(
        Modifier
            .fillMaxWidth()
            .padding(end = 16.dp, top = 16.dp)
            .clip(shape)
            .background(color = color.copy(0.09f))
            .clickable {
                onClick()
            }
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = null,
            tint = Color.Black,
            modifier = modifier
        )


        Text(
            text = text,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = modifier
        )
    }

}



@Composable
fun DrawerButton(
    @DrawableRes resId : Int,
    text : String,
    color: Color = Apricot,
    onClick : () -> Unit
) {

    val shape = LocalCustomShapes.current.drawerButtonShape

    val modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp)

    Row(
        Modifier
            .fillMaxWidth()
            .padding(end = 16.dp, top = 16.dp)
            .clip(shape)
            .background(color = color.copy(0.09f))
            .clickable {
                onClick()
            }
    ) {
        Icon(
            painter = painterResource(id = resId),
            contentDescription = null,
            tint = Color.Black,
            modifier = modifier
        )


        Text(
            text = text,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = modifier
        )
    }

}