package com.example.salvageauctionindia.ui.vehicle_detail_screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.salvageauctionindia.R
import com.example.salvageauctionindia.data.remote.dtos.enums.SellType
import com.example.salvageauctionindia.domain.model.Vehicle

@Composable
fun VehicleSpecificationCard(
    modifier: Modifier,
    vehicle: Vehicle,
    shape: CornerBasedShape
) {

    Card(
        modifier = modifier,
        shape = shape,
        backgroundColor = MaterialTheme.colors.background,
        elevation = 4.dp
    ) {

        Column {

            var headText = ""
            var valText = ""

            when (vehicle.sellType) {
                SellType.AUCTION.value -> {
                    headText = "Auction Starting Price"
                    valText = "${vehicle.auctionStartingPrice ?: 0}"
                }
                SellType.FINAL.value -> {
                    headText = "Final Price"
                    valText = "${vehicle.finalPrice ?: 0}"
                }
                SellType.FRESH.value, SellType.BANK_SEIZE.value -> {
                    headText = "Price"
                    valText = "${vehicle.userVehiclePrice ?: 0}"
                }
            }

            DetailRecordRow(
                modifier = modifier,
                headText = headText,
                valText = "${stringResource(id = R.string.rupee)} $valText"
            )

            Div()

            DetailRecordRow(
                modifier = modifier,
                headText = "Brand Name",
                valText = vehicle.brandName
            )

            Div()

            DetailRecordRow(
                modifier = modifier,
                headText = "Variant",
                valText = vehicle.year
            )

            Div()

            DetailRecordRow(
                modifier = modifier,
                headText = "Model",
                valText = vehicle.title
            )

            Div()

            DetailRecordRow(
                modifier = modifier,
                headText = "Fuel Type",
                valText = vehicle.fuelType
            )

            Div()

            DetailRecordRow(
                modifier = modifier,
                headText = "RTO",
                valText = vehicle.vehicleNo.substring(0..3)
            )

            Div()

            DetailRecordRow(
                modifier = modifier,
                headText = "Document Status",
                valText = vehicle.documentStatus
            )

            Div()

            DetailRecordRow(
                modifier = modifier,
                headText = "Transmission",
                valText = vehicle.transmissionType
            )

        }
    }

}