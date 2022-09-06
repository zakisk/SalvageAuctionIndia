package com.example.salvageauctionindia.ui.category_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.salvageauctionindia.R
import com.example.salvageauctionindia.data.remote.dtos.enums.VehicleType
import com.example.salvageauctionindia.ui.app_navigation.Screen
import com.example.salvageauctionindia.ui.category_screen.components.CategoryItem
import com.example.salvageauctionindia.ui.vehicle_detail_screen.components.Div


@Composable
fun CategoryScreen(
    navController: NavHostController
) {

    Column(modifier = Modifier.fillMaxWidth()) {

        CategoryItem(
            resId = R.drawable.ic_bike,
            title = VehicleType.BIKE.value
        ) {
            navController.navigate(
                Screen.SellScreen.route + "/${VehicleType.BIKE.value}"
            )
        }

        Div()

        CategoryItem(resId = R.drawable.ic_car,
            title = VehicleType.CAR.value
        ) {
            navController.navigate(
                Screen.SellScreen.route + "/${VehicleType.CAR.value}"
            )
        }

        Div()

        CategoryItem(
            resId = R.drawable.ic_truck,
            title = VehicleType.TRUCK.value
        ) {
            navController.navigate(
                Screen.SellScreen.route + "/${VehicleType.TRUCK.value}"
            )
        }

        Div()

        CategoryItem(
            resId = R.drawable.ic_car_parts,
            title = VehicleType.SPARE_PART.value
        ) {
            navController.navigate(
                Screen.SellScreen.route + "/${VehicleType.SPARE_PART.value}"
            )
        }

        Div()
    }

}