package com.example.salvageauctionindia.domain.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.example.salvageauctionindia.domain.interfaces.NetworkServiceProvider
import com.example.salvageauctionindia.util.Constants.KEY_AFFECTED_ROWS
import com.example.salvageauctionindia.util.Constants.KEY_CREATE_USER_ERROR
import com.example.salvageauctionindia.util.Constants.KEY_USER_DTO
import com.example.salvageauctionindia.util.asUserDto
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.*


@HiltWorker
class CreateUserWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val networkServiceProvider: NetworkServiceProvider
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        val userDto = inputData.getString(KEY_USER_DTO)?.asUserDto()
        if (userDto != null) {
            val response = networkServiceProvider.createUser(userDto)
            return if (response.isNotNull()) {
                val data = Data.Builder()
                    .putInt(KEY_AFFECTED_ROWS, response.affectedRows ?: -1)
                    .build()

                Result.success(data)
            } else {
                return Result.failure(failureData(response.errorMessage ?: "Unknown Error"))
            }
        } else {
            return Result.failure(failureData("Error When Parsing User Information"))
        }
    }


    private fun failureData(message: String): Data {
        return workDataOf(KEY_CREATE_USER_ERROR  to message)
    }



    companion object {
        fun createRequest(context: Context, data: Data) : UUID {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val workRequest = OneTimeWorkRequestBuilder<CreateUserWorker>()
                .setConstraints(constraints)
                .setInputData(data)
                .build()

            WorkManager.getInstance(context)
                .enqueue(workRequest)

            return workRequest.id
        }
    }
}