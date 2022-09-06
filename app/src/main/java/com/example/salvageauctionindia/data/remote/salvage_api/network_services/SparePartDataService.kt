package com.example.salvageauctionindia.data.remote.salvage_api.network_services

import com.example.salvageauctionindia.data.remote.dtos.Response
import com.example.salvageauctionindia.data.remote.dtos.SparePartDto
import com.example.salvageauctionindia.data.remote.dtos.UpdateBody
import retrofit2.http.*

interface SparePartDataService {

    @Headers("Content-Type: application/json")
    @GET("/get-spare-parts/")
    suspend fun getSpareParts(
        @Header("userId") userId: String
    ): List<SparePartDto>


    @Headers("Content-Type: application/json")
    @GET("/get-spare-parts-by-id/")
    suspend fun getSparePartsById(
        @Header("userId") userId: String
    ): List<SparePartDto>



    @Headers("Content-Type: application/json")
    @GET("/get-spare-part/{postId}")
    suspend fun getSparePart(
        @Path("postId") postId: String,
        @Header("userId") userId: String
    ): SparePartDto?


    @Headers("Content-Type: application/json")
    @POST("/insert-spare-part")
    suspend fun insertSparePart(@Body sparePartDto: SparePartDto): Response


    @POST("/upload-image")
    @FormUrlEncoded
    suspend fun uploadImage(
        @Field("image") image: String,
        @Field("postId") postId: String,
        @Field("imageName") imageName: String
    ): Boolean


    @Headers("Content-Type: application/json")
    @POST("/update-spare-part")
    suspend fun updateSparePart(
        @Header("userId") userId: String,
        @Body body: UpdateBody
    ): Response


}