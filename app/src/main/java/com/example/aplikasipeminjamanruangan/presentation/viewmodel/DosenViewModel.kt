package com.example.aplikasipeminjamanruangan.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplikasipeminjamanruangan.data.Resource
import com.example.aplikasipeminjamanruangan.domain.model.PeminjamanModel
import com.example.aplikasipeminjamanruangan.domain.model.PengajuanModel
import com.example.aplikasipeminjamanruangan.domain.usecase.IAppUseCase
import com.example.aplikasipeminjamanruangan.presentation.states.RealtimeDBDosenState
import com.example.aplikasipeminjamanruangan.presentation.states.RealtimeDBGetPeminjamanState
import com.example.aplikasipeminjamanruangan.presentation.states.RealtimeDBPeminjamanState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DosenViewModel @Inject constructor(
    private val appUseCase: IAppUseCase
) : ViewModel() {
    private val _dosen = MutableStateFlow(RealtimeDBDosenState())
    val dosenState: StateFlow<RealtimeDBDosenState> = _dosen.asStateFlow()

    init {
        getDosen()
    }

    fun getDosen() = viewModelScope.launch {
        appUseCase.getDosen().collect { result ->
            when (result) {
                is Resource.Loading -> {
                    _dosen.update { it.copy(data = null, isLoading = true, errMsg = null) }
                }

                is Resource.Success -> {
                    _dosen.update {
                        it.copy(
                            data = result.data,
                            isLoading = false,
                            errMsg = null
                        )
                    }
                }

                is Resource.Error -> {
                    _dosen.update {
                        it.copy(
                            data = null,
                            isLoading = false,
                            errMsg = result.exception.message
                        )
                    }
                }
            }
        }
    }
}