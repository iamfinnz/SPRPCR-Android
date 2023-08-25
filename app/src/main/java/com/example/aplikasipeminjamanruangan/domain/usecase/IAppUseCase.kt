package com.example.aplikasipeminjamanruangan.domain.usecase

import android.content.Context
import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner
import com.example.aplikasipeminjamanruangan.data.Resource
import com.example.aplikasipeminjamanruangan.domain.model.CameraXModel
import com.example.aplikasipeminjamanruangan.domain.model.DosenModel
import com.example.aplikasipeminjamanruangan.domain.model.PeminjamanModel
import com.example.aplikasipeminjamanruangan.domain.model.PengajuanModel
import com.example.aplikasipeminjamanruangan.domain.model.RetrofitImageModel
import com.example.aplikasipeminjamanruangan.domain.model.RetrofitPcrModel
import com.example.aplikasipeminjamanruangan.domain.model.RoomsMataKuliah
import com.example.aplikasipeminjamanruangan.domain.model.RoomsModelMain
import kotlinx.coroutines.flow.Flow
import java.io.File

interface IAppUseCase {
    suspend fun getRooms(): Flow<Resource<List<RoomsModelMain?>>>
    suspend fun updateRooms(roomsModelMain: RoomsModelMain): Flow<Resource<String>>
    suspend fun captureAndSaveImage(context: Context): Flow<Resource<CameraXModel>>
    suspend fun showCameraPreview(
        previewView: PreviewView,
        lifecycleOwner: LifecycleOwner
    )

    suspend fun getImageResult(file: File): Flow<Resource<RetrofitImageModel>>
    suspend fun verifiedNim(nim: String): Flow<Resource<RetrofitPcrModel>>
    suspend fun insertPengajuan(data: PengajuanModel): Flow<Resource<String>>
    suspend fun getPengajuan(): Flow<Resource<List<PengajuanModel?>>>
    suspend fun insertPeminjaman(data: PeminjamanModel): Flow<Resource<String>>
    suspend fun getPeminjaman(): Flow<Resource<List<PeminjamanModel?>>>
    suspend fun getMataKuliah(): Flow<Resource<List<RoomsMataKuliah?>>>
    suspend fun getDosen(): Flow<Resource<List<DosenModel?>>>
}