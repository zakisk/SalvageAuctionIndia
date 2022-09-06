package com.example.salvageauctionindia.ui.carousel_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.example.salvageauctionindia.ui.carousel_screen.components.ButtonsRow
import com.example.salvageauctionindia.ui.vehicle_detail_screen.components.CarouselImageItem
import com.example.salvageauctionindia.util.Constants.BASE_URL
import com.example.salvageauctionindia.util.ImageDownloader
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState


@ExperimentalCoilApi
@ExperimentalPagerApi
@Composable
fun CarouselScreen(
    postId: String,
    imagePrefix: String,
    noOfImages: Int
) {

    val pagerState = rememberPagerState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        val isShowActions = remember { mutableStateOf(false) }
        val isShowProgressBar = remember { mutableStateOf(true) }
        val userScrollEnabled = remember { mutableStateOf(true) }
        val url = "${BASE_URL}get-image/$postId/${imagePrefix}"

        if (isShowProgressBar.value) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(32.dp),
                color = Color.White,
                strokeWidth = 2.dp
            )
        }

        if (postId != "N/A" && imagePrefix != "N/A" && noOfImages > 0) {
            val context = LocalContext.current
            val imageDownloader = ImageDownloader(context)

            HorizontalPager(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .align(Alignment.Center),
                count = noOfImages,
                state = pagerState,
                userScrollEnabled = userScrollEnabled.value
            ) { page ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    ButtonsRow(
                        onClickDownload = {
                            imageDownloader.download(
                                urlString = "${url}_${page + 1}",
                                imageName = "${imagePrefix}_${page + 1}"
                            )
                        },
                        onClickShare = {
                            imageDownloader.share(
                                urlString = "${url}_${page + 1}",
                                imageName = "${imagePrefix}_${page + 1}"
                            )
                        }
                    )

                    CarouselImageItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(360.dp),
                        data = "${url}_${page + 1}",
                        contentScale = ContentScale.Fit,
                        userScrollEnabled = userScrollEnabled,
                        isZoomable = true,
                        onClick = { isShowActions.value = true }
                    )
                }
            }

        } else {
            Text(
                text = "Image not available",
                style = MaterialTheme.typography.h3,
                color = MaterialTheme.colors.error,
                modifier = Modifier.align(Alignment.Center)
            )
        }

    }
}

