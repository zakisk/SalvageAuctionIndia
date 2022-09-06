package com.example.salvageauctionindia.ui.my_ads_screen.components

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkInfo
import com.example.salvageauctionindia.domain.model.DataState
import com.example.salvageauctionindia.domain.model.Vehicle
import com.example.salvageauctionindia.domain.use_cases.GetVehiclesUseCase
import com.example.salvageauctionindia.domain.workers.WorkerStarter
import com.example.salvageauctionindia.ui.common_components.VehiclesState
import com.example.salvageauctionindia.util.Constants.ERROR
import com.example.salvageauctionindia.util.Constants.KEY_DELETE_VEHICLE
import com.example.salvageauctionindia.util.Constants.KEY_SOLD
import com.example.salvageauctionindia.util.Constants.KEY_TOKENIZE
import com.example.salvageauctionindia.util.asTinyInt
import com.example.salvageauctionindia.util.showToast
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MyAdsVehiclesViewModel @Inject constructor(
    private val getVehiclesUseCase: GetVehiclesUseCase,
) : ViewModel() {

    private val _vehiclesState: MutableState<VehiclesState> = mutableStateOf(VehiclesState())
    lateinit var vehiclesState: State<VehiclesState>

    private var userId: String? = null

    init {
        userId = FirebaseAuth.getInstance().currentUser?.uid ?: "0"
        userId?.let { getVehicles(it) }
    }

    private fun getVehicles(userId: String) {
        vehiclesState = _vehiclesState
        getVehiclesUseCase(userId = userId).onEach {
            when (it) {
                is DataState.Loading -> {
                    _vehiclesState.value = VehiclesState(isLoading = true)
                }

                is DataState.Success -> {
                    _vehiclesState.value = VehiclesState(vehicles = it.data ?: emptyList())
                }

                is DataState.Error -> {
                    _vehiclesState.value = VehiclesState(error = it.message ?: "Unknown Error")
                }
            }
        }.launchIn(viewModelScope)
    }


    fun onOptionsMenuSelected(context: Context, action: Int, vehicle: Vehicle) {
        when (action) {
            ACTION_TOKENIZE -> {
                if (vehicle.isApproved) {
                    if (!vehicle.isTokenized) {
                        WorkerStarter(context).startUpdateVehicleWorker(
                            vehicle = vehicle,
                            setKey = KEY_TOKENIZE,
                            setValue = true.asTinyInt()
                        ).observe(context as LifecycleOwner) {
                            observe(workInfo = it, context = context)
                        }
                    } else {
                        context.showToast("This Vehicle is Already Got Token")
                    }
                } else {
                    context.showToast("Vehicle is not Approved, Can't get Token")
                }
            }

            ACTION_SOLD -> {
                if (vehicle.isApproved) {
                    if (!vehicle.isSold) {
                        WorkerStarter(context).startUpdateVehicleWorker(
                            vehicle = vehicle,
                            setKey = KEY_SOLD,
                            setValue = true.asTinyInt()
                        ).observe(context as LifecycleOwner) {
                            observe(workInfo = it, context = context)
                        }
                    } else {
                        context.showToast("This Vehicle is Already Sold by You")
                    }
                } else {
                    context.showToast("Vehicle is not Approved, Can't be Sold")
                }
            }

            ACTION_DELETE -> {
                WorkerStarter(context).startDeleteSalvageWorker(
                    postId = vehicle.postId,
                    which = KEY_DELETE_VEHICLE
                ).observe(context as LifecycleOwner) {
                    observe(workInfo = it, context = context)
                }
            }
        }
    }


    private fun observe(workInfo: WorkInfo, context: Context) {
        when (workInfo.state) {
            WorkInfo.State.FAILED -> {
                val errorMessage =
                    if ((workInfo.outputData.getString(ERROR) ?: "Unknown Error").contains("Query")
                    ) {
                        "DUPLICATE ENTRY\nThe Vehicle Already Exists in List"
                    } else {
                        workInfo.outputData.getString(ERROR) ?: "Unknown Error"
                    }
                context.showToast(errorMessage)
            }

            WorkInfo.State.SUCCEEDED -> {
                userId?.let { getVehicles(it) }

            }
            WorkInfo.State.ENQUEUED -> {}
            WorkInfo.State.RUNNING -> {}
            WorkInfo.State.BLOCKED -> {}
            WorkInfo.State.CANCELLED -> {}
        }
    }


    companion object {
        const val ACTION_TOKENIZE = 0

        const val ACTION_SOLD = 1

        const val ACTION_DELETE = 2
    }

}