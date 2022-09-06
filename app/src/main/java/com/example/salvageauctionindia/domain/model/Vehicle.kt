package com.example.salvageauctionindia.domain.model

import com.example.salvageauctionindia.data.remote.dtos.VehicleDto
import com.example.salvageauctionindia.util.asTinyInt


data class Vehicle (
    var userId: String,
    var postId: String,
    var postDate: String,
    val vehicleType: String,
    val brandName: String,
    val year: String,
    val title: String,
    val otherDetails: String? = null,
    val vehicleNo: String,
    val transmissionType: String,
    val fuelType: String,
    val kmDriven: Int,
    val documentStatus: String,
    val sellType: String,
    val isApproved: Boolean,
    val ownerNumber: String,
    val vehicleCity: String,
    val vehicleStreet: String?,
    val vehicleState: String?,
    val userVehiclePrice: Int?,
    val auctionStartingPrice: String?,
    val finalPrice: String?,
    val imagePrefix: String,
    val primeImage: String,
    val noOfImages: Int,
    val isTokenized: Boolean,
    val isSold: Boolean
)



fun Vehicle.asDTO(): VehicleDto {
    return VehicleDto(
        userId = userId,
        postId = postId,
        postDate = postDate,
        vehicleType = vehicleType,
        brandName = brandName,
        year = year,
        title = title,
        otherDetails = otherDetails,
        vehicleNo = vehicleNo,
        transmissionType = transmissionType,
        fuelType = fuelType,
        kmDriven = kmDriven,
        documentStatus = documentStatus,
        sellType = sellType,
        isApproved = isApproved.asTinyInt(),
        ownerNumber = ownerNumber,
        vehicleCity = vehicleCity,
        vehicleStreet = vehicleStreet,
        vehicleState = vehicleState,
        userVehiclePrice = userVehiclePrice,
        auctionStartingPrice = auctionStartingPrice,
        finalPrice = finalPrice,
        imagePrefix = imagePrefix,
        primeImage = primeImage,
        noOfImages = noOfImages,
        isTokenized = isTokenized.asTinyInt(),
        isSold = isSold.asTinyInt()
    )
}