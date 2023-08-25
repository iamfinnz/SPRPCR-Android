package com.example.aplikasipeminjamanruangan.domain.repository

import android.content.Context
import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner
import com.example.aplikasipeminjamanruangan.data.Resource
import com.example.aplikasipeminjamanruangan.domain.model.CameraXModel
import kotlinx.coroutines.flow.Flow

interface ICustomCameraRepository {
    suspend fun captureAndSaveImage(context: Context): Flow<Resource<CameraXModel>>
    suspend fun showCameraPreview(previewView: PreviewView, lifecycleOwner: LifecycleOwner)
}