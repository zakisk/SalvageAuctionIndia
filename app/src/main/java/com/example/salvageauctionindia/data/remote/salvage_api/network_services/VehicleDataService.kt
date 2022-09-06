package com.example.salvageauctionindia.data.remote.salvage_api.network_services

import com.example.salvageauctionindia.data.remote.dtos.DeleteBody
import com.example.salvageauctionindia.data.remote.dtos.UpdateBody
import com.example.salvageauctionindia.data.remote.dtos.VehicleDto
import com.example.salvageauctionindia.data.remote.dtos.Response
import retrofit2.http.*

interface VehicleDataService {

    @Headers("Content-Type: application/json")
    @GET("/get-vehicles/{sellType}")
    suspend fun getVehicles(
        @Path("sellType") sellType: String,
        @Header("userId") userId: String
    ): List<VehicleDto>


    @Headers("Content-Type: application/json")
    @GET("/get-vehicles-by-id")
    suspend fun getVehicles(
        @Header("userId") userId: String
    ): List<VehicleDto>


    @Headers("Content-Type: application/json")
    @GET("/get-vehicle/{postId}")
    suspend fun getVehicle(
        @Path("postId") postId: String,
        @Header("userId") userId: String
    ): VehicleDto


    @Headers("Content-Type: application/json")
    @POST("/insert-vehicle")
    suspend fun insertVehicle(@Body vehicleDto: VehicleDto): Response



    @POST("/upload-image")
    @FormUrlEncoded
    suspend fun uploadImage(
        @Field("image") image: String,
        @Field("postId") postId: String,
        @Field("imageName") imageName: String
    ): Boolean


    @Headers("Content-Type: application/json")
    @POST("/update-vehicle")
    suspend fun updateVehicle(
        @Header("userId") userId: String,
        @Body body: UpdateBody
    ): Response


}