package com.example.salvageauctionindia.domain.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.example.salvageauctionindia.domain.interfaces.VehicleDataSource
import com.example.salvageauctionindia.util.Constants.ERROR
import com.example.salvageauctionindia.util.Constants.UPDATE_BODY
import com.example.salvageauctionindia.util.Constants.USER_ID
import com.example.salvageauctionindia.util.asUpdateBody
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.*


@HiltWorker
class UpdateVehicleWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val vehicleDataSource: VehicleDataSource
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        val userId = inputData.getString(USER_ID)
        val body = inputData.getString(UPDATE_BODY)?.asUpdateBody()

        return if (body != null && userId != null) {
            val response = vehicleDataSource.updateVehicle(userId, body)
            if (response.isNotNull()) {
                if ((response.affectedRows ?: -1) > 0) {
                    Result.success()
                } else {
                    Result.failure(createFailureData("Vehicle doesn't exist"))
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

            val workRequest = OneTimeWorkRequestBuilder<UpdateVehicleWorker>()
                .setConstraints(constraints)
                .setInputData(data)
                .build()

            WorkManager.getInstance(context)
                .enqueue(workRequest)

            return workRequest.id
        }
    }

}