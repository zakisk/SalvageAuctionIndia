package com.example.salvageauctionindia.ui.my_ads_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.salvageauctionindia.util.Constants.NAV_KEY_LAST_TAB
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MyAdsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val selected: MutableState<Int> = mutableStateOf(0)

    init {
        savedStateHandle.get<Int>(NAV_KEY_LAST_TAB)?.let {
            selected.value = it
        }
    }

    fun saveLastTab() {
        savedStateHandle[NAV_KEY_LAST_TAB] = selected.value
    }
}