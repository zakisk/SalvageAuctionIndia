package com.example.salvageauctionindia.ui.sell_screen

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.salvageauctionindia.data.remote.dtos.enums.VehicleType
import com.example.salvageauctionindia.ui.app_navigation.Screen
import com.example.salvageauctionindia.ui.sell_screen.components.SparePartForm
import com.example.salvageauctionindia.ui.sell_screen.components.VehicleForm
import com.example.salvageauctionindia.util.Constants.OPERATION_VEHICLE

@Composable
fun SellScreen(
    viewModel: SellViewModel = hiltViewModel(),
    type: String,
    navController: NavHostController,
    selectImage: () -> Unit,
    images: MutableState<List<Uri>>,
    isNetworkAvailable : MutableState<Boolean>
) {

    val context = LocalContext.current

    val onSubmit: (Int) -> Unit = {
        if (it == OPERATION_VEHICLE) {
            viewModel.addVehicle(context, images.value, type)
        } else {
            viewModel.addSparePart(context, images.value)
        }
    }

    val navigate: () -> Unit = {
        images.value = emptyList()
        navController.navigate(Screen.SectionsScreen.route) {
            navController.popBackStack(Screen.SectionsScreen.route, inclusive = true)
        }
    }


    if (type == VehicleType.SPARE_PART.value) {
        SparePartForm(
            viewModel = viewModel,
            selectImage = selectImage,
            images = images,
            onSubmit = onSubmit,
            isNetworkAvailable = isNetworkAvailable,
            navigate = navigate
        )
    } else {
        VehicleForm(
            viewModel = viewModel,
            type = type,
            selectImage = selectImage,
            images = images,
            onSubmit = onSubmit,
            isNetworkAvailable = isNetworkAvailable,
            navigate = navigate
        )
    }
}