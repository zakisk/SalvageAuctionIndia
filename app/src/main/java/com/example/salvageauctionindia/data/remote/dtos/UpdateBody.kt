package com.example.salvageauctionindia.data.remote.dtos

data class UpdateBody(
    val setKey: Int,
    val setValue: Int,
    val postId: String,
    val title: String,
    val brandName: String,
    val primeImage: String
)
