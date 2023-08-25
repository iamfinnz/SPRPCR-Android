package com.example.aplikasipeminjamanruangan.presentation.viewmodel

import android.content.Context
import android.net.Uri
import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplikasipeminjamanruangan.data.Resource
import com.example.aplikasipeminjamanruangan.domain.usecase.IAppUseCase
import com.example.aplikasipeminjamanruangan.presentation.states.CameraXState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraXViewModel @Inject constructor(
    private val appUseCase: IAppUseCase
) : ViewModel() {
    private val _uriState = MutableStateFlow(CameraXState())
    val uriState: StateFlow<CameraXState> = _uriState.asStateFlow()

    private val _isCameraLaunched = MutableStateFlow(false)
    val isCameraLaunched: StateFlow<Boolean> = _isCameraLaunched.asStateFlow()

    private val _isGalleryLaunched = MutableStateFlow(false)
    val isGalleryLaunched: StateFlow<Boolean> = _isGalleryLaunched.asStateFlow()

    fun saveUriFromGallery(uri: Uri) {
        _uriState.value = CameraXState(uri = uri, errMsg = null, isLoading = false)
    }

    fun isLaunched(status: Boolean) {
        _isCameraLaunched.value = status
    }

    fun isGalleryLaunched(status: Boolean){
        _isGalleryLaunched.value = status
    }

    fun showCameraPreview(
        previewView: PreviewView,
        lifecycleOwner: LifecycleOwner
    ) {
        viewModelScope.launch {
            appUseCase.showCameraPreview(
                previewView,
                lifecycleOwner
            )
        }
    }

    fun captureAndSave(context: Context) {
        viewModelScope.launch {
            appUseCase.let {
                appUseCase.captureAndSaveImage(context).collect { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _uriState.update {
                                it.copy(
                                    uri = null,
                                    isLoading = true,
                                    errMsg = null
                                )
                            }
                        }

                        is Resource.Success -> {
                            _uriState.update {
                                it.copy(
                                    uri = result.data.uri,
                                    isLoading = false,
                                    errMsg = null
                                )
                            }
                        }

                        is Resource.Error -> {
                            _uriState.update {
                                it.copy(
                                    uri = null,
                                    isLoading = false,
                                    errMsg = result.exception.message
                                )
                            }
                        }
                    }

                }
            }
        }
    }
}