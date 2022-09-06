package com.example.salvageauctionindia.ui.spare_part_detail_screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.salvageauctionindia.R
import com.example.salvageauctionindia.domain.model.SparePart
import com.example.salvageauctionindia.ui.vehicle_detail_screen.components.Description
import com.example.salvageauctionindia.ui.vehicle_detail_screen.components.DetailRecordRow
import com.example.salvageauctionindia.ui.vehicle_detail_screen.components.Div


@Composable
fun SparePartSpecificationCard(
    modifier: Modifier,
    sparePart: SparePart,
    shape: CornerBasedShape
) {

    Card(
        modifier = modifier,
        shape = shape,
        backgroundColor = MaterialTheme.colors.background,
        elevation = 4.dp
    ) {

        Column {

            DetailRecordRow(
                modifier = modifier,
                headText = "price",
                valText = "${stringResource(id = R.string.rupee)} ${sparePart.price}"
            )

            Div()

            DetailRecordRow(
                modifier = modifier,
                headText = "Brand Name",
                valText = sparePart.brandName
            )

            Div()

            DetailRecordRow(
                modifier = modifier,
                headText = "Address",
                valText = sparePart.address
            )

            Div()

            Description(description = sparePart.description ?: "No Details Available")

        }

    }

}