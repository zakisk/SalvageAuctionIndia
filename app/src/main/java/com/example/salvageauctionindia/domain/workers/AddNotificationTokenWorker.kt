package com.example.salvageauctionindia.domain.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.example.salvageauctionindia.domain.interfaces.NetworkServiceProvider
import com.example.salvageauctionindia.util.Constants.KEY_ADD_NOTIFICATION_TOKEN_ERROR
import com.example.salvageauctionindia.util.Constants.KEY_AFFECTED_ROWS
import com.example.salvageauctionindia.util.Constants.KEY_NOTIFICATION_TOKEN_DTO
import com.example.salvageauctionindia.util.asNotificationTokenDto
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.*

@HiltWorker
class AddNotificationTokenWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParameters: WorkerParameters,
    val networkServiceProvider: NetworkServiceProvider
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        val notificationTokenDto =
            inputData.getString(KEY_NOTIFICATION_TOKEN_DTO)?.asNotificationTokenDto()

        if (notificationTokenDto != null) {
            val response = networkServiceProvider.insertNotificationTokenId(notificationTokenDto)
            return if (response.isNotNull()) {
                val data = Data.Builder()
                    .putInt(KEY_AFFECTED_ROWS, response.affectedRows ?: -1)
                    .build()
                Result.success(data)
            } else {
                return Result.failure(failureData("Server Error, Please Try Again Later"))
            }
        } else {
            return Result.failure(failureData("Error When Parsing Notification Token"))
        }
    }

    private fun failureData(message: String): Data {
        return workDataOf(KEY_ADD_NOTIFICATION_TOKEN_ERROR to message)
    }

    companion object {
        fun createRequest(context: Context, data: Data): UUID {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val workRequest = OneTimeWorkRequestBuilder<AddNotificationTokenWorker>()
                .setConstraints(constraints)
                .setInputData(data)
                .build()

            WorkManager.getInstance(context)
                .enqueue(workRequest)

            return workRequest.id
        }
    }
}