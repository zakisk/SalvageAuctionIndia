package com.example.salvageauctionindia.ui.app_navigation.home_screen_navigation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.salvageauctionindia.ui.app_navigation.Screen
import com.example.salvageauctionindia.ui.theme.LocalCustomShapes

@Composable
fun SalvageBottomAppBar(
    navController: NavHostController
) {

    val oval = LocalCustomShapes.current.ovalShape

    val modifier = Modifier
        .fillMaxWidth()

    val items = listOf(
        Screen.BottomScreen.BottomSectionsScreen,
        Screen.BottomScreen.BottomMyAdsScreen
    )

    BottomAppBar(
        cutoutShape = oval,
        backgroundColor = MaterialTheme.colors.background,
        modifier = modifier
    ) {

        val currentDestination by navController.currentBackStackEntryAsState()
        val currentRoute = currentDestination?.destination

        items.forEach { screen ->

            BottomNavigationItem(
                label = {
                    Text(
                        text = stringResource(id = screen.strResId),
                        fontWeight = FontWeight.Bold
                    )
                },
                icon = {
                    Icon(
                        painter = painterResource(id = screen.iconId),
                        contentDescription = null
                    )
                },
                selected = currentRoute?.hierarchy?.any { it.route == screen.bottomRoute } == true,
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Gray,
                onClick = {
                    if (screen.bottomRoute != currentRoute?.route) {
                        navController.navigate(screen.route) {
                            if (screen.bottomRoute == Screen.SectionsScreen.route) {
                                navController.popBackStack(Screen.SectionsScreen.route, inclusive = true)
                            }
                        }
                    }
                }
            )

        }

    }


}