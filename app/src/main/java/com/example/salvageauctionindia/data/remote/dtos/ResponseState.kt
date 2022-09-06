package com.example.salvageauctionindia.data.remote.dtos

data class ResponseState(
    val response: Response = Response(),
    val errorMsg: String? = null
)
