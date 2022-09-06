package com.example.salvageauctionindia.domain.workers

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.salvageauctionindia.data.remote.dtos.*
import com.example.salvageauctionindia.domain.model.SparePart
import com.example.salvageauctionindia.domain.model.Vehicle
import com.example.salvageauctionindia.util.*
import com.example.salvageauctionindia.util.Constants.DEVICE_ID
import com.example.salvageauctionindia.util.Constants.KEY_NOTIFICATION_TOKEN
import com.example.salvageauctionindia.util.Constants.KEY_NOTIFICATION_TOKEN_DTO
import com.example.salvageauctionindia.util.Constants.KEY_SPARE_PART_DTO
import com.example.salvageauctionindia.util.Constants.KEY_URIS
import com.example.salvageauctionindia.util.Constants.KEY_USER_DTO
import com.example.salvageauctionindia.util.Constants.KEY_USER_ID
import com.example.salvageauctionindia.util.Constants.KEY_VEHICLE_DTO
import com.example.salvageauctionindia.util.Constants.KEY_WHICH
import com.example.salvageauctionindia.util.Constants.NOTIFY_REFRESHED_TOKEN_ID
import com.example.salvageauctionindia.util.Constants.POST_ID
import com.example.salvageauctionindia.util.Constants.UPDATE_BODY
import com.example.salvageauctionindia.util.Constants.USER_ID
import com.example.salvageauctionindia.util.Constants.USER_NAME
import com.google.firebase.auth.FirebaseAuth
import java.util.*

class WorkerStarter(private val context: Context) {

    fun startAddNotificationTokenWorker(): LiveData<WorkInfo>? {
        val notificationTokenDto = createNotificationToken()
        if (notificationTokenDto != null) {
            val data = workDataOf(KEY_NOTIFICATION_TOKEN_DTO to notificationTokenDto.asJSON())
            val uuid = AddNotificationTokenWorker.createRequest(context, data)

            return WorkManager.getInstance(context).getWorkInfoByIdLiveData(uuid)
        } else {
            context.showToast("Problem in Retrieving User Id or Token Id")
        }
        return null
    }

    fun startUpdateNotificationTokenWorker(userId: String, tokenId: String) {
        val data = workDataOf(
            KEY_USER_ID to userId,
            KEY_NOTIFICATION_TOKEN to tokenId
        )
        UpdateNotificationTokenWorker.createRequest(context, data)
    }


    fun startAddVehicleWorker(vehicleDto: VehicleDto, uris: List<Uri>): LiveData<WorkInfo> {
        val data = workDataOf(
            KEY_VEHICLE_DTO to vehicleDto.asJSON(),
            KEY_URIS to uris.asStringArray()
        )

        val uuid = AddVehicleWorker.createRequest(context, data)
        return WorkManager.getInstance(context)
            .getWorkInfoByIdLiveData(uuid)
    }


    fun startAddSparePartWorker(sparePartDto: SparePartDto, uris: List<Uri>): LiveData<WorkInfo> {
        val data = workDataOf(
            KEY_SPARE_PART_DTO to sparePartDto.asJSON(),
            KEY_URIS to uris.asStringArray()
        )

        val uuid = AddSparePartWorker.createRequest(context, data)
        return WorkManager.getInstance(context)
            .getWorkInfoByIdLiveData(uuid)

    }


    fun startUpdateVehicleWorker(
        vehicle: Vehicle,
        setKey: Int,
        setValue: Int
    ): LiveData<WorkInfo> {
        val updateBody = UpdateBody(
            setKey = setKey,
            setValue = setValue,
            postId = vehicle.postId,
            title = vehicle.title,
            brandName = vehicle.brandName,
            primeImage = vehicle.primeImage
        )

        val data = workDataOf(
            USER_ID to vehicle.userId,
            UPDATE_BODY to updateBody.asJSON()
        )

        val uuid = UpdateVehicleWorker.createRequest(context, data)
        return WorkManager.getInstance(context)
            .getWorkInfoByIdLiveData(uuid)
    }


    fun startUpdateSparePartWorker(
        sparePart: SparePart,
        setKey: Int,
        setValue: Int
    ): LiveData<WorkInfo> {
        val updateBody = UpdateBody(
            setKey = setKey,
            setValue = setValue,
            postId = sparePart.postId,
            title = sparePart.title,
            brandName = sparePart.brandName,
            primeImage = sparePart.primeImage
        )

        val data = workDataOf(
            USER_ID to vehicle.userId,
            UPDATE_BODY to updateBody.asJSON()
        )

        val uuid = UpdateSparePartWorker.createRequest(context, data)
        return WorkManager.getInstance(context)
            .getWorkInfoByIdLiveData(uuid)
    }


    fun startCreateUser(userDto: UserDto): LiveData<WorkInfo> {
        val data = workDataOf(KEY_USER_DTO to userDto.asJSON())
        val uuid = CreateUserWorker.createRequest(context, data)
        return WorkManager.getInstance(context)
            .getWorkInfoByIdLiveData(uuid)
    }


    private fun createNotificationToken(): NotificationTokenDto? {
        val pref = SharedPreferencesUtil(context)
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val name = pref.get<String>(USER_NAME) ?: "N/A"
        val tokenId = pref.get<String>(NOTIFY_REFRESHED_TOKEN_ID)
        var deviceId = pref.get<String>(DEVICE_ID)
        if (deviceId == null) {
            deviceId = UUID.randomUUID().toString()
            pref.put(DEVICE_ID, deviceId)
        }
        return if (userId != null && tokenId != null) {
            NotificationTokenDto(
                userId = userId,
                name = name,
                deviceId = deviceId,
                tokenId = tokenId
            )
        } else {
            null
        }
    }


    fun startDeleteSalvageWorker(postId: String, which: Int): LiveData<WorkInfo> {
        val data = workDataOf(
            POST_ID to postId,
            KEY_WHICH to which
        )

        val uuid = DeleteSalvageWorker.createRequest(context, data)
        return WorkManager.getInstance(context)
            .getWorkInfoByIdLiveData(uuid)
    }

}