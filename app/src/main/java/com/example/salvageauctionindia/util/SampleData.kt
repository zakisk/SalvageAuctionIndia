package com.example.salvageauctionindia.util

import com.example.salvageauctionindia.data.remote.dtos.enums.*
import com.example.salvageauctionindia.domain.model.Vehicle

val vehicle: Vehicle = Vehicle(
    userId = "111",
    postId = "923",
    postDate = "03/03/2022",
    vehicleType = VehicleType.CAR.value,
    brandName = "Tesla",
    year = "2018",
    title = "Tesla Model X",
    otherDetails = "Good Condition Car",
    vehicleNo = "MH19BU3876",
    transmissionType = TransmissionType.MANUAL.value,
    fuelType = FuelType.DIESEL.value,
    kmDriven = 9238,
    documentStatus = DocumentStatus.WITH_RC.value,
    sellType = SellType.FINAL.value,
    isApproved = true,
    ownerNumber = OwnerNumber.FIRST.value,
    vehicleCity = "Jalgaon",
    vehicleStreet = "1, Sadashiv Nagar, Behind Kasturi Hotel, MIDC Road, Mehrun",
    vehicleState = "Maharashtra",
    userVehiclePrice = 9000000,
    auctionStartingPrice = null,
    finalPrice = null,
    imagePrefix = "9233748374",
    primeImage = "https://images.pexels.com/photos/1149137/pexels-photo-1149137.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
    noOfImages = 15,
    isTokenized = false,
    isSold = false
)