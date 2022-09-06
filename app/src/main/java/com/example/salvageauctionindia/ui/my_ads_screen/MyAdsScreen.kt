package com.example.salvageauctionindia.ui.my_ads_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import com.example.salvageauctionindia.ui.my_ads_screen.components.MyAdsSpareParts
import com.example.salvageauctionindia.ui.my_ads_screen.components.MyAdsVehicles
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch


@ExperimentalPagerApi
@ExperimentalCoilApi
@Composable
fun MyAdsScreen(
    viewModel: MyAdsViewModel = hiltViewModel(),
    navController: NavHostController
) {


    val tabs = listOf("Vehicles", "Spare Parts")
    val saveLastTab: () -> Unit = { viewModel.saveLastTab() }
    val state = rememberPagerState()
    val scope = rememberCoroutineScope()
    viewModel.selected.value = state.currentPage

    Column {
        TabRow(
            selectedTabIndex = viewModel.selected.value,
            backgroundColor = Color.Transparent,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[viewModel.selected.value])
                )
            }
        ) {
            tabs.forEachIndexed { index, tab ->
                Tab(
                    selected = viewModel.selected.value == index,
                    onClick = {
                        viewModel.selected.value = index
                        scope.launch { state.scrollToPage(index) }
                    },
                    text = { Text(text = tab) }
                )
            }
        }



        HorizontalPager(
            state = state,
            count = tabs.size
        ) { page ->
            if (page == 0) {
                MyAdsVehicles(
                    navController = navController,
                    saveLastTab = saveLastTab
                )
            } else {
                MyAdsSpareParts(
                    navController = navController,
                    saveLastTab = saveLastTab
                )
            }
        }
    }

}