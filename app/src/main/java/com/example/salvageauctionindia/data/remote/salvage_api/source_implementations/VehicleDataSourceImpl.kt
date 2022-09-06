package com.example.salvageauctionindia.data.remote.salvage_api.source_implementations

import com.example.salvageauctionindia.data.remote.dtos.UpdateBody
import com.example.salvageauctionindia.data.remote.dtos.VehicleDto
import com.example.salvageauctionindia.domain.interfaces.VehicleDataSource
import com.example.salvageauctionindia.data.remote.dtos.Response
import com.example.salvageauctionindia.data.remote.salvage_api.network_services.VehicleDataService
import retrofit2.HttpException
import javax.inject.Inject

class VehicleDataSourceImpl @Inject constructor(
    private val vehicleDataService: VehicleDataService
) : VehicleDataSource {

    override suspend fun uploadImage(image: String, postId: String, imageName: String): Boolean {
        return try {
            vehicleDataService.uploadImage(image, postId, imageName)
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun insertVehicle(vehicleDto: VehicleDto): Response {
        return try {
            vehicleDataService.insertVehicle(vehicleDto)
        } catch (e: HttpException) {
            Response(errorMessage = "Couldn't reach server, Check your Internet Connection")
        } catch (e: Exception) {
            Response(errorMessage = e.localizedMessage ?: "Unknown Error")
        }
    }

    override suspend fun getVehicles(sellType: String, userId: String): List<VehicleDto> {
        return try {
            vehicleDataService.getVehicles(sellType, userId)
        } catch(e: Exception) {
            emptyList()
        }
    }

    override suspend fun getVehicles(userId: String): List<VehicleDto> {
        return try {
            vehicleDataService.getVehicles(userId)
        } catch(e: Exception) {
            emptyList()
        }
    }

    override suspend fun getVehicle(postId: String, userId: String): VehicleDto? {
        return try {
            vehicleDataService.getVehicle(postId, userId)
        } catch(e: Exception) {
            null
        }
    }


    override suspend fun updateVehicle(userId: String, body: UpdateBody): Response {
        return try {
            vehicleDataService.updateVehicle(userId, body)
        } catch (e: Exception) {
            Response()
        }
    }

}