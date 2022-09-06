package com.example.salvageauctionindia.ui.sell_screen

data class UploadState(
    val isUploading: Boolean = false,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false
)