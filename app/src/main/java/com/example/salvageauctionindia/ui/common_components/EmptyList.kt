package com.example.salvageauctionindia.ui.common_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.salvageauctionindia.R
import com.example.salvageauctionindia.ui.theme.LocalSpacing


@Composable
fun BoxScope.EmptyList() {

    val spacing = LocalSpacing.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_empty_list),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = "Nothing Found",
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(top = spacing.large)
        )
    }
}