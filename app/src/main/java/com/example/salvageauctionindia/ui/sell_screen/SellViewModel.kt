package com.example.salvageauctionindia.ui.sell_screen

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.work.WorkInfo
import com.example.salvageauctionindia.data.remote.dtos.SparePartDto
import com.example.salvageauctionindia.data.remote.dtos.VehicleDto
import com.example.salvageauctionindia.data.remote.dtos.enums.DocumentStatus
import com.example.salvageauctionindia.data.remote.dtos.enums.FuelType
import com.example.salvageauctionindia.data.remote.dtos.enums.TransmissionType
import com.example.salvageauctionindia.domain.workers.WorkerStarter
import com.example.salvageauctionindia.util.Constants.ADMIN_MOBILE_NO
import com.example.salvageauctionindia.util.Constants.ERROR
import com.example.salvageauctionindia.util.Constants.KEY_AFFECTED_ROWS
import com.example.salvageauctionindia.util.Constants.USER_MOBILE
import com.example.salvageauctionindia.util.Helper.createSparePart
import com.example.salvageauctionindia.util.Helper.createVehicle
import com.example.salvageauctionindia.util.SharedPreferencesUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SellViewModel @Inject constructor() : ViewModel() {

    val price: MutableState<String> = mutableStateOf("")
    val priceError: MutableState<Boolean> = mutableStateOf(true)
    val sparePrice: MutableState<String> = mutableStateOf("")
    val sparePriceError: MutableState<Boolean> = mutableStateOf(true)
    val brandName: MutableState<String> = mutableStateOf("")
    val brandNameError: MutableState<Boolean> = mutableStateOf(true)
    val vehicleNo: MutableState<String> = mutableStateOf("")
    val vehicleNoError: MutableState<Boolean> = mutableStateOf(true)
    val year: MutableState<String> = mutableStateOf("")
    val yearError: MutableState<Boolean> = mutableStateOf(true)
    val kmDriven: MutableState<String> = mutableStateOf("")
    val kmDrivenError: MutableState<Boolean> = mutableStateOf(true)
    val title: MutableState<String> = mutableStateOf("")
    val titleError: MutableState<Boolean> = mutableStateOf(true)
    val vehicleCity: MutableState<String> = mutableStateOf("")
    val vehicleCityError: MutableState<Boolean> = mutableStateOf(true)
    val vehicleStreet: MutableState<String> = mutableStateOf("")
    val vehicleState: MutableState<String> = mutableStateOf("")
    val otherDetails: MutableState<String> = mutableStateOf("")
    val transmissionTypeExpanded: MutableState<Boolean> = mutableStateOf(false)
    val transmissionType: MutableState<String> = mutableStateOf(TransmissionType.MANUAL.value)
    val fuelTypeExpanded: MutableState<Boolean> = mutableStateOf(false)
    val fuelType: MutableState<String> = mutableStateOf(FuelType.DIESEL.value)
    val documentStatusExpanded: MutableState<Boolean> = mutableStateOf(false)
    val documentStatus: MutableState<String> = mutableStateOf(DocumentStatus.WITH_RC.value)
    val spareAddress: MutableState<String> = mutableStateOf("")
    val spareAddressError: MutableState<Boolean> = mutableStateOf(true)
    val isShowBasicInformation: MutableState<Boolean> = mutableStateOf(true)
    val isShowVehicleAddress: MutableState<Boolean> = mutableStateOf(true)
    val isShowImages: MutableState<Boolean> = mutableStateOf(true)

    private val _uploadState: MutableState<UploadState> = mutableStateOf(UploadState())
    val state: MutableState<UploadState> = _uploadState

    fun validateVehicle(): Boolean {
        return try {
            brandName.value.length >= 3 && title.value.length >= 7 &&
                    year.value.toInt() >= 1900 && year.value.toInt() <= Calendar.getInstance()
                .get(Calendar.YEAR)
                    && kmDriven.value.toInt() < 1000000 &&
                    vehicleNo.value.length >= 7 && vehicleCity.value.length >= 3
        } catch (e: Exception) {
            false
        }
    }

    fun validateSparePart(): Boolean {
        return try {
            brandName.value.length >= 3 && title.value.length >= 7
        } catch (e: Exception) {
            false
        }
    }


    fun addVehicle(context: Context, uris: List<Uri>, type: String) {
        val ownerNumber = SharedPreferencesUtil(context).get<String>(USER_MOBILE) ?: ADMIN_MOBILE_NO
        val vehicleDto = createVehicleDto(ownerNumber, uris.size, type)
        if (vehicleDto != null) {
            WorkerStarter(context).startAddVehicleWorker(vehicleDto, uris)
                .observe(context as LifecycleOwner) {
                    observe(it)
                }
        } else {
            _uploadState.value =
                UploadState(errorMessage = "an error occurred, Please try again later")
        }
    }


    fun addSparePart(context: Context, uris: List<Uri>) {
        val ownerNumber = SharedPreferencesUtil(context).get<String>(USER_MOBILE) ?: ADMIN_MOBILE_NO
        val sparePartDto = createSparePartDto(ownerNumber, uris.size)
        if (sparePartDto != null) {
            WorkerStarter(context).startAddSparePartWorker(sparePartDto, uris)
                .observe(context as LifecycleOwner) {
                    observe(it)
                }
        } else {
            _uploadState.value =
                UploadState(errorMessage = "an error occurred, Please try again later")
        }
    }


    private fun observe(workInfo: WorkInfo) {
        when (workInfo.state) {
            WorkInfo.State.FAILED -> {
                val errorMessage =
                    workInfo.outputData.getString(ERROR) ?: "Unknown Error"
                _uploadState.value = UploadState(errorMessage = errorMessage)
            }

            WorkInfo.State.SUCCEEDED -> {
                val result = workInfo.outputData.getInt(KEY_AFFECTED_ROWS, -1)
                _uploadState.value = UploadState(isSuccess = result != -1)
            }

            WorkInfo.State.ENQUEUED -> {
                _uploadState.value = UploadState(isUploading = true)
            }

            WorkInfo.State.RUNNING -> {
                _uploadState.value = UploadState(isUploading = true)
            }

            WorkInfo.State.BLOCKED -> {
                val errorMessage = workInfo.outputData.getString(ERROR) ?: "Unknown Error"
                _uploadState.value = UploadState(errorMessage = errorMessage)
            }

            WorkInfo.State.CANCELLED -> {
                val errorMessage = workInfo.outputData.getString(ERROR) ?: "Unknown Error"
                _uploadState.value = UploadState(errorMessage = errorMessage)
            }
        }
    }

    private fun createVehicleDto(ownerNumber: String, noOfImages: Int, type: String): VehicleDto? {
        return createVehicle(
            vehicleType = type,
            brandName = brandName.value,
            year = year.value,
            title = title.value,
            otherDetails = otherDetails.value.ifEmpty { null },
            vehicleNo = vehicleNo.value,
            transmissionType = transmissionType.value,
            fuelType = fuelType.value,
            kmDriven = kmDriven.value.toInt(),
            documentStatus = documentStatus.value,
            ownerNumber = ownerNumber,
            vehicleCity = vehicleCity.value,
            vehicleStreet = vehicleStreet.value,
            vehicleState = vehicleState.value,
            userVehiclePrice = price.value.toInt(),
            noOfImages = noOfImages
        )
    }

    private fun createSparePartDto(ownerNumber: String, noOfImages: Int): SparePartDto? {
        return createSparePart(
            price = sparePrice.value.toInt(),
            brandName = brandName.value,
            title = title.value,
            description = otherDetails.value.ifEmpty { null },
            ownerNumber = ownerNumber,
            address = spareAddress.value,
            noOfImages = noOfImages
        )
    }

}