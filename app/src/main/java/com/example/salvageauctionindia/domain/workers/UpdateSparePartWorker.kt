package com.example.salvageauctionindia.domain.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.example.salvageauctionindia.domain.interfaces.SparePartDataSource
import com.example.salvageauctionindia.util.Constants.ERROR
import com.example.salvageauctionindia.util.Constants.UPDATE_BODY
import com.example.salvageauctionindia.util.Constants.USER_ID
import com.example.salvageauctionindia.util.asUpdateBody
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.*


@HiltWorker
class UpdateSparePartWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val sparePartDataSource: SparePartDataSource
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        val userId = inputData.getString(USER_ID)
        val body = inputData.getString(UPDATE_BODY)?.asUpdateBody()

        return if (body != null && userId != null) {
            val response = sparePartDataSource.updateSparePart(userId,  body)
            if (response.isNotNull()) {
                if ((response.affectedRows ?: -1) > 0) {
                    Result.success()
                } else {
                    Result.failure(createFailureData("Spare Part doesn't exist"))
                }
            } else {
                Result.failure(createFailureData("Server error, Please try again later"))
            }
        } else {
            Result.failure(createFailureData("Unable to get Data"))
        }
    }


    private fun createFailureData(message: String): Data {
        return Data.Builder()
            .putString(ERROR, message)
            .build()
    }


    companion object {
        fun createRequest(context: Context, data: Data): UUID {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val workRequest = OneTimeWorkRequestBuilder<UpdateSparePartWorker>()
                .setConstraints(constraints)
                .setInputData(data)
                .build()

            WorkManager.getInstance(context)
                .enqueue(workRequest)

            return workRequest.id
        }
    }

}