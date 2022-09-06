package com.example.salvageauctionindia.data.remote.salvage_api.network_services

import com.example.salvageauctionindia.data.remote.dtos.*
import retrofit2.http.*


interface NetworkService {

    @Headers("Content-Type: application/json")
    @POST("/insert-notification-token")
    suspend fun insertNotificationToken(@Body notificationTokenDto: NotificationTokenDto): Response


    @Headers("Content-Type: application/json")
    @POST("/update-notification-token")
    suspend fun updateNotificationToken(
        @Header("userId") userId: String,
        @Body tokenId: String
    ): Response



    @Headers("Content-Type: application/json")
    @POST("/create-user")
    suspend fun createUser(@Body user: UserDto): Response



    @Headers("Content-Type: application/json")
    @POST("/delete-salvage/{postId}")
    suspend fun deleteSalvage(
        @Path("postId") postId: String,
        @Header("userId") userId: String,
        @Body body: DeleteBody
    ): Response

    @Headers("Content-Type: application/json")
    @POST("/notify/{postId}")
    suspend fun notify(
        @Path("postId") postId: String,
        @Header("userId") userId: String,
        @Body body: NotifyBody
    ): Any

}