package com.example.salvageauctionindia.data.remote.dtos

import com.example.salvageauctionindia.domain.model.Vehicle
import com.example.salvageauctionindia.util.asBoolean
import com.google.gson.annotations.SerializedName

data class VehicleDto (
    @SerializedName("userId")
    var userId: String,

    @SerializedName("postId")
    var postId: String,

    @SerializedName("postDate")
    var postDate: String,

    @SerializedName("vehicleType")
    val vehicleType: String,

    @SerializedName("brandName")
    val brandName: String,

    @SerializedName("year")
    val year: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("otherDetails")
    val otherDetails: String? = null,

    @SerializedName("vehicleNo")
    val vehicleNo: String,

    @SerializedName("transmissionType")
    val transmissionType: String,

    @SerializedName("fuelType")
    val fuelType: String,

    @SerializedName("kmDriven")
    val kmDriven: Int,

    @SerializedName("documentStatus")
    val documentStatus: String,

    @SerializedName("sellType")
    val sellType: String,

    @SerializedName("isApproved")
    val isApproved: Int,

    @SerializedName("ownerNumber")
    val ownerNumber: String,

    @SerializedName("vehicleCity")
    val vehicleCity: String,

    @SerializedName("vehicleStreet")
    val vehicleStreet: String?,

    @SerializedName("vehicleState")
    val vehicleState: String?,

    @SerializedName("userVehiclePrice")
    val userVehiclePrice: Int?,

    @SerializedName("auctionStartingPrice")
    val auctionStartingPrice: String?,

    @SerializedName("finalPrice")
    val finalPrice: String?,

    @SerializedName("imagePrefix")
    val imagePrefix: String,

    @SerializedName("primeImage")
    val primeImage: String,

    @SerializedName("noOfImages")
    val noOfImages: Int,

    @SerializedName("isTokenized")
    val isTokenized: Int,

    @SerializedName("isSold")
    val isSold: Int
)


fun VehicleDto.asDomain(): Vehicle {
    return Vehicle(
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
        isApproved = isApproved.asBoolean(),
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
        isTokenized = isTokenized.asBoolean(),
        isSold = isSold.asBoolean()
    )
}