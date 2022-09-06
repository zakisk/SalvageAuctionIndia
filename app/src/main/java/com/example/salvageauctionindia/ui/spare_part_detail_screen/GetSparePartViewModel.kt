package com.example.salvageauctionindia.ui.spare_part_detail_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salvageauctionindia.domain.model.DataState
import com.example.salvageauctionindia.domain.use_cases.GetSparePartUseCase
import com.example.salvageauctionindia.util.Constants
import com.example.salvageauctionindia.util.Constants.NAV_KEY_POST_ID
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class GetSparePartViewModel @Inject constructor(
    private val getSparePartUseCase: GetSparePartUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state: MutableState<SparePartState> = mutableStateOf(SparePartState())
    val state: State<SparePartState> = _state

    init {
        savedStateHandle.get<String>(NAV_KEY_POST_ID)?.let { postId ->
            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: "0"
            getSparePart(postId, userId)
        }
    }


    private fun getSparePart(postId : String, userId: String) {
        getSparePartUseCase(postId, userId).onEach {
            when(it) {
                is DataState.Loading -> {
                    _state.value = SparePartState(isLoading = true)
                }

                is DataState.Success -> {
                    _state.value = SparePartState(sparePart = it.data)
                }

                is DataState.Error -> {
                    _state.value = SparePartState(error = it.message ?: "Unknown Error")
                }
            }
        }.launchIn(viewModelScope)
    }

}