package com.example.salvageauctionindia.domain.model

data class SparePart(
    val userId: String,
    val postId: String,
    val postDate: String,
    val brandName: String,
    val title: String,
    val description: String?,
    val ownerNumber: String,
    val address: String,
    val price: Int,
    val isApproved: Boolean,
    val isSold: Boolean,
    val imagePrefix: String,
    val primeImage: String,
    val noOfImages: Int,
)