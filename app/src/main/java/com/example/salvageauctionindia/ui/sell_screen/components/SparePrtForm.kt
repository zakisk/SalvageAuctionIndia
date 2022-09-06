package com.example.salvageauctionindia.ui.sell_screen.components

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.salvageauctionindia.ui.common_components.MyButton
import com.example.salvageauctionindia.ui.sell_screen.SellViewModel
import com.example.salvageauctionindia.ui.theme.LocalSpacing
import com.example.salvageauctionindia.ui.vehicle_detail_screen.components.Div
import com.example.salvageauctionindia.util.Constants.OPERATION_SPARE_PART
import com.example.salvageauctionindia.util.showToast


@Composable
fun SparePartForm(
    viewModel: SellViewModel,
    selectImage: () -> Unit,
    images: MutableState<List<Uri>>,
    onSubmit: (Int) -> Unit,
    isNetworkAvailable: MutableState<Boolean>,
    navigate: () -> Unit
) {

    val state = viewModel.state
    val spacing = LocalSpacing.current
    val context = LocalContext.current
    val onCloseClicked: (Int) -> Unit = {
        images.value = images.value.filterIndexed { index, _ -> index != it }
    }

    LazyColumn(
        modifier = Modifier.padding(spacing.small)
    ) {

        item {
            Text(
                text = "Fill your Spare Part Details",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h5
            )
            Spacer(modifier = Modifier.height(spacing.medium))
            Div()
            Label(label = "Mandatory Fields *")
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {

            Heading(text = "Spare Part Information", isShow = viewModel.isShowBasicInformation)

            AnimatedVisibility(
                visible = viewModel.isShowBasicInformation.value,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                SparePartBasicInformation(viewModel = viewModel)
            }
            if (viewModel.isShowBasicInformation.value)
                Spacer(modifier = Modifier.height(spacing.extraSmall))
        }

        item {

            Heading(text = "Images", isShow = viewModel.isShowImages)

            AnimatedVisibility(
                visible = viewModel.isShowImages.value,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                ImagesRow(
                    images = images,
                    selectImage = selectImage,
                    onCloseClicked = onCloseClicked
                )
            }
        }

        item {
            MyButton(
                text = "Submit",
                onClick = {
                    if (isNetworkAvailable.value && viewModel.validateSparePart() && images.value.size > 2) {
                        onSubmit(OPERATION_SPARE_PART)
                    } else {
                        context.showToast("Please fill all fields and Select at least three images")
                    }
                }
            )
        }

    }

    if (state.value.isUploading) {
        StatusDialog()
    }

    if (state.value.isSuccess) {
        StatusDialog(isSuccess = true, navigate = navigate)
    }

    state.value.errorMessage?.let {
        context.showToast(it)
    }

}