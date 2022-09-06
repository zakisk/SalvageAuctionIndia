package com.example.salvageauctionindia.ui.vehicle_detail_screen


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import com.example.salvageauctionindia.data.remote.dtos.enums.SellType
import com.example.salvageauctionindia.domain.share.ShareUtil.callAdmin
import com.example.salvageauctionindia.domain.share.ShareUtil.shareOnWhatsapp
import com.example.salvageauctionindia.ui.app_navigation.Screen
import com.example.salvageauctionindia.ui.theme.*
import com.example.salvageauctionindia.ui.vehicle_detail_screen.components.*
import com.example.salvageauctionindia.util.Constants.BASE_URL
import com.example.salvageauctionindia.util.showToast
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.firebase.auth.FirebaseAuth

@ExperimentalPagerApi
@ExperimentalCoilApi
@Composable
fun VehicleDetailScreen(
    viewModel: GetVehicleViewModel = hiltViewModel(),
    navController: NavHostController
) {

    val state = viewModel.state

    val spacing = LocalSpacing.current
    val shape = LocalCustomShapes.current.mediumShape
    val context = LocalContext.current
    var bottomPadding by remember { mutableStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        state.value.vehicle?.let { vehicle ->
            val userId = FirebaseAuth.getInstance().currentUser?.uid
            val isShowContactButtons = remember { mutableStateOf(userId != state.value.vehicle?.userId) }

            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(bottom = with(LocalDensity.current) { bottomPadding.toDp() })
                    .align(Alignment.TopCenter)
            ) {

                item {
                    CarouselImageItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp)
                            .clickable {
                                navController.navigate(
                                    Screen.CarouselScreen.route +
                                            "/${vehicle.postId}&${vehicle.imagePrefix}&${vehicle.noOfImages}"
                                )
                            },
                        data = "${BASE_URL}get-image/${vehicle.postId}/${vehicle.primeImage}",
                        contentScale = ContentScale.FillHeight
                    )
                }

                item {

                    val modifier = Modifier
                        .fillMaxWidth()
                        .padding(spacing.small)

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Mahogany)
                    ) {

                        VehicleSpecificationCard(
                            modifier = modifier,
                            vehicle = vehicle,
                            shape = shape
                        )

                        VehicleGeneralDetailCard(
                            modifier = modifier,
                            vehicle = vehicle,
                            shape = shape
                        )
                    }

                }

            }

            if (isShowContactButtons.value) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .onGloballyPositioned {
                            bottomPadding = it.size.height
                        }
                ) {
                    ChatButton {
                        val message =
                            "Hi, I want to talk about this ${vehicle.title} (${vehicle.vehicleNo})"
                        if (vehicle.sellType == SellType.AUCTION.value ||
                            vehicle.sellType == SellType.FINAL.value ||
                            vehicle.sellType == SellType.BANK_SEIZE.value
                        ) {
                            if (!shareOnWhatsapp(context, message)) {
                                context.showToast("Whatsapp is not installed")
                            }
                        } else {
                            if (!shareOnWhatsapp(context, message, vehicle.ownerNumber)) {
                                context.showToast("Whatsapp is not installed")
                            }
                        }
                    }

                    CallButton {
                        if (vehicle.sellType == SellType.AUCTION.value ||
                            vehicle.sellType == SellType.FINAL.value ||
                            vehicle.sellType == SellType.BANK_SEIZE.value
                        ) {
                            if (!callAdmin(context))
                                context.showToast("an error occurred")
                        } else {
                            if (!callAdmin(context, vehicle.ownerNumber))
                                context.showToast("an error occurred")
                        }
                    }
                }
            }

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