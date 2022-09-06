package com.example.salvageauctionindia.ui.sell_screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.example.salvageauctionindia.data.remote.dtos.enums.DocumentStatus
import com.example.salvageauctionindia.data.remote.dtos.enums.FuelType
import com.example.salvageauctionindia.data.remote.dtos.enums.TransmissionType
import com.example.salvageauctionindia.ui.common_components.MyTextField
import com.example.salvageauctionindia.ui.sell_screen.SellViewModel
import java.util.*


@Composable
fun VehicleBasicInformation(
    viewModel: SellViewModel
) {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        MyTextField(
            text = viewModel.price,
            error = viewModel.priceError,
            placeholderText = "Price *",
            errorText = "Please Enter Valid Price",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        MyTextField(
            text = viewModel.brandName,
            error = viewModel.brandNameError,
            placeholderText = "Brand *",
            errorText = "Please Enter Brand Name",
            minCharactersConstraint = 3
        )

        MyTextField(
            text = viewModel.title,
            error = viewModel.titleError,
            placeholderText = "Title *",
            errorText = "Please Enter Title Name",
            minCharactersConstraint = 7,
            maxCharacterConstraint = 100
        )

        MyTextField(
            text = viewModel.year,
            error = viewModel.yearError,
            placeholderText = "Year *",
            errorText = "Please Enter valid Year",
            isNumber = true,
            minNumberConstraint = 1900,
            maxNumberConstraint = Calendar.getInstance().get(Calendar.YEAR),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        MyTextField(
            text = viewModel.kmDriven,
            error = viewModel.kmDrivenError,
            placeholderText = "Kilometers Driven*",
            errorText = "Please Enter Kilometers",
            isNumber = true,
            maxNumberConstraint = 999999,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        MyTextField(
            text = viewModel.vehicleNo,
            error = viewModel.vehicleNoError,
            placeholderText = "Vehicle No *",
            errorText = "Please Enter Vehicle No",
            minCharactersConstraint = 7,
            maxCharacterConstraint = 12
        )

        Label(label = "Document Status *")
        MyDropdownMenu(
            expanded = viewModel.documentStatusExpanded,
            selected = viewModel.documentStatus,
            items = DocumentStatus.values().map { it.value }
        )

        Label(label = "Transmission Type *")
        MyDropdownMenu(
            expanded = viewModel.transmissionTypeExpanded,
            selected = viewModel.transmissionType,
            items = TransmissionType.values().map { it.value }
        )

        Label(label = "Fuel Type *")
        MyDropdownMenu(
            expanded = viewModel.fuelTypeExpanded,
            selected = viewModel.fuelType,
            items = FuelType.values().map { it.value }
        )

        Label(label = "Other Detail about vehicle")
        MyTextField(
            text = viewModel.otherDetails,
            placeholderText = "Enter Description",
            errorText = "",
            minCharactersConstraint = 0,
            maxCharacterConstraint = 4096,
            singleLine = false
        )

    }

}