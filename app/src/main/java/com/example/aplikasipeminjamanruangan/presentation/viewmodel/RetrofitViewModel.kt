package com.example.aplikasipeminjamanruangan.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplikasipeminjamanruangan.data.Resource
import com.example.aplikasipeminjamanruangan.domain.usecase.IAppUseCase
import com.example.aplikasipeminjamanruangan.presentation.states.RetrofitNimValidation
import com.example.aplikasipeminjamanruangan.presentation.states.RetrofitTextDetectionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class RetrofitViewModel @Inject constructor(
    private val appUseCase: IAppUseCase
) : ViewModel() {
    private val _isTextDetected = MutableStateFlow(RetrofitTextDetectionState())
    val isTextDetected: StateFlow<RetrofitTextDetectionState> = _isTextDetected.asStateFlow()

    private val _isNimValid = MutableStateFlow(RetrofitNimValidation())
    val isNimValid: StateFlow<RetrofitNimValidation> = _isNimValid.asStateFlow()

    fun deleteData() = _isNimValid.update { it.copy(data = null) }

    fun resetRetrofitData() {
        _isTextDetected.update {
            it.copy(
                data = null,
                isLoading = false,
                errMsg = null
            )
        }
        _isNimValid.update{
            it.copy(
                data = null,
                isLoading = false,
                errMsg = null
            )
        }
    }

    fun uploadImage(file: File) = viewModelScope.launch {
        appUseCase.getImageResult(file).collect { result ->
            when (result) {
                is Resource.Loading -> {
                    _isTextDetected.update { it.copy(data = null, isLoading = true, errMsg = null) }
                }

                is Resource.Success -> {
                    _isTextDetected.update {
                        it.copy(
                            data = result.data,
                            isLoading = false,
                            errMsg = null
                        )
                    }
                }

                is Resource.Error -> {
                    _isTextDetected.update {
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

    fun verifyNim(nim: String) = viewModelScope.launch {
        appUseCase.verifiedNim(nim).collect { result ->
            when (result) {
                is Resource.Loading -> {
                    _isNimValid.update { it.copy(data = null, isLoading = true, errMsg = null) }
                }

                is Resource.Success -> {
                    _isNimValid.update {
                        it.copy(
                            data = result.data.items,
                            isLoading = false,
                            errMsg = null
                        )
                    }
                }

                is Resource.Error -> {
                    _isNimValid.update {
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