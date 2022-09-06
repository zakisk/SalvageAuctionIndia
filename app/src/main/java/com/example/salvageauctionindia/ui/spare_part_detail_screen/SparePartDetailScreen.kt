package com.example.salvageauctionindia.ui.spare_part_detail_screen

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
import com.example.salvageauctionindia.domain.share.ShareUtil
import com.example.salvageauctionindia.ui.app_navigation.Screen
import com.example.salvageauctionindia.ui.spare_part_detail_screen.components.SparePartSpecificationCard
import com.example.salvageauctionindia.ui.theme.*
import com.example.salvageauctionindia.ui.vehicle_detail_screen.components.CallButton
import com.example.salvageauctionindia.ui.vehicle_detail_screen.components.CarouselImageItem
import com.example.salvageauctionindia.ui.vehicle_detail_screen.components.ChatButton
import com.example.salvageauctionindia.util.Constants
import com.example.salvageauctionindia.util.showToast
import com.google.firebase.auth.FirebaseAuth


@ExperimentalCoilApi
@Composable
fun SparePartDetailScreen(
    viewModel: GetSparePartViewModel = hiltViewModel(),
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
            .background(color = PrussianBlue)
    ) {
        state.value.sparePart?.let { sparePart ->
            val userId = FirebaseAuth.getInstance().currentUser?.uid
            val isShowContactButtons = remember { mutableStateOf(userId != state.value.sparePart?.userId) }

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
                                            "/${sparePart.postId}&${sparePart.imagePrefix}&${sparePart.noOfImages}"
                                )
                            },
                        data = "${Constants.BASE_URL}get-image/${sparePart.postId}/${sparePart.primeImage}",
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

                        SparePartSpecificationCard(
                            modifier = modifier,
                            sparePart = sparePart,
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
                            "Hi, I want to talk about this ${sparePart.title} Spare Part"
                        if (!ShareUtil.shareOnWhatsapp(context, message, sparePart.ownerNumber)) {
                            context.showToast("Whatsapp is not installed")
                        }
                    }

                    CallButton {
                        if (!ShareUtil.callAdmin(context, sparePart.ownerNumber)) context.showToast(
                            "an error occurred"
                        )
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