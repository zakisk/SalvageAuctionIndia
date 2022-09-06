package com.example.salvageauctionindia.ui.spare_parts_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salvageauctionindia.domain.model.DataState
import com.example.salvageauctionindia.domain.model.SparePart
import com.example.salvageauctionindia.domain.use_cases.GetSparePartsUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class SparePartsViewModel @Inject constructor(
    private val getSparePartsUseCase: GetSparePartsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var _state : MutableState<SparePartsState> = mutableStateOf(SparePartsState())
    lateinit var state : MutableState<SparePartsState>
    val spareParts: MutableState<List<SparePart>> = mutableStateOf(_state.value.spareParts)

    init {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: "0"
        getSpareParts(userId)
    }

    private fun getSpareParts(userId : String) {
        state = _state
        getSparePartsUseCase(userId).onEach {
            when(it) {
                is DataState.Loading -> {
                    _state.value = SparePartsState(isLoading = true)
                }

                is DataState.Success -> {
                    _state.value = SparePartsState(spareParts = it.data ?: emptyList())
                }

                is DataState.Error -> {
                    _state.value = SparePartsState(error = it.message ?: "Unknown Error")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onSearch(text: String) {
        spareParts.value = if (text.isNotEmpty()) {
            _state.value.spareParts.filter {
                it.brandName.contains(text, true)  || it.title.contains(text, true) ||
                    it.description?.contains(text, true) == true || it.address.contains(text, true)
            }
        } else {
            _state.value.spareParts
        }
    }

}