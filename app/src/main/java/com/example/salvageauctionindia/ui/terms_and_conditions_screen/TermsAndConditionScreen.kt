package com.example.salvageauctionindia.ui.terms_and_conditions_screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.example.salvageauctionindia.R
import com.example.salvageauctionindia.ui.theme.LocalSpacing
import com.example.salvageauctionindia.ui.theme.Spacing


@Composable
fun TermsAndConditionScreen(
    spacing: Spacing = LocalSpacing.current,
    listBottomSpacing: Dp = spacing.listBottomSpacing
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
            .padding(bottom = listBottomSpacing),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Terms & Conditions",
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.padding(spacing.extraSmall)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }

            Text(
                text = stringResource(id = R.string.terms_and_conditions),
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(spacing.small)
            )
        }
    }
}