package com.example.salvageauctionindia.ui.sell_screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.salvageauctionindia.ui.common_components.MyTextField
import com.example.salvageauctionindia.ui.sell_screen.SellViewModel


@Composable
fun VehicleAddress(
    viewModel: SellViewModel
) {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        MyTextField(
            text = viewModel.vehicleStreet,
            placeholderText = "Street",
            errorText = "Please Enter Street Address",
            minCharactersConstraint = 0
        )

        MyTextField(
            text = viewModel.vehicleCity,
            error = viewModel.vehicleCityError,
            placeholderText = "City *",
            errorText = "Please Enter Vehicle City",
            minCharactersConstraint = 3,
            maxCharacterConstraint = 100
        )

        MyTextField(
            text = viewModel.vehicleState,
            placeholderText = "State",
            errorText = "",
            minCharactersConstraint = 0
        )
    }

}