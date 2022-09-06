package com.example.salvageauctionindia.ui.spare_parts_screen

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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import com.example.salvageauctionindia.domain.model.SparePart
import com.example.salvageauctionindia.ui.app_navigation.Screen
import com.example.salvageauctionindia.ui.common_components.EmptyList
import com.example.salvageauctionindia.ui.spare_parts_screen.components.SparePartCardItem
import com.example.salvageauctionindia.ui.theme.Apricot
import com.example.salvageauctionindia.ui.theme.LocalSpacing

@ExperimentalCoilApi
@Composable
fun SparePartsScreen(
    viewModel: SparePartsViewModel = hiltViewModel(),
    navController: NavHostController,
    searchedText: State<String>
) {

    val spacing = LocalSpacing.current
    val state = viewModel.state
    searchedText.value.let {
        viewModel.onSearch(it)
    }

    val onItemClicked: (SparePart) -> Unit = {
        navController.navigate(Screen.SparePartDetailScreen.route + "/${it.postId}")
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.padding(bottom = spacing.listBottomSpacing)) {
            items(viewModel.spareParts.value) { sparePart ->
                SparePartCardItem(sparePart = sparePart, onItemClicked = onItemClicked)
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