package com.example.aplikasipeminjamanruangan.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplikasipeminjamanruangan.data.Resource
import com.example.aplikasipeminjamanruangan.domain.model.PengajuanModel
import com.example.aplikasipeminjamanruangan.domain.usecase.IAppUseCase
import com.example.aplikasipeminjamanruangan.presentation.states.RealtimeDBGetPengajuanState
import com.example.aplikasipeminjamanruangan.presentation.states.RealtimeDBPengajuanState
import com.example.aplikasipeminjamanruangan.presentation.states.RealtimeDbMataKuliahState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MataKuliahViewModel @Inject constructor(
    private val appUseCase: IAppUseCase
) : ViewModel() {
    private val _mataKuliahState = MutableStateFlow(RealtimeDbMataKuliahState())
    val mataKuliahState: StateFlow<RealtimeDbMataKuliahState> = _mataKuliahState.asStateFlow()

    init{
        getMataKuliah()
    }

    fun getMataKuliah() = viewModelScope.launch {
        appUseCase.getMataKuliah().collect { result ->
            when (result) {
                is Resource.Loading -> {
                    _mataKuliahState.update { it.copy(data = null, isLoading = true, errMsg = null) }
                }

                is Resource.Success -> {
                    _mataKuliahState.update {
                        it.copy(
                            data = result.data,
                            isLoading = false,
                            errMsg = null
                        )
                    }
                }

                is Resource.Error -> {
                    _mataKuliahState.update {
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