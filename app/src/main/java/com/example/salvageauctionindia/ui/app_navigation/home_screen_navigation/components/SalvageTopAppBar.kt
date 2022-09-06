package com.example.salvageauctionindia.ui.app_navigation.home_screen_navigation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun SalvageTopAppBar(
    modifier: Modifier,
    openCloseDrawer: () -> Unit,
    searchWidgetState: SearchWidgetState,
    searchTextState: String,
    onTextChanged: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
    onSearchTriggered: () -> Unit,
    navController: NavHostController
) {

    when (searchWidgetState) {
        SearchWidgetState.CLOSED -> {
            PlainTopAppBar(
                modifier = modifier,
                openCloseDrawer = openCloseDrawer,
                onSearchIconClicked = onSearchTriggered,
                navController = navController
            )
        }

        SearchWidgetState.OPENED -> {
            SearchAppBar(
                text = searchTextState,
                onTextChanged = onTextChanged,
                onCloseClicked = onCloseClicked,
                onSearchClicked = onSearchClicked
            )
        }
    }

}

