package com.example.aplikasipeminjamanruangan.presentation.states

import com.example.aplikasipeminjamanruangan.domain.model.PengajuanModel

data class RealtimeDBPengajuanState(
    val data: String? = null,
    val isLoading: Boolean = false,
    val errMsg: String? = null
)

data class RealtimeDBGetPengajuanState(
    val data: List<PengajuanModel?>? = null,
    val isLoading: Boolean = false,
    val errMsg: String? = null
)
