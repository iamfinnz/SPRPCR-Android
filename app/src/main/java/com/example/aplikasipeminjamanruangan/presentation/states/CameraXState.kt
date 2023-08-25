package com.example.aplikasipeminjamanruangan.presentation.states

import android.net.Uri

data class CameraXState(
    var uri: Uri? = null,
    val isLoading: Boolean = false,
    val errMsg: String? = null
)
