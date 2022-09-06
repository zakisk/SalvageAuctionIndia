package com.example.salvageauctionindia.ui.app_navigation.home_screen_navigation.components

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor() : ViewModel() {

    private val _searchWidgetState : MutableState<SearchWidgetState> =
                mutableStateOf(SearchWidgetState.CLOSED)
    val searchWidgetState : State<SearchWidgetState> = _searchWidgetState

    private val _searchTextState : MutableState<String> =
                mutableStateOf("")
    val searchTextState : State<String> = _searchTextState

    fun updateSearchWidgetState(newValue: SearchWidgetState) {
        _searchWidgetState.value = newValue
    }

    fun updateSearchTextState(newValue: String) {
        _searchTextState.value = newValue
    }

}