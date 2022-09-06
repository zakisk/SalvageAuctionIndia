package com.example.salvageauctionindia.ui.sell_screen.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.example.salvageauctionindia.ui.theme.Apricot
import com.example.salvageauctionindia.ui.theme.LocalCustomShapes
import com.example.salvageauctionindia.ui.theme.LocalSpacing


@Composable
fun MyDropdownMenu(
    expanded: MutableState<Boolean>,
    selected: MutableState<String>,
    items: List<String>
) {
    val spacing = LocalSpacing.current

    var size by remember { mutableStateOf(Size.Zero) }
    val width: @Composable () -> Dp = { with(LocalDensity.current) { size.width.toDp() } }

    val shape = LocalCustomShapes.current.smallShape

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = spacing.small)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape)
                .border(
                    width = 1.dp,
                    color = if (expanded.value) Apricot else Color.Gray,
                    shape = shape
                )
                .onGloballyPositioned {
                    size = it.size.toSize()
                }
                .clickable { expanded.value = true }
                .padding(spacing.medium),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = selected.value)
            Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
        }

        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
            modifier = Modifier
                .width(width())
        ) {

            items.forEach { item ->
                DropdownMenuItem(
                    onClick = {
                        selected.value = item
                        expanded.value = false
                    }
                ) {
                    Text(
                        text = item,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(spacing.medium),
                        color = Color.Black
                    )
                }
            }
        }
    }
}