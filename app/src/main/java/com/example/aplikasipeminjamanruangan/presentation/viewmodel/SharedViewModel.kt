package com.example.aplikasipeminjamanruangan.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.aplikasipeminjamanruangan.domain.model.PengajuanModel
import com.example.aplikasipeminjamanruangan.domain.model.RoomsModel
import com.example.aplikasipeminjamanruangan.domain.model.RoomsModelMain
import com.example.aplikasipeminjamanruangan.presentation.states.SharedPengajuanViewModelState
import com.example.aplikasipeminjamanruangan.presentation.states.SharedViewModelState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SharedViewModel : ViewModel() {
    private var _sharedState = MutableStateFlow(SharedViewModelState())
    val sharedStated = _sharedState.asStateFlow()

    private var _sharedPengajuanState = MutableStateFlow(SharedPengajuanViewModelState())
    val sharedPengajuanStated = _sharedPengajuanState.asStateFlow()

    fun addRooms(rooms: RoomsModelMain) {
        _sharedState.value.data = rooms
    }

    fun addPengajuan(pengajuan: PengajuanModel) {
        _sharedPengajuanState.value.data = pengajuan
    }

    fun resetRoomsData() {
        _sharedState.update {
            it.copy(
                it.data?.copy(
                    key = it.data!!.key,
                    item = RoomsModel(
                        deskripsi_ruangan = null,
                        fasilitas_ruangan = null,
                        foto_ruangan = null,
                        id_ruangan = 0,
                        isLent = null,
                        lantai_ruangan = null,
                        nama_ruangan = null
                    )
                )
            )
        }
    }
//        _sharedState.value.copy(
//            deskripsi_ruangan = roomsModel.deskripsi_ruangan,
//            fasilitas_ruangan = roomsModel.deskripsi_ruangan,
//            foto_ruangan = roomsModel.foto_ruangan,
//            id_ruangan = roomsModel.id_ruangan,
//            isLent = roomsModel.isLent,
//            lantai_ruangan = roomsModel.lantai_ruangan,
//            nama_ruangan = roomsModel.nama_ruangan
//        )
}