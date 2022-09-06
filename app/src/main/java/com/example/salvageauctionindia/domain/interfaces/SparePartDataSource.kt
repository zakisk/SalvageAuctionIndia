package com.example.salvageauctionindia.domain.interfaces

import com.example.salvageauctionindia.data.remote.dtos.Response
import com.example.salvageauctionindia.data.remote.dtos.SparePartDto
import com.example.salvageauctionindia.data.remote.dtos.UpdateBody

interface SparePartDataSource {

    suspend fun uploadImage(image: String, postId: String, imageName: String): Boolean

    suspend fun insertSparePart(sparePartDto: SparePartDto) : Response

    suspend fun getSpareParts(userId: String) : List<SparePartDto>

    suspend fun getSparePartsById(userId: String) : List<SparePartDto>

    suspend fun getSparePart(postId: String, userId: String) : SparePartDto?

    suspend fun updateSparePart(userId: String, body: UpdateBody): Response

}