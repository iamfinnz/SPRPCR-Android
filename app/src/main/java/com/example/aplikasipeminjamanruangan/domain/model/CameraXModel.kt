package com.example.aplikasipeminjamanruangan.domain.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CameraXModel(
    var uri: Uri? =null,
): Parcelable