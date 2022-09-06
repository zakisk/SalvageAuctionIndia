package com.example.salvageauctionindia.data.remote.dtos

data class SparePartDto(
    val userId: String,
    val postId: String,
    val postDate: String,
    val brandName: String,
    val title: String,
    val description: String?,
    val ownerNumber: String,
    val address: String,
    val price: Int,
    val isApproved: Int,
    val isSold: Int,
    val imagePrefix: String,
    val primeImage: String,
    val noOfImages: Int,
)