package com.example.salvageauctionindia.ui.sections_screen

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.salvageauctionindia.R
import com.example.salvageauctionindia.data.remote.dtos.enums.SellType
import com.example.salvageauctionindia.ui.app_navigation.Screen
import com.example.salvageauctionindia.ui.sections_screen.components.SectionButton
import com.example.salvageauctionindia.ui.theme.*

@Composable
fun SectionsScreen(
    navController: NavHostController
) {

    val navigate: (String) -> Unit = {
        navController.navigate(it)
    }

    val screenWidth = LocalConfiguration.current.screenWidthDp

    val horizontal = ((screenWidth - 320) / 4).dp

    val vertical = LocalSpacing.current.medium

    Column(
        Modifier
            .fillMaxWidth()
    ) {

        val modifier = Modifier.padding(horizontal = horizontal, vertical = vertical)

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SectionButton(
                modifier = modifier,
                iconId = R.drawable.ic_car_auction,
                color = PrussianBlue,
                string1Id = R.string.auction,
                string2Id = R.string.vehicle,
                navigate = { navigate(Screen.AuctionVehiclesScreen.route + "/${SellType.AUCTION.value}") }
            )

            SectionButton(
                modifier = modifier,
                iconId = R.drawable.ic_sign_10,
                color = Mahogany,
                string1Id = R.string.final_,
                string2Id = R.string.vehicle,
                navigate = { navigate(Screen.FinalVehiclesScreen.route + "/${SellType.FINAL.value}") }
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SectionButton(
                modifier = modifier,
                iconId = R.drawable.ic_spare_parts,
                color = PaoloVeroneseGreen,
                string1Id = R.string.spare,
                string2Id = R.string.part,
                navigate = { navigate(Screen.SparePartsScreen.route) }
            )

            SectionButton(
                modifier = modifier,
                iconId = R.drawable.ic_fresh_cars,
                color = RedSalsa,
                string1Id = R.string.fresh,
                string2Id = R.string.vehicle,
                navigate = { navigate(Screen.FreshVehiclesScreen.route + "/${SellType.FRESH.value}") }
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SectionButton(
                modifier = modifier,
                iconId = R.drawable.ic_bank_seized,
                color = EarthYellow,
                string1Id = R.string.bank_seized,
                string2Id = R.string.vehicle,
                navigate = { navigate(Screen.FreshVehiclesScreen.route + "/${SellType.BANK_SEIZE.value}") }
            )
        }
    }

}