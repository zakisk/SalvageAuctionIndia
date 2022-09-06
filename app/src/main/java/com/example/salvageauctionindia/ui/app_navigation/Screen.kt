package com.example.salvageauctionindia.ui.app_navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.salvageauctionindia.R


sealed class Screen(val route : String) {

    object LoginScreen : Screen("login")

    object VerifyOtpScreen : Screen("verify_otp")

    object SectionsScreen : Screen("sections")

    object AuctionVehiclesScreen : Screen("auction_vehicles")

    object FinalVehiclesScreen : Screen("final_vehicles")

    object SparePartsScreen : Screen("spare_parts")

    object FreshVehiclesScreen : Screen("fresh_vehicles")

    object BankSeizedVehiclesScreen : Screen("bank_seized_vehicles")

    object VehicleDetailScreen : Screen("vehicle_detail")

    object SparePartDetailScreen : Screen("spare_part_detail")

    object CarouselScreen : Screen("carousel")

    object SellScreen : Screen("sell")

    object CategoryScreen : Screen("category")

    object AboutUsScreen : Screen("about_us")

    object ContactUsScreen : Screen("contact_us")

    object TermsAndConditionsScreen : Screen("terms_and_conditions")

    sealed class BottomScreen(
        val bottomRoute : String,
        @StringRes val strResId : Int,
        @DrawableRes val iconId : Int
        ) : Screen(bottomRoute) {

        object BottomSectionsScreen : BottomScreen(SectionsScreen.route, R.string.home, R.drawable.ic_home)

        object BottomMyAdsScreen : BottomScreen("my_ads", R.string.my_ads, R.drawable.ic_my_ads)

    }

}
