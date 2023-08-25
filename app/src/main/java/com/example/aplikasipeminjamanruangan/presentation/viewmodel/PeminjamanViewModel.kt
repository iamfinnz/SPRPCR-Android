package com.example.aplikasipeminjamanruangan.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplikasipeminjamanruangan.data.Resource
import com.example.aplikasipeminjamanruangan.domain.model.PeminjamanModel
import com.example.aplikasipeminjamanruangan.domain.model.PengajuanModel
import com.example.aplikasipeminjamanruangan.domain.usecase.IAppUseCase
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
class PeminjamanViewModel @Inject constructor(
    private val appUseCase: IAppUseCase
) : ViewModel() {
    private val _peminjaman = MutableStateFlow(RealtimeDBPeminjamanState())
    val peminjamanState: StateFlow<RealtimeDBPeminjamanState> = _peminjaman.asStateFlow()

    private val _getPeminjaman = MutableStateFlow(RealtimeDBGetPeminjamanState())
    val getPeminjaman: StateFlow<RealtimeDBGetPeminjamanState> = _getPeminjaman.asStateFlow()

    init {
        getPeminjaman()
    }
    fun insertPeminjaman(data: PeminjamanModel) = viewModelScope.launch {
        appUseCase.insertPeminjaman(data).collect { result ->
            when (result) {
                is Resource.Loading -> {
                    _peminjaman.update { it.copy(data = null, isLoading = true, errMsg = null) }
                }

                is Resource.Success -> {
                    _peminjaman.update {
                        it.copy(
                            data = result.data,
                            isLoading = false,
                            errMsg = null
                        )
                    }
                }

                is Resource.Error -> {
                    _peminjaman.update {
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

    fun getPeminjaman() = viewModelScope.launch {
        appUseCase.getPeminjaman().collect { result ->
            when (result) {
                is Resource.Loading -> {
                    _getPeminjaman.update { it.copy(data = null, isLoading = true, errMsg = null) }
                }

                is Resource.Success -> {
                    _getPeminjaman.update {
                        it.copy(
                            data = result.data,
                            isLoading = false,
                            errMsg = null
                        )
                    }
                }

                is Resource.Error -> {
                    _getPeminjaman.update {
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