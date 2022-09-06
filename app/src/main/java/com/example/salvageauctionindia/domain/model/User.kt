package com.example.salvageauctionindia.domain.model

data class User(
    val userId: String,
    val name: String,
    val city: String,
    val state: String,
    val mobileNo: String,
    val timeStamp: String,
    val isBlocked: Boolean
)
