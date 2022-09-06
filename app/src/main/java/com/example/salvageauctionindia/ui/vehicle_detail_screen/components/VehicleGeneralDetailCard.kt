package com.example.salvageauctionindia.ui.vehicle_detail_screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.salvageauctionindia.domain.model.Vehicle
import com.example.salvageauctionindia.ui.theme.LocalSpacing


@Composable
fun VehicleGeneralDetailCard(
    modifier: Modifier,
    vehicle: Vehicle,
    shape: CornerBasedShape
) {
    val spacing = LocalSpacing.current.large

    Card(
        modifier = modifier.padding(bottom = spacing),
        shape = shape,
        backgroundColor = MaterialTheme.colors.background,
        elevation = 4.dp
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {

                DetailRecordRow(
                    modifier = modifier,
                    headText = "Vehicle Address :- ",
                    valText = vehicle.vehicleStreet ?: "N/A"
                )

                Div()

                DetailRecordRow(
                    modifier = modifier,
                    headText = "Approval Time :- ",
                    valText = "2 to 20 Working Days"
                )

                Div()

                Description(vehicle.otherDetails ?: "No Details Available")
        }
    }

}