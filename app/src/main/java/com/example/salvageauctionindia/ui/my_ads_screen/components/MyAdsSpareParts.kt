package com.example.salvageauctionindia.ui.my_ads_screen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import com.example.salvageauctionindia.domain.model.SparePart
import com.example.salvageauctionindia.ui.app_navigation.Screen
import com.example.salvageauctionindia.ui.common_components.EmptyList
import com.example.salvageauctionindia.ui.theme.Apricot
import com.example.salvageauctionindia.ui.theme.LocalSpacing
import com.example.salvageauctionindia.ui.vehicle_detail_screen.components.Div


@ExperimentalCoilApi
@Composable
fun MyAdsSpareParts(
    viewModel: MyAdsSparePartsViewModel = hiltViewModel(),
    navController: NavHostController,
    saveLastTab: () -> Unit
) {
    val actions = listOf("Spare Part Sold", "Delete Spare Part")
    val context = LocalContext.current
    val state = viewModel.sparePartsState
    val spacing = LocalSpacing.current

    val onItemClicked: (SparePart) -> Unit = {
        saveLastTab()
        navController.navigate(Screen.SparePartDetailScreen.route + "/${it.postId}")
    }

    val onOptionsMenuSelected: (String, SparePart) -> Unit = { action, sparePart ->
        viewModel.onOptionsMenuSelected(context, actions.indexOf(action), sparePart)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.padding(
                start = spacing.small,
                end = spacing.small,
                top = spacing.small,
                bottom = spacing.listBottomSpacing
            )
        ) {
            items(state.value.spareParts) { sparePart ->
                SparePartListItem(
                    sparePart = sparePart,
                    onItemClicked = onItemClicked,
                    actions = actions,
                    onOptionsMenuSelected = onOptionsMenuSelected
                )
                Div()
            }
        }

        if (state.value.spareParts.isEmpty() && !state.value.isLoading) {
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