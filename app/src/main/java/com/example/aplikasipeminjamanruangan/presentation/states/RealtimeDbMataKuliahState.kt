package com.example.aplikasipeminjamanruangan.presentation.states

import com.example.aplikasipeminjamanruangan.domain.model.RoomsMataKuliah

data class RealtimeDbMataKuliahState(
    val data: List<RoomsMataKuliah?>? = null,
    val isLoading: Boolean = false,
    val errMsg: String? = null
)