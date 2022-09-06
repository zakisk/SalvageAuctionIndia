package com.example.salvageauctionindia.ui.app_navigation.home_screen_navigation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.salvageauctionindia.ui.app_navigation.Screen
import com.example.salvageauctionindia.ui.common_components.Header
import com.example.salvageauctionindia.util.Constants.PARAM_SELL_TYPE
import com.example.salvageauctionindia.R

@Composable
fun PlainTopAppBar(
    modifier: Modifier,
    openCloseDrawer: () -> Unit,
    onSearchIconClicked: () -> Unit,
    navController: NavHostController
) {

    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStack?.destination?.route
    var isShowSearchIcon by remember { mutableStateOf(false) }
    isShowSearchIcon =
        currentRoute == Screen.AuctionVehiclesScreen.route + "/{$PARAM_SELL_TYPE}" ||
                currentRoute == Screen.FinalVehiclesScreen.route + "/{$PARAM_SELL_TYPE}" ||
                currentRoute == Screen.SparePartsScreen.route ||
                currentRoute == Screen.FreshVehiclesScreen.route + "/{$PARAM_SELL_TYPE}"


    TopAppBar(
        title = { Header(modifier = modifier, fontSize = 16.dp, color = Color.Black) },
        navigationIcon = {
            MenuIcon(
                modifier = modifier,
                onClick = {
                    if (currentRoute == Screen.SectionsScreen.route) {
                        openCloseDrawer()
                    } else {
                        navController.navigateUp()
                    }
                },
                imageVector = if (currentRoute != null) {
                    if (currentRoute == Screen.SectionsScreen.route) {
                        Icons.Default.Menu
                    } else {
                        Icons.Default.ArrowBack
                    }
                } else {
                    Icons.Default.Menu
                }
            )
        },
        backgroundColor = MaterialTheme.colors.background,
        actions = {
            if (isShowSearchIcon) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .clickable {
                            onSearchIconClicked()
                        }
                )
            }

            Image(
                painter = painterResource(id = R.drawable.ic_app_logo),
                contentDescription = null,
                colorFilter = ColorFilter.tint(color = Color.Unspecified),
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(24.dp)
            )
        }
    )
}