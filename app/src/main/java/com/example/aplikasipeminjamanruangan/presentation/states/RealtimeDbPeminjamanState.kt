package com.example.aplikasipeminjamanruangan.presentation.states

import com.example.aplikasipeminjamanruangan.domain.model.PeminjamanModel

data class RealtimeDBPeminjamanState(
    val data: String? = null,
    val isLoading: Boolean = false,
    val errMsg: String? = null
)

data class RealtimeDBGetPeminjamanState(
    val data: List<PeminjamanModel?>? = null,
    val isLoading: Boolean = false,
    val errMsg: String? = null
)
