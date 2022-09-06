package com.example.salvageauctionindia.data.remote.salvage_api.source_implementations

import com.example.salvageauctionindia.data.remote.dtos.Response
import com.example.salvageauctionindia.data.remote.dtos.SparePartDto
import com.example.salvageauctionindia.data.remote.dtos.UpdateBody
import com.example.salvageauctionindia.data.remote.salvage_api.network_services.SparePartDataService
import com.example.salvageauctionindia.domain.interfaces.SparePartDataSource
import retrofit2.HttpException
import javax.inject.Inject

class SparePartDataSourceImpl @Inject constructor(
    private val sparePartDataService: SparePartDataService
) : SparePartDataSource {

    override suspend fun getSpareParts(userId: String): List<SparePartDto> {
        return try {
            sparePartDataService.getSpareParts(userId)
        } catch(e: Exception) {
            emptyList()
        }
    }

    override suspend fun getSparePartsById(userId: String): List<SparePartDto> {
        return try {
            sparePartDataService.getSparePartsById(userId)
        } catch(e: Exception) {
            emptyList()
        }
    }

    override suspend fun getSparePart(postId: String, userId: String): SparePartDto? {
        return try {
            sparePartDataService.getSparePart(postId, userId)
        } catch(e: Exception) {
            null
        }
    }

    override suspend fun insertSparePart(sparePartDto: SparePartDto): Response {
        return try {
            sparePartDataService.insertSparePart(sparePartDto)
        } catch (e: HttpException) {
            Response(errorMessage = "Couldn't reach server, Check your Internet Connection")
        } catch (e: Exception) {
            Response(errorMessage = e.localizedMessage ?: "Unknown Error")
        }
    }

    override suspend fun uploadImage(image: String, postId: String, imageName: String): Boolean {
        return try {
            sparePartDataService.uploadImage(image, postId, imageName)
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun updateSparePart(userId: String, body: UpdateBody): Response {
        return try {
            sparePartDataService.updateSparePart(userId, body)
        } catch (e: Exception) {
            Response()
        }
    }
}