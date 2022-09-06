package com.example.salvageauctionindia.ui.vehicle_detail_screen

import com.example.salvageauctionindia.domain.model.Vehicle

data class VehicleState(
    val isLoading: Boolean = false,
    val vehicle: Vehicle? = null,
    val error: String? = null
)