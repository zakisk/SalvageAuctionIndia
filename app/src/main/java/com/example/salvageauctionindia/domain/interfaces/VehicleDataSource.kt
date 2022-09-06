package com.example.salvageauctionindia.domain.interfaces

import com.example.salvageauctionindia.data.remote.dtos.UpdateBody
import com.example.salvageauctionindia.data.remote.dtos.VehicleDto
import com.example.salvageauctionindia.data.remote.dtos.Response

interface VehicleDataSource {

    suspend fun uploadImage(image: String, postId: String, imageName: String): Boolean

    suspend fun insertVehicle(vehicleDto: VehicleDto) : Response

    suspend fun getVehicles(sellType: String, userId: String) : List<VehicleDto>

    suspend fun getVehicles(userId: String) : List<VehicleDto>

    suspend fun getVehicle(postId: String, userId: String) : VehicleDto?

    suspend fun updateVehicle(userId: String, body: UpdateBody): Response

}