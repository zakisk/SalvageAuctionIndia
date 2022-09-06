package com.example.salvageauctionindia.ui.spare_part_detail_screen

import com.example.salvageauctionindia.domain.model.SparePart

data class SparePartState(
    val isLoading: Boolean = false,
    val sparePart: SparePart? = null,
    val error: String? = null
)
