package com.example.salvageauctionindia.ui.spare_parts_screen

import com.example.salvageauctionindia.domain.model.SparePart

data class SparePartsState(
    val isLoading: Boolean = false,
    val spareParts: List<SparePart> = emptyList(),
    val error: String? = null
)
