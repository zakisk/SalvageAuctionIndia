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
import com.example.salvageauctionindia.domain.model.SparePart
import com.example.salvageauctionindia.domain.use_cases.GetSparePartsUseCase
import com.example.salvageauctionindia.domain.workers.WorkerStarter
import com.example.salvageauctionindia.ui.spare_parts_screen.SparePartsState
import com.example.salvageauctionindia.util.Constants
import com.example.salvageauctionindia.util.asTinyInt
import com.example.salvageauctionindia.util.showToast
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MyAdsSparePartsViewModel @Inject constructor(
    private val getSparePartsUseCase: GetSparePartsUseCase
) : ViewModel() {

    private val _sparePartsState: MutableState<SparePartsState> = mutableStateOf(SparePartsState())
    lateinit var sparePartsState: State<SparePartsState>

    private var userId: String? = null

    init {
        userId = FirebaseAuth.getInstance().currentUser?.uid ?: "0"
        userId?.let { getSpareParts(it) }
    }


    private fun getSpareParts(userId: String) {
        sparePartsState = _sparePartsState
        getSparePartsUseCase(userId = userId, byId = true).onEach {
            when (it) {
                is DataState.Loading -> {
                    _sparePartsState.value = SparePartsState(isLoading = true)
                }

                is DataState.Success -> {
                    _sparePartsState.value = SparePartsState(spareParts = it.data ?: emptyList())
                }

                is DataState.Error -> {
                    _sparePartsState.value = SparePartsState(error = it.message ?: "Unknown Error")
                }
            }
        }.launchIn(viewModelScope)
    }


    fun onOptionsMenuSelected(context: Context, action: Int, sparePart: SparePart) {
        when (action) {
            ACTION_SOLD -> {
                if (sparePart.isApproved) {
                    if (!sparePart.isSold) {
                        WorkerStarter(context).startUpdateSparePartWorker(
                            sparePart = sparePart,
                            setKey = Constants.KEY_SOLD,
                            setValue = true.asTinyInt()
                        ).observe(context as LifecycleOwner) {
                            observe(workInfo = it, context = context)
                        }
                    } else {
                        context.showToast("This Spare Part is Already Sold by You")
                    }
                } else {
                    context.showToast("Spare Part is not Approved, Can't be Sold")
                }
            }

            ACTION_DELETE -> {
                WorkerStarter(context).startDeleteSalvageWorker(
                    postId = sparePart.postId,
                    which = Constants.KEY_DELETE_SPARE_PART
                ).observe(context as LifecycleOwner) {
                    observe(workInfo = it, context = context)
                }
            }
        }
    }


    private fun observe(workInfo: WorkInfo, context: Context) {
        when (workInfo.state) {
            WorkInfo.State.FAILED -> {
                val errorMessage = workInfo.outputData.getString(Constants.ERROR) ?: "Unknown Error"
                context.showToast(errorMessage)
            }

            WorkInfo.State.SUCCEEDED -> {
                userId?.let { getSpareParts(it) }

            }
            WorkInfo.State.ENQUEUED -> {}
            WorkInfo.State.RUNNING -> {}
            WorkInfo.State.BLOCKED -> {}
            WorkInfo.State.CANCELLED -> {}
        }
    }


    companion object {
        const val ACTION_SOLD = 0

        const val ACTION_DELETE = 1
    }

}