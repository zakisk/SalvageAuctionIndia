package com.example.salvageauctionindia.data.remote.dtos

data class UserDto(
    val userId: String,
    val name: String,
    val city: String,
    val state: String,
    val mobileNo: String,
    val timeStamp: String,
    val isBlocked: Int
)
