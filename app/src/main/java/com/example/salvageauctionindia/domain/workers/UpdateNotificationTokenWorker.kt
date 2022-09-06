package com.example.salvageauctionindia.domain.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.example.salvageauctionindia.domain.interfaces.NetworkServiceProvider
import com.example.salvageauctionindia.util.Constants
import com.example.salvageauctionindia.util.Constants.KEY_NOTIFICATION_TOKEN
import com.example.salvageauctionindia.util.Constants.KEY_USER_ID
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.*


@HiltWorker
class UpdateNotificationTokenWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted params: WorkerParameters,
    private val networkServiceProvider: NetworkServiceProvider

) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        val userId = inputData.getString(KEY_USER_ID)
        val notificationToken = inputData.getString(KEY_NOTIFICATION_TOKEN)
        if (userId != null && notificationToken != null) {
            val response = networkServiceProvider.updateNotificationTokenId(userId, notificationToken)
            return if (response.isNotNull()) {
                val data = Data.Builder()
                    .putInt(Constants.KEY_AFFECTED_ROWS, response.affectedRows ?: -1)
                    .build()

                Result.success(data)
            } else {
                return Result.failure(failureData(response.errorMessage ?: "Unknown Error"))
            }
        } else {
            return Result.failure(failureData("Error When Parsing Notification Token"))
        }
    }

    private fun failureData(message: String): Data {
        return workDataOf(Constants.KEY_CREATE_USER_ERROR to message)
    }


    companion object {
        fun createRequest(context: Context, data: Data): UUID {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val workRequest = OneTimeWorkRequestBuilder<UpdateNotificationTokenWorker>()
                .setConstraints(constraints)
                .setInputData(data)
                .build()

            WorkManager.getInstance(context)
                .enqueue(workRequest)

            return workRequest.id
        }
    }
}