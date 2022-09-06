package com.example.salvageauctionindia.ui.spare_parts_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.example.salvageauctionindia.R
import com.example.salvageauctionindia.domain.model.SparePart
import com.example.salvageauctionindia.ui.common_components.CardImage
import com.example.salvageauctionindia.ui.theme.DarkGreen
import com.example.salvageauctionindia.ui.theme.LocalCustomShapes
import com.example.salvageauctionindia.ui.theme.LocalSpacing


@ExperimentalCoilApi
@Composable
fun SparePartCardItem(
    sparePart: SparePart,
    onItemClicked: (SparePart) -> Unit
) {
    val spacing = LocalSpacing.current

    val shape = LocalCustomShapes.current.cardShape

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.medium)
            .clip(shape)
            .background(Color.White, shape = shape)
            .border(width = 0.3.dp, color = Color.LightGray, shape = shape)
            .clickable {
                onItemClicked(sparePart)
            }
    ) {

        CardImage(
            isSold = sparePart.isSold,
            postId = sparePart.postId,
            primeImage = sparePart.primeImage
        )

        Text(
            text = sparePart.title,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = spacing.medium, vertical = spacing.small),
            style  = MaterialTheme.typography.body2
        )

        Text(
            text = "Price : ${stringResource(id = R.string.rupee)}${sparePart.price}",
            fontWeight = FontWeight.Bold,
            color = Color.DarkGreen,
            modifier = Modifier.padding(horizontal = spacing.medium, vertical = spacing.extraSmall),
            style  = MaterialTheme.typography.body2
        )
    }

}