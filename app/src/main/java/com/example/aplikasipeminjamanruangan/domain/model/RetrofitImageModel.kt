package com.example.aplikasipeminjamanruangan.domain.model

import android.net.Uri
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RetrofitImageModel(
    @SerializedName("nim")
    var nim: String? = null,
) : Parcelable