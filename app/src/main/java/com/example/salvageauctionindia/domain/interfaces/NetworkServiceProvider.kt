package com.example.salvageauctionindia.domain.interfaces

import com.example.salvageauctionindia.data.remote.dtos.*

interface NetworkServiceProvider {

    suspend fun insertNotificationTokenId(notificationTokenDto: NotificationTokenDto): Response

    suspend fun updateNotificationTokenId(userId: String, tokenId: String): Response

    suspend fun createUser(user: UserDto): Response

    suspend fun deleteSalvage(postId: String, userId: String, body: DeleteBody): Response

    suspend fun notify(postId: String, userId: String, body: NotifyBody): Any

}