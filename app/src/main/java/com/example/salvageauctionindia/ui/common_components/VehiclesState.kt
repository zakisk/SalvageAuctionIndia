package com.example.salvageauctionindia.ui.common_components

import com.example.salvageauctionindia.domain.model.Vehicle

data class VehiclesState(
    val isLoading : Boolean = false,
    val vehicles : List<Vehicle> = emptyList(),
    val error : String? = null
)
