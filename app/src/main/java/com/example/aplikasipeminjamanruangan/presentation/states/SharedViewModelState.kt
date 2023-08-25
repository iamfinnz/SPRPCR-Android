package com.example.aplikasipeminjamanruangan.presentation.states

import com.example.aplikasipeminjamanruangan.domain.model.DosenModel
import com.example.aplikasipeminjamanruangan.domain.model.PengajuanModel
import com.example.aplikasipeminjamanruangan.domain.model.RoomsModel
import com.example.aplikasipeminjamanruangan.domain.model.RoomsModelMain

data class SharedViewModelState(
    var data: RoomsModelMain? = null,
)

data class SharedPengajuanViewModelState(
    var data: PengajuanModel? = null,
)