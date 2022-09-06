package com.example.salvageauctionindia.domain.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.example.salvageauctionindia.data.remote.dtos.DeleteBody
import com.example.salvageauctionindia.domain.interfaces.NetworkServiceProvider
import com.example.salvageauctionindia.util.Constants
import com.example.salvageauctionindia.util.Constants.KEY_IS_SUCCESS
import com.example.salvageauctionindia.util.Constants.KEY_WHICH
import com.example.salvageauctionindia.util.Constants.POST_ID
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.*


@HiltWorker
class DeleteSalvageWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val networkServiceProvider: NetworkServiceProvider
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        val userId = inputData.getString(POST_ID)
        val postId = inputData.getString(POST_ID)
        val which = inputData.getInt(KEY_WHICH, -1)

        return if (postId != null && userId != null && which != -1) {
            val response = networkServiceProvider.deleteSalvage(postId, userId, DeleteBody(which))
            if (response.isNotNull()) {
                val data = Data.Builder()
                    .putBoolean(KEY_IS_SUCCESS, (response.affectedRows ?: -1) > 0)
                    .build()
                Result.success(data)
            } else {
                Result.failure(createFailureData("server Error, Please try again later"))
            }
        } else {
            Result.failure(createFailureData("Unable to get Data"))
        }
    }


    private fun createFailureData(message: String): Data {
        return Data.Builder()
            .putString(Constants.ERROR, message)
            .build()
    }


    companion object {
        fun createRequest(context: Context, data: Data): UUID {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val workRequest = OneTimeWorkRequestBuilder<DeleteSalvageWorker>()
                .setConstraints(constraints)
                .setInputData(data)
                .build()

            WorkManager.getInstance(context)
                .enqueue(workRequest)

            return workRequest.id
        }
    }

}