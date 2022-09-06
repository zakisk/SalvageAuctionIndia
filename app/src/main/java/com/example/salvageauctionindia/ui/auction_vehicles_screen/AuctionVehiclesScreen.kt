package com.example.salvageauctionindia.ui.auction_vehicles_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import com.example.salvageauctionindia.domain.model.Vehicle
import com.example.salvageauctionindia.ui.app_navigation.Screen
import com.example.salvageauctionindia.ui.common_components.EmptyList
import com.example.salvageauctionindia.ui.common_components.GetVehiclesViewModel
import com.example.salvageauctionindia.ui.common_components.VehicleCardItem
import com.example.salvageauctionindia.ui.theme.Apricot


@ExperimentalCoilApi
@Composable
fun AuctionVehiclesScreen(
    viewModel: GetVehiclesViewModel = hiltViewModel(),
    navController: NavHostController,
    searchedText: State<String>
) {

    val state = viewModel.state
    searchedText.value.let {
        viewModel.onSearch(it)
    }

    val onItemClicked: (Vehicle) -> Unit = {
        navController.navigate(Screen.VehicleDetailScreen.route + "/${it.postId}")
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.padding(bottom = 96.dp)) {
            items(viewModel.vehicles.value) { vehicle ->
                VehicleCardItem(vehicle = vehicle, onItemClicked = onItemClicked)
            }
        }

        if (state.value.vehicles.isEmpty() && !state.value.isLoading) {
            EmptyList()
        }

        if (state.value.isLoading) {
            CircularProgressIndicator(color = Apricot, modifier = Modifier.align(Alignment.Center))
        }

        state.value.error?.let {
            Text(
                text = it,
                color = MaterialTheme.colors.error,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}