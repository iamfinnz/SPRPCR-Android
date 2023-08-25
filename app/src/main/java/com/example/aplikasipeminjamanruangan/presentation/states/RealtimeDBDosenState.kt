package com.example.aplikasipeminjamanruangan.presentation.states

import com.example.aplikasipeminjamanruangan.domain.model.DosenModel

data class RealtimeDBDosenState(
    val data: List<DosenModel?>? = null,
    val isLoading: Boolean = false,
    val errMsg: String? = null
)