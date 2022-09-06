package com.example.salvageauctionindia.ui.sell_screen.components

import android.widget.CalendarView
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView


@Composable
fun DatePicker(
    onDateSelected : (Int, Int, Int) -> Unit
) {
    AndroidView(
        factory = { CalendarView(it) },
        modifier = Modifier
            .wrapContentWidth(),
        update = { views ->
            views.setOnDateChangeListener { _, year, month, day ->
                onDateSelected(year, month + 1, day)
            }
        }
    )
}