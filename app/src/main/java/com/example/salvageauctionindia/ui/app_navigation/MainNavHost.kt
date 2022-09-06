package com.example.salvageauctionindia.ui.app_navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import coil.annotation.ExperimentalCoilApi
import com.example.salvageauctionindia.ui.MainActivityViewModel
import com.example.salvageauctionindia.ui.about_us_screen.AboutUsScreen
import com.example.salvageauctionindia.ui.auction_vehicles_screen.AuctionVehiclesScreen
import com.example.salvageauctionindia.ui.bank_seized_vehicles_screen.BankSeizedVehiclesScreen
import com.example.salvageauctionindia.ui.carousel_screen.CarouselScreen
import com.example.salvageauctionindia.ui.category_screen.CategoryScreen
import com.example.salvageauctionindia.ui.contact_us_screen.ContactUsScreen
import com.example.salvageauctionindia.ui.final_vehicles_screen.FinalVehiclesScreen
import com.example.salvageauctionindia.ui.fresh_vehicles_screen.FreshVehiclesScreen
import com.example.salvageauctionindia.ui.login_screen.LoginScreen
import com.example.salvageauctionindia.ui.my_ads_screen.MyAdsScreen
import com.example.salvageauctionindia.ui.sections_screen.SectionsScreen
import com.example.salvageauctionindia.ui.sell_screen.SellScreen
import com.example.salvageauctionindia.ui.spare_part_detail_screen.SparePartDetailScreen
import com.example.salvageauctionindia.ui.spare_parts_screen.SparePartsScreen
import com.example.salvageauctionindia.ui.terms_and_conditions_screen.TermsAndConditionScreen
import com.example.salvageauctionindia.ui.vehicle_detail_screen.VehicleDetailScreen
import com.example.salvageauctionindia.ui.verify_otp_screen.VerifyOTPScreen
import com.example.salvageauctionindia.util.Constants.NAV_KEY_POST_ID
import com.example.salvageauctionindia.util.Constants.PARAM_SELL_TYPE
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalPagerApi
@ExperimentalCoilApi
@Composable
fun MainNavHost(
    navController: NavHostController,
    viewModel: MainActivityViewModel,
    startDestination: String,
    onVerified: @Composable () -> Unit,
    verifyOtp: (String) -> Unit,
    sendOtp: (String) -> Unit,
    selectImage: () -> Unit,
    searchedText: State<String>
) {

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable(Screen.LoginScreen.route) {
            LoginScreen(navController = navController, sendOtp = sendOtp)
        }

        composable(
            Screen.VerifyOtpScreen.route + "/{mobileNo}",
            arguments = listOf(navArgument("mobileNo") { type = NavType.StringType })
        ) {
            it.arguments?.getString("mobileNo")?.let { mobileNo ->
                VerifyOTPScreen(
                    mobileNo = mobileNo,
                    onVerified = onVerified,
                    verifyOtp = verifyOtp,
                    isResendOtp = viewModel.isResendOtp,
                    isVerified = viewModel.isVerified,
                    sendOtp = sendOtp,
                    errorMessage = viewModel.errorMsg,
                    isOtpInvalid = viewModel.isOtpInvalid,
                    isOtpReceived = viewModel.isOtpReceived
                )
            }
        }

        composable(Screen.SectionsScreen.route) {
            SectionsScreen(navController = navController)
        }

        composable(Screen.AuctionVehiclesScreen.route + "/{$PARAM_SELL_TYPE}") {
            AuctionVehiclesScreen(navController = navController, searchedText = searchedText)
        }

        composable(Screen.FinalVehiclesScreen.route + "/{$PARAM_SELL_TYPE}") {
            FinalVehiclesScreen(navController = navController, searchedText = searchedText)
        }

        composable(Screen.SparePartsScreen.route) {
            SparePartsScreen(navController = navController, searchedText = searchedText)
        }

        composable(Screen.FreshVehiclesScreen.route + "/{$PARAM_SELL_TYPE}") {
            FreshVehiclesScreen(navController = navController, searchedText = searchedText)
        }

        composable(Screen.BankSeizedVehiclesScreen.route + "/{$PARAM_SELL_TYPE}") {
            BankSeizedVehiclesScreen(navController = navController, searchedText = searchedText)
        }

        composable(
            Screen.VehicleDetailScreen.route + "/{$NAV_KEY_POST_ID}",
            deepLinks = listOf(navDeepLink { uriPattern = "https://salvageauctionindia.com/vehicles/{$NAV_KEY_POST_ID}" })
        ) {
            VehicleDetailScreen(navController = navController)
        }

        composable(
            Screen.SparePartDetailScreen.route + "/{$NAV_KEY_POST_ID}",
            deepLinks = listOf(navDeepLink { uriPattern = "https://salvageauctionindia.com/spare-parts/{$NAV_KEY_POST_ID}" })
        ) {
            SparePartDetailScreen(navController = navController)
        }

        composable(
            Screen.CarouselScreen.route + "/{postId}&{imagePrefix}&{noOfImages}",
            arguments = listOf(
                navArgument("postId") { type = NavType.StringType },
                navArgument("imagePrefix") { type = NavType.StringType },
                navArgument("noOfImages") { type = NavType.IntType }
            )
        ) {
            CarouselScreen(
                postId = it.arguments?.getString("postId") ?: "N/A",
                imagePrefix = it.arguments?.getString("imagePrefix") ?: "N/A",
                noOfImages = it.arguments?.getInt("noOfImages", 0) ?: 0
            )

        }

        composable(Screen.CategoryScreen.route) {
            CategoryScreen(navController = navController)
        }

        composable(
            Screen.SellScreen.route + "/{type}",
            arguments = listOf(navArgument("type") { type = NavType.StringType })
        ) {
            it.arguments?.getString("type")?.let { type ->
                SellScreen(
                    type = type,
                    navController = navController,
                    selectImage = selectImage,
                    images = viewModel.images,
                    isNetworkAvailable = viewModel.isNetworkAvailable
                )
            }
        }

        composable(Screen.AboutUsScreen.route) {
            AboutUsScreen()
        }

        composable(Screen.ContactUsScreen.route) {
            ContactUsScreen()
        }

        composable(Screen.BottomScreen.BottomMyAdsScreen.route) {
            MyAdsScreen(navController = navController)
        }

        composable(Screen.TermsAndConditionsScreen.route) {
            TermsAndConditionScreen()
        }
    }

}
