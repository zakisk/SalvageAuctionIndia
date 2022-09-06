package com.example.salvageauctionindia.domain.workers

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.example.salvageauctionindia.data.remote.dtos.VehicleDto
import com.example.salvageauctionindia.domain.interfaces.VehicleDataSource
import com.example.salvageauctionindia.domain.notification.SalvageNotification
import com.example.salvageauctionindia.util.Constants.ERROR
import com.example.salvageauctionindia.util.Constants.KEY_AFFECTED_ROWS
import com.example.salvageauctionindia.util.Constants.KEY_URIS
import com.example.salvageauctionindia.util.Constants.KEY_VEHICLE_DTO
import com.example.salvageauctionindia.util.Constants.POST_ID
import com.example.salvageauctionindia.util.Helper.map
import com.example.salvageauctionindia.util.TAG
import com.example.salvageauctionindia.util.asBase64
import com.example.salvageauctionindia.util.asBitmap
import com.example.salvageauctionindia.util.asVehicleDto
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.*

@HiltWorker
class AddVehicleWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val vehicleDataSource: VehicleDataSource
) : CoroutineWorker(context, workerParameters) {

    private lateinit var notification: SalvageNotification

    override suspend fun doWork(): Result {
        notification = SalvageNotification(context, true)

        notification.sendNotification("uploading Images", "Uploading...")

        try {
            val uris = inputData.getStringArray(KEY_URIS)?.map { it.toUri() }
            val vehicleDto = inputData.getString(KEY_VEHICLE_DTO)?.asVehicleDto()

            if (vehicleDto != null && uris != null) {

                return if (areAllImagesUploaded(uris, vehicleDto)) {
                    val response = vehicleDataSource.insertVehicle(vehicleDto)
                    if (response.isNotNull()) {
                        val data = Data.Builder()
                            .putInt(KEY_AFFECTED_ROWS, response.affectedRows ?: -1)
                            .putString(POST_ID, vehicleDto.postId)
                            .build()
                        Result.success(data)
                    } else {
                        Result.failure(createFailureData("Vehicle Information is not uploaded"))
                    }

                } else {
                    Result.failure(createFailureData("Images were not uploaded"))
                }
            }
        } catch (e: Exception) {
            Log.d(TAG, "error : ${e.localizedMessage}")
            return Result.failure(createFailureData(e.localizedMessage ?: "Unknown Error"))
        }

        return Result.failure(createFailureData("Images or Vehicle Information is Empty"))
    }

    private fun createFailureData(message: String): Data {
        notification.cancelNotification()
        return Data.Builder()
            .putString(ERROR, message)
            .build()
    }

    private suspend fun areAllImagesUploaded(uris: List<Uri>, vehicleDto: VehicleDto): Boolean {
        var progress: Int
        val images = uris.mapIndexed { index, uri ->
            try {
                val compressedImage = uri.asBitmap(context)
                val base64 = compressedImage.asBase64()

                if (base64 != null) {
                    progress = map(index + 1, 1, uris.size, 0, 100)
                    notification.updateProgress(progress)
                    return@mapIndexed vehicleDataSource.uploadImage(
                        base64,
                        vehicleDto.postId,
                        vehicleDto.imagePrefix + "_${index + 1}"
                    )
                } else {
                    false
                }

            } catch (e: Exception) {
                Log.d(TAG, "error : ${e.localizedMessage}")
                false
            }
        }
        notification.updateProgress(-1, true)

        val imagesUploadResult = images.filter { it }
        return imagesUploadResult.size == uris.size
    }


    companion object {
        fun createRequest(context: Context, data: Data): UUID {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val workRequest = OneTimeWorkRequestBuilder<AddVehicleWorker>()
                .setConstraints(constraints)
                .setInputData(data)
                .build()

            WorkManager.getInstance(context)
                .enqueue(workRequest)

            return workRequest.id
        }
    }
}