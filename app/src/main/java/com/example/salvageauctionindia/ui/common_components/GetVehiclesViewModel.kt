package com.example.salvageauctionindia.ui.common_components

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salvageauctionindia.domain.model.DataState
import com.example.salvageauctionindia.domain.model.Vehicle
import com.example.salvageauctionindia.domain.use_cases.GetVehiclesUseCase
import com.example.salvageauctionindia.util.Constants.PARAM_SELL_TYPE
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class GetVehiclesViewModel @Inject constructor(
    private val getVehiclesUseCase: GetVehiclesUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state: MutableState<VehiclesState> = mutableStateOf(VehiclesState())
    val state: State<VehiclesState> = _state
    val vehicles: MutableState<List<Vehicle>> = mutableStateOf(_state.value.vehicles)

    init {
        savedStateHandle.get<String>(PARAM_SELL_TYPE)?.let { type ->
            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: "0"
            getVehicles(type, userId)
        }
    }

    private fun getVehicles(type: String, userId : String) {
        getVehiclesUseCase(type, userId).onEach {
            when(it) {
                is DataState.Loading -> {
                    _state.value = VehiclesState(isLoading = true)
                }

                is DataState.Success -> {
                    _state.value = VehiclesState(vehicles = it.data ?: emptyList())
                }

                is DataState.Error -> {
                    _state.value = VehiclesState(error = it.message ?: "Unknown Error")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onSearch(text: String) {
        vehicles.value = if (text.isNotEmpty()) {
            _state.value.vehicles.filter {
                it.brandName.contains(text, true) || it.fuelType.contains(text, true) ||
                        it.otherDetails?.contains(text, true) == true || it.title.contains(text, true) ||
                        it.transmissionType.contains(text, true) || it.vehicleCity.contains(text, true) ||
                        it.year.contains(text) || it.vehicleNo.contains(text, true)
            }
        } else {
            _state.value.vehicles
        }
    }
}