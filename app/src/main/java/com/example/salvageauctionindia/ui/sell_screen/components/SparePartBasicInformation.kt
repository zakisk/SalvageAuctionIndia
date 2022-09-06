package com.example.salvageauctionindia.ui.sell_screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.example.salvageauctionindia.ui.common_components.MyTextField
import com.example.salvageauctionindia.ui.sell_screen.SellViewModel


@Composable
fun SparePartBasicInformation(
    viewModel: SellViewModel
) {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        MyTextField(
            text = viewModel.sparePrice,
            error = viewModel.sparePriceError,
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
            maxCharacterConstraint = 20
        )

        MyTextField(
            text = viewModel.spareAddress,
            error = viewModel.spareAddressError,
            placeholderText = "Address *",
            errorText = "Please Enter Address",
            minCharactersConstraint = 10,
            maxCharacterConstraint = 200
        )

        Label(label = "Other Detail about spare part")
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