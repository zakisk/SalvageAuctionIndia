package com.example.salvageauctionindia.ui.common_components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.example.salvageauctionindia.R
import com.example.salvageauctionindia.data.remote.dtos.enums.SellType
import com.example.salvageauctionindia.domain.model.Vehicle
import com.example.salvageauctionindia.ui.theme.DarkGreen
import com.example.salvageauctionindia.ui.theme.LocalCustomShapes
import com.example.salvageauctionindia.ui.theme.LocalSpacing
import com.example.salvageauctionindia.util.dpToSp

@ExperimentalCoilApi
@Composable
fun VehicleCardItem(
    vehicle: Vehicle,
    onItemClicked: (Vehicle) -> Unit
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
                onItemClicked(vehicle)
            }
    ) {

        CardImage(
            isSold = vehicle.isSold,
            isTokenized = vehicle.isTokenized,
            postId = vehicle.postId,
            primeImage = vehicle.primeImage
        )

        Text(
            text = vehicle.title,
            fontWeight = FontWeight.ExtraBold,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(horizontal = spacing.medium, vertical = spacing.small)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = spacing.medium, vertical = spacing.extraSmall),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(vehicle.vehicleNo.substring(0..3), fontSize = dpToSp(dp = 16.dp), fontWeight = FontWeight.Bold)

            Divider(modifier = Modifier.width(1.dp), thickness = 16.dp)

            Text(vehicle.fuelType, fontSize = dpToSp(dp = 16.dp), fontWeight = FontWeight.Bold)

            Divider(modifier = Modifier.width(1.dp), thickness = 16.dp)

            Text(vehicle.vehicleCity, fontSize = dpToSp(dp = 16.dp), fontWeight = FontWeight.Bold)

            Divider(modifier = Modifier.width(1.dp), thickness = 16.dp)

            Text(vehicle.documentStatus, fontSize = dpToSp(dp = 16.dp), fontWeight = FontWeight.Bold)

        }

        val price = when(vehicle.sellType) {
            SellType.AUCTION.value -> "${vehicle.auctionStartingPrice ?: 0}"
            SellType.FINAL.value -> "${vehicle.finalPrice ?: 0}"
            SellType.FRESH.value, SellType.BANK_SEIZE.value -> "${vehicle.userVehiclePrice ?: 0}"
            else -> "0"
        }

        Text(
            text =
            "Price : ${stringResource(id = R.string.rupee)}$price",
            fontWeight = FontWeight.Bold,
            color = Color.DarkGreen,
            modifier = Modifier.padding(
                horizontal = spacing.medium,
                vertical = spacing.extraSmall
            ),
            style = MaterialTheme.typography.body2,
        )
    }

}