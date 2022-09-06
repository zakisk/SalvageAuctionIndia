package com.example.salvageauctionindia.ui.my_ads_screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.example.salvageauctionindia.R
import com.example.salvageauctionindia.domain.model.Vehicle
import com.example.salvageauctionindia.ui.common_components.CardImage
import com.example.salvageauctionindia.ui.theme.DarkGreen
import com.example.salvageauctionindia.ui.theme.LocalCustomShapes
import com.example.salvageauctionindia.ui.theme.LocalSpacing

@ExperimentalCoilApi
@Composable
fun VehicleListItem(
    vehicle: Vehicle,
    onItemClicked: (Vehicle) -> Unit,
    actions: List<String>,
    onOptionsMenuSelected: (String, Vehicle) -> Unit
) {

    val smallSpacing = LocalSpacing.current.small
    val shape = LocalCustomShapes.current.smallShape
    val expanded = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(smallSpacing)
            .clip(shape)
            .clickable {
                onItemClicked(vehicle)
            }
    ) {

        CardImage(
            isSold = vehicle.isSold,
            isTokenized = vehicle.isTokenized,
            postId = vehicle.postId,
            primeImage = vehicle.primeImage,
            height = 130.dp,
            contentScale = ContentScale.FillWidth,
            fraction = 0.3f,
            shape = shape
        )


        Box(
            modifier = Modifier
                .padding(smallSpacing)
                .fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.align(Alignment.TopStart)
            ) {

                val caption = MaterialTheme.typography.caption

                Text(text = vehicle.title, style = caption)

                Text(text = vehicle.brandName, style = caption)

                Text(text = vehicle.fuelType, style = caption)

                Text(text = vehicle.vehicleNo, style = caption)

                Row(modifier = Modifier) {
                    Icon(
                        painterResource(id = R.drawable.ic_location),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )

                    Text(text = "  ${vehicle.vehicleCity}", style = caption)
                }

                Text(
                    text =
                    "Price : ${stringResource(id = R.string.rupee)}${vehicle.finalPrice ?: vehicle.userVehiclePrice ?: vehicle.auctionStartingPrice}",
                    style = caption,
                    color = Color.DarkGreen
                )

                if (!vehicle.isApproved) {
                    Text(
                        text = "NOT APPROVED",
                        style = MaterialTheme.typography.caption,
                        color = MaterialTheme.colors.error
                    )
                }

            }

            Box(modifier = Modifier.align(Alignment.TopEnd)) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable {
                            expanded.value = true
                        }
                )

                DropdownMenuContainer(
                    expanded = expanded,
                    onClickItem = {
                        onOptionsMenuSelected(it, vehicle)
                    },
                    items = actions
                )
            }


        }

    }


}
