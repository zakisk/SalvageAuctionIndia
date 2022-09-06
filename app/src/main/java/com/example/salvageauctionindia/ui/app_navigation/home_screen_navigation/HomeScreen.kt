package com.example.salvageauctionindia.ui.app_navigation.home_screen_navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import com.example.salvageauctionindia.domain.workers.WorkerStarter
import com.example.salvageauctionindia.ui.MainActivityViewModel
import com.example.salvageauctionindia.ui.app_navigation.MainNavHost
import com.example.salvageauctionindia.ui.app_navigation.Screen
import com.example.salvageauctionindia.ui.app_navigation.home_screen_navigation.components.*
import com.example.salvageauctionindia.ui.theme.LocalSpacing
import com.example.salvageauctionindia.util.Constants.NAV_KEY_POST_ID
import com.example.salvageauctionindia.util.Constants.USER_ID
import com.example.salvageauctionindia.util.Constants.USER_NAME
import com.example.salvageauctionindia.util.SharedPreferencesUtil
import com.example.salvageauctionindia.util.isNotNull
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@ExperimentalCoilApi
@Composable
fun HomeScreen(
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
    mainActivityViewModel: MainActivityViewModel,
    selectImage: () -> Unit,
    sendOtp: (String) -> Unit,
    verifyOtp: (String) -> Unit,
    onExit: () -> Unit,
    requestPermissions: () -> Unit
) {
    val spacing = LocalSpacing.current.small
    val defaultModifier = Modifier
        .padding(spacing)

    val openCloseDrawer: () -> Unit = {
        if (scaffoldState.drawerState.isClosed) {
            scope.launch { scaffoldState.drawerState.open() }
        } else {
            scope.launch { scaffoldState.drawerState.close() }
        }
    }

    val navController = rememberNavController()

    val context = LocalContext.current
    var startDestination by remember {
        mutableStateOf(
            if (mainActivityViewModel.user.value == null) Screen.LoginScreen.route
            else Screen.SectionsScreen.route
        )
    }

    val currentDestination by navController.currentBackStackEntryAsState()
    val currentRoute = currentDestination?.destination?.route
    val onVerified: @Composable () -> Unit = {
        startDestination = Screen.SectionsScreen.route
        val pref = SharedPreferencesUtil(context)
        pref.put(USER_ID, mainActivityViewModel.user.value?.uid ?: "N/A")
        WorkerStarter(context).startAddNotificationTokenWorker()
        requestPermissions()
    }

    val onLogout: () -> Unit = {
        startDestination = Screen.LoginScreen.route
        mainActivityViewModel.user.value = FirebaseAuth.getInstance().currentUser
    }

    val userName = SharedPreferencesUtil(context).get<String>(USER_NAME) ?: "N/A"
    val searchTextState by homeScreenViewModel.searchTextState
    val searchWidgetState by homeScreenViewModel.searchWidgetState
    navController.addOnDestinationChangedListener { _, _, _ ->
        mainActivityViewModel.images.value = emptyList()
        homeScreenViewModel.updateSearchWidgetState(SearchWidgetState.CLOSED)
    }


    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            if (mainActivityViewModel.user.value.isNotNull()) {
                SalvageTopAppBar(
                    modifier = defaultModifier,
                    openCloseDrawer = openCloseDrawer,
                    searchTextState = searchTextState,
                    searchWidgetState = searchWidgetState,
                    onTextChanged = {
                        homeScreenViewModel.updateSearchTextState(it)
                    },
                    onCloseClicked = {
                        homeScreenViewModel.updateSearchTextState("")
                        homeScreenViewModel.updateSearchWidgetState(SearchWidgetState.CLOSED)
                    },
                    onSearchClicked = { },
                    onSearchTriggered = {
                        homeScreenViewModel.updateSearchWidgetState(SearchWidgetState.OPENED)
                    },
                    navController = navController
                )
            }
        },
        bottomBar = {
            if (mainActivityViewModel.user.value.isNotNull() &&
                currentRoute != Screen.VehicleDetailScreen.route + "/{$NAV_KEY_POST_ID}" &&
                currentRoute != Screen.SparePartDetailScreen.route + "/{${NAV_KEY_POST_ID}}" &&
                currentRoute != Screen.SellScreen.route + "/{type}" &&
                currentRoute != Screen.CarouselScreen.route + "/{postId}&{imagePrefix}&{noOfImages}") {
                SalvageBottomAppBar(navController = navController)
            }
        },
        floatingActionButton = {
            if (mainActivityViewModel.user.value.isNotNull() &&
                currentRoute != Screen.VehicleDetailScreen.route + "/{$NAV_KEY_POST_ID}" &&
                currentRoute != Screen.SparePartDetailScreen.route + "/{${NAV_KEY_POST_ID}}" &&
                currentRoute != Screen.SellScreen.route + "/{type}" &&
                currentRoute != Screen.CarouselScreen.route + "/{postId}&{imagePrefix}&{noOfImages}") {
                SalvageFloatingActionButton {
                    navController.navigate(Screen.CategoryScreen.route)
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        drawerContent = {
            if (mainActivityViewModel.user.value.isNotNull()) {
                SalvageDrawer(
                    userName = userName,
                    onLogout = onLogout,
                    navController = navController,
                    openCloseDrawer = openCloseDrawer
                )
            }
        },
        drawerGesturesEnabled = currentRoute == Screen.SectionsScreen.route
    ) {

        MainNavHost(
            navController = navController,
            viewModel = mainActivityViewModel,
            startDestination = startDestination,
            onVerified = onVerified,
            verifyOtp = verifyOtp,
            sendOtp = sendOtp,
            selectImage = selectImage,
            searchedText = homeScreenViewModel.searchTextState
        )

        if (!mainActivityViewModel.isNetworkAvailable.value) {
            NetworkNotAvailableDialog(onExit = onExit)
        }

    }

}