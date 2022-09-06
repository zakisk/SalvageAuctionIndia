package com.example.salvageauctionindia.data.remote.salvage_api.source_implementations

import com.example.salvageauctionindia.data.remote.dtos.*
import com.example.salvageauctionindia.data.remote.salvage_api.network_services.NetworkService
import com.example.salvageauctionindia.domain.interfaces.NetworkServiceProvider
import retrofit2.HttpException
import javax.inject.Inject

class NetworkServiceProviderImpl @Inject constructor(
    private val networkService: NetworkService
) : NetworkServiceProvider {

    override suspend fun insertNotificationTokenId(notificationTokenDto: NotificationTokenDto): Response {
        return try {
            networkService.insertNotificationToken(notificationTokenDto)
        } catch (e: HttpException) {
            Response(errorMessage = "Couldn't reach server, Check your Internet Connection")
        } catch (e: Exception) {
            Response(errorMessage = e.localizedMessage ?: "Unknown Error")
        }
    }

    override suspend fun updateNotificationTokenId(
        userId: String,
        tokenId: String
    ): Response {
        return try {
            networkService.updateNotificationToken(userId, tokenId)
        } catch (e: HttpException) {
            Response(errorMessage = "Couldn't reach server, Check your Internet Connection")
        } catch (e: Exception) {
            Response(errorMessage = e.localizedMessage ?: "Unknown Error")
        }
    }



    override suspend fun createUser(user: UserDto): Response {
        return try {
            networkService.createUser(user)
        } catch (e: HttpException) {
            Response(errorMessage = "Couldn't reach server, Check your Internet Connection")
        } catch (e: Exception) {
            Response(errorMessage = e.localizedMessage ?: "Unknown Error")
        }
    }

    override suspend fun deleteSalvage(postId: String, userId: String, body: DeleteBody): Response {
        return try {
             networkService.deleteSalvage(postId, userId, body)
        } catch (e: HttpException) {
            Response(errorMessage = "Couldn't reach server, Check your Internet Connection")
        } catch (e: Exception) {
            Response(errorMessage = e.localizedMessage ?: "Unknown Error")
        }
    }

    override suspend fun notify(postId: String, userId: String, body: NotifyBody): Any {
        return try {
            networkService.notify(postId, userId, body)
        } catch (e: Exception) {
            e.localizedMessage ?: "Unknown Error"
        }
    }


}