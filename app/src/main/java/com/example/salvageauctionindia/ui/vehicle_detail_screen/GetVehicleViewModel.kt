package com.example.salvageauctionindia.ui.vehicle_detail_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salvageauctionindia.domain.model.DataState
import com.example.salvageauctionindia.domain.use_cases.GetVehicleUseCase
import com.example.salvageauctionindia.util.Constants.NAV_KEY_POST_ID
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class GetVehicleViewModel @Inject constructor(
    private val getVehicleUseCase: GetVehicleUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state: MutableState<VehicleState> = mutableStateOf(VehicleState())
    val state: State<VehicleState> = _state

    init {
        savedStateHandle.get<String>(NAV_KEY_POST_ID)?.let { postId ->
            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: "0"
            getVehicle(postId, userId)
        }
    }


    private fun getVehicle(postId : String, userId: String) {
       getVehicleUseCase(postId, userId).onEach {
           when(it) {
               is DataState.Loading -> {
                   _state.value = VehicleState(isLoading = true)
               }

               is DataState.Success -> {
                   _state.value = VehicleState(vehicle = it.data)
               }

               is DataState.Error -> {
                   _state.value = VehicleState(error = it.message ?: "Unknown Error")
               }
           }
       }.launchIn(viewModelScope)
    }

}