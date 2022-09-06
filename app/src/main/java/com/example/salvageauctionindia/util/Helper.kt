package com.example.salvageauctionindia.util

import com.example.salvageauctionindia.data.remote.dtos.SparePartDto
import com.example.salvageauctionindia.data.remote.dtos.VehicleDto
import com.example.salvageauctionindia.data.remote.dtos.enums.SellType
import com.example.salvageauctionindia.data.remote.dtos.enums.VehicleType
import com.google.firebase.auth.FirebaseAuth
import java.util.*

object Helper {

    fun createVehicle(
        vehicleType: String,
        brandName: String,
        year: String,
        title: String,
        otherDetails: String?,
        vehicleNo: String,
        transmissionType: String,
        fuelType: String,
        kmDriven: Int,
        documentStatus: String,
        ownerNumber: String,
        vehicleCity: String,
        vehicleStreet: String?,
        vehicleState: String?,
        userVehiclePrice: Int?,
        noOfImages: Int,
    ): VehicleDto? {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val postId = vehicleNo.asSHA256()
        val postDate = Calendar.getInstance().time.toFormattedString()
        val imagePrefix = "${System.currentTimeMillis()}"
        return if (userId != null) {
            VehicleDto(
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
                sellType = if (vehicleType != VehicleType.SPARE_PART.value) SellType.FRESH.value else SellType.SPARE.value,
                isApproved = 0,
                ownerNumber = ownerNumber,
                vehicleCity = vehicleCity,
                vehicleStreet = vehicleStreet,
                vehicleState = vehicleState,
                userVehiclePrice = userVehiclePrice,
                auctionStartingPrice = null,
                finalPrice = null,
                imagePrefix = imagePrefix,
                primeImage = imagePrefix + "_1",
                noOfImages = noOfImages,
                isTokenized = 0,
                isSold = 0
            )
        } else {
            null
        }
    }


    fun createSparePart(
        price: Int,
        brandName: String,
        title: String,
        description: String?,
        ownerNumber: String,
        address: String,
        noOfImages: Int
    ): SparePartDto? {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val currentMillis = "${System.currentTimeMillis()}"
        val postId = currentMillis.asSHA256()
        return if (userId == null) {
            null
        } else {
            SparePartDto(
                userId = userId,
                postId = postId,
                postDate = Calendar.getInstance().time.toFormattedString(),
                brandName = brandName,
                title = title,
                description = description,
                ownerNumber = ownerNumber,
                address = address,
                price = price,
                isApproved = 0,
                isSold = 0,
                imagePrefix = currentMillis,
                primeImage = currentMillis + "_1",
                noOfImages = noOfImages
            )
        }
    }


    fun map(value: Int, inMin: Int, inMax: Int, outMin: Int, outMax: Int): Int {
        return (value - inMin) * (outMax - outMin) / (inMax - inMin) + outMin
    }

}