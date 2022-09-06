package com.example.salvageauctionindia.ui.about_us_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.salvageauctionindia.R
import com.example.salvageauctionindia.ui.theme.LocalSpacing

@Composable
fun AboutUsScreen() {
    val spacing = LocalSpacing.current
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "About Us",
                style = MaterialTheme.typography.h4,
                modifier = Modifier
                    .padding(spacing.extraSmall)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        Text(
            text = stringResource(id = R.string.about_us),
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(spacing.medium)
        )
    }
}