package com.example.salvageauctionindia.domain.workers

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.example.salvageauctionindia.data.remote.dtos.SparePartDto
import com.example.salvageauctionindia.domain.interfaces.SparePartDataSource
import com.example.salvageauctionindia.domain.notification.SalvageNotification
import com.example.salvageauctionindia.util.*
import com.example.salvageauctionindia.util.Constants.KEY_AFFECTED_ROWS
import com.example.salvageauctionindia.util.Constants.KEY_SPARE_PART_DTO
import com.example.salvageauctionindia.util.Constants.KEY_URIS
import com.example.salvageauctionindia.util.Constants.POST_ID
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.*


@HiltWorker
class AddSparePartWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val sparePartDataSource: SparePartDataSource
) : CoroutineWorker(context, workerParameters) {

    private lateinit var notification: SalvageNotification

    override suspend fun doWork(): Result {
        notification = SalvageNotification(context, true)
        notification.sendNotification("uploading Images", "Uploading...")

        try {
            val uris = inputData.getStringArray(KEY_URIS)?.map { it.toUri() }
            val sparePartDto = inputData.getString(KEY_SPARE_PART_DTO)?.asSparePartDto()

            if (sparePartDto != null && uris != null) {

                return if (areAllImagesUploaded(uris, sparePartDto)) {
                    val response = sparePartDataSource.insertSparePart(sparePartDto)
                    if (response.isNotNull()) {
                        val data = Data.Builder()
                            .putInt(KEY_AFFECTED_ROWS, response.affectedRows ?: -1)
                            .putString(POST_ID, sparePartDto.postId)
                            .build()
                        Result.success(data)
                    } else {
                        Result.failure(createFailureData("Spare Part Information is not uploaded"))
                    }

                } else {
                    Result.failure(createFailureData("Images were not uploaded"))
                }
            }
        } catch (e: Exception) {
            Log.d(TAG, "error : ${e.localizedMessage}")
            return Result.failure(createFailureData(e.localizedMessage ?: "Unknown Error"))
        }

        return Result.failure(createFailureData("Images or Spare Part Information is Empty"))
    }


    private fun createFailureData(message: String): Data {
        notification.cancelNotification()
        return Data.Builder()
            .putString(Constants.ERROR, message)
            .build()
    }



    private suspend fun areAllImagesUploaded(uris: List<Uri>, sparePartDto: SparePartDto): Boolean {
        var progress: Int
        val images = uris.mapIndexed { index, uri ->
            try {
                val compressedImage = uri.asBitmap(context)
                val base64 = compressedImage.asBase64()

                if (base64 != null) {
                    progress = Helper.map(index + 1, 1, uris.size, 0, 100)
                    notification.updateProgress(progress)
                    return@mapIndexed sparePartDataSource.uploadImage(
                        base64,
                        sparePartDto.postId,
                        sparePartDto.imagePrefix + "_${index + 1}"
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

            val workRequest = OneTimeWorkRequestBuilder<AddSparePartWorker>()
                .setConstraints(constraints)
                .setInputData(data)
                .build()

            WorkManager.getInstance(context)
                .enqueue(workRequest)

            return workRequest.id
        }
    }
}