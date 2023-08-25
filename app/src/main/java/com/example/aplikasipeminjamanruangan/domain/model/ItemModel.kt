package com.example.aplikasipeminjamanruangan.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class ItemModel(
    @SerializedName("prodi")
    var prodi: String? = null,
    @SerializedName("nim")
    var nim: String? = null,
    @SerializedName("nama")
    var nama: String? = null,
) : Parcelable